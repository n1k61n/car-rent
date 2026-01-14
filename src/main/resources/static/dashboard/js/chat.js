let stompClient = null;
let selectedGuestId = null;
let chatHistory = {};
const currentUserName = "ADMIN"; // Adminin tanınması üçün

function connectAdmin() {
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);

    // Konsolda lazımsız logları bağlamaq üçün (istəsəniz silə bilərsiniz)
    stompClient.debug = null;

    stompClient.connect({}, function (frame) {
        console.log('Admin Connected: ' + frame);

        stompClient.subscribe('/topic/admin', function (payload) {
            const message = JSON.parse(payload.body);
            console.log("Serverdən gələn data:", message);
            handleIncomingMessage(message);
        });
    });
}

function handleIncomingMessage(message) {
    // Sizin modeldəki sahələrə uyğunlaşdırma
    const guestId = message.sessionId;
    const guestName = message.from || "Qonaq";
    const messageText = message.content || message.text; // Hər iki ehtimalı yoxlayırıq

    if (!guestId) return;

    // 1. İstifadəçi siyahıda yoxdursa, sol tərəfə əlavə et
    if (!chatHistory[guestId]) {
        chatHistory[guestId] = [];
        const userHtml = `
            <div id="user-${guestId}" class="user-list-item" onclick="selectGuest('${guestId}', '${guestName}')">
                <div class="avatar-wrapper">
                    <img src="/dashboard/img/undraw_profile.svg" alt="User">
                </div>
                <div class="user-details">
                    <div class="user-name">${guestName}</div>
                    <div class="user-status text-success">Online</div>
                </div>
            </div>`;
        document.getElementById('user-list').insertAdjacentHTML('beforeend', userHtml);
    }

    // 2. Mesajı tarixçəyə əlavə et
    const msgObj = {
        from: guestName,
        text: messageText,
        isMe: (guestName === currentUserName || message.from === "ADMIN")
    };

    chatHistory[guestId].push(msgObj);

    // 3. Əgər hal-hazırda bu istifadəçi ilə çat açıqdırsa, dərhal ekranda göstər
    if (selectedGuestId === guestId) {
        renderMessage(msgObj);
    } else {
        // Digər halda sol tərəfdə bildiriş rəngi ver
        $(`#user-${guestId}`).addClass('unread-highlight');
    }
}

function selectGuest(guestId, guestName) {
    selectedGuestId = guestId;

    // Vizual aktivlik
    $('.user-list-item').removeClass('active');
    $(`#user-${guestId}`).addClass('active').removeClass('unread-highlight');

    // Sağ tərəfi təmizlə və tarixçəni yüklə
    const chatWin = document.getElementById('chat-windows');
    chatWin.innerHTML = '';

    if (chatHistory[guestId]) {
        chatHistory[guestId].forEach(msg => renderMessage(msg));
    }
}

function renderMessage(msg) {
    const chatWin = document.getElementById('chat-windows');
    // Mesajın kimdən gəldiyini yoxlayırıq
    const isMe = msg.from === "ADMIN" || msg.isMe;

    const msgHtml = `
        <div class="message ${isMe ? 'sent' : 'received'}">
            <div class="bubble">
                ${msg.text}
            </div>
            <div class="message-time">
                ${new Date().toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
            </div>
        </div>`;

    chatWin.insertAdjacentHTML('beforeend', msgHtml);
    chatWin.scrollTop = chatWin.scrollHeight;
}

$(document).ready(function() {
    connectAdmin();

    $('#messageForm').on('submit', function(e) {
        e.preventDefault();
        const input = document.getElementById('messageInput');
        const text = input.value.trim();

        if (text && selectedGuestId) {
            const chatMessage = {
                from: "ADMIN",
                to: selectedGuestId,
                content: text, // Sizin loglarda 'content' işləyir
                sessionId: selectedGuestId // Cavab verdiyimiz adamın ID-si
            };

            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));

            // Öz ekranımızda göstərək
            const myMsg = { from: "ADMIN", text: text, isMe: true };
            renderMessage(myMsg);
            chatHistory[selectedGuestId].push(myMsg);

            input.value = '';
        } else if (!selectedGuestId) {
            alert("Zəhmət olmasa, əvvəlcə sol tərəfdən bir istifadəçi seçin.");
        }
    });
});