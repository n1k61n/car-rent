document.addEventListener('DOMContentLoaded', function () {
    const chatWindows = document.getElementById('chat-windows');
    const messageForm = document.getElementById('messageForm');
    const messageInput = document.getElementById('messageInput');

    let stompClient = null;

    function connect() {
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        // Konsolda lazımsız loqları gizlətmək üçün (istəyə bağlı)
        stompClient.debug = null;

        stompClient.connect({}, function (frame) {
            console.log('Bağlantı uğurludur: ' + frame);

            // Bağlantı qurulan kimi "Gözlənilir" yazısını silirik
            if (chatWindows) {
                chatWindows.innerHTML = '';
            }

            stompClient.subscribe('/topic/public', function (payload) {
                const message = JSON.parse(payload.body);
                renderMessage(message);
            });
        }, function (error) {
            console.error('WebSocket bağlantı xətası: ', error);
        });
    }

    function renderMessage(message) {
        if (!chatWindows) return;

        const messageDiv = document.createElement('div');
        const isMine = message.from === currentUserName;

        // HTML strukturu sizin CSS-ə uyğun olaraq
        messageDiv.className = isMine ? 'message sent' : 'message received';
        messageDiv.innerHTML = `
            <div class="bubble shadow-sm">
                ${!isMine ? `<small class="d-block font-weight-bold mb-1" style="font-size: 0.7rem; color: #4e73df;">${message.from}</small>` : ''}
                <span>${message.text}</span>
            </div>
        `;

        chatWindows.appendChild(messageDiv);
        chatWindows.scrollTop = chatWindows.scrollHeight;
    }

    if (messageForm) {
        messageForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const content = messageInput.value.trim();
            if (content && stompClient) {
                const chatMessage = { from: currentUserName, text: content };
                stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
                messageInput.value = '';
            }
        });
    }

    connect();
});