// HTML-dəki gizli inputlardan məlumatları oxuyuruq
const authUserName = document.getElementById('authUserName').value;
const isAuth = document.getElementById('isAuth').value === 'true';

let stompClient = null;
let currentUserName = isAuth ? authUserName : null;
let sessionId = null;

// Səhifə yüklənəndə çat düymələrini aktivləşdir
document.addEventListener('DOMContentLoaded', function() {
    initChatControls();

    // Əgər istifadəçi daxil olubsa, birbaşa qoşul və əsas ekranı göstər
    if (isAuth) {
        document.getElementById('namePromptScreen').style.display = 'none';
        document.getElementById('mainChatArea').style.display = 'flex';
        connectChat();
    }
});

function initChatControls() {
    const chatToggleBtn = document.getElementById('chatToggleBtn');
    const chatInterface = document.getElementById('chatInterface');
    const chatCloseBtn = document.getElementById('chatCloseBtn');
    const startChatBtn = document.getElementById('startChatBtn');

    chatToggleBtn.onclick = () => {
        chatInterface.style.display = (chatInterface.style.display === 'none') ? 'flex' : 'none';
    };

    chatCloseBtn.onclick = () => {
        chatInterface.style.display = 'none';
    };

    if (startChatBtn) {
        startChatBtn.onclick = () => {
            const nameInput = document.getElementById('anonymousUserName');
            if (nameInput.value.trim() !== "") {
                currentUserName = nameInput.value.trim();
                document.getElementById('namePromptScreen').style.display = 'none';
                document.getElementById('mainChatArea').style.display = 'flex';
                connectChat();
            } else {
                alert("Zəhmət olmasa adınızı daxil edin.");
            }
        };
    }

    document.getElementById('chatSendBtn').onclick = sendMessage;
    document.getElementById('chatInput').onkeypress = (e) => {
        if (e.key === 'Enter') sendMessage();
    };
}

function connectChat() {
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);
    stompClient.debug = null; // Konsolu təmiz saxlamaq üçün

   stompClient.connect({}, function (frame) {
       // 1. İstifadəçi özünə gələn cavabları dinləyir
       stompClient.subscribe('/topic/user/' + sessionId, function (payload) {
           showReply(JSON.parse(payload.body).content);
       });

       // 2. QOŞULMA MESAJI: Adminə bildiriş göndərir
       const joinMessage = {
           sender: currentUserName,
           content: "Mən qoşuldum",
           sessionId: sessionId,
           type: 'JOIN' // Mesaj növü JOIN olmalıdır
       };
       stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(joinMessage));
   });
}

function sendMessage() {
    const input = document.getElementById('chatInput');
    const messageText = input.value.trim();

    if (messageText && stompClient) {
        const chatMessage = {
            sender: currentUserName, // Java-da 'sender' sahəsinə uyğun
            content: messageText,    // Java-da 'content' və ya 'text' sahəsinə uyğun
            to: "ADMIN",             // BU VACİBDİR: Backend-dəki NullPointerException-ı həll edir
            sessionId: sessionId,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        showMyMessage(messageText);
        input.value = '';
    }
}

function showMyMessage(text) {
    const msgHtml = `<div style="align-self: flex-end; background: #0779e4; color: white; padding: 10px 15px; border-radius: 15px 15px 0 15px; max-width: 80%; font-size: 14px; box-shadow: 0 3px 10px rgba(7,121,228,0.2);">${text}</div>`;
    appendMessage(msgHtml);
}

function showReceivedMessage(msg) {
    const msgHtml = `<div style="align-self: flex-start; background: white; color: #333; padding: 10px 15px; border-radius: 15px 15px 15px 0; max-width: 80%; font-size: 14px; border: 1px solid #eee; box-shadow: 0 3px 10px rgba(0,0,0,0.02);">${msg.text}</div>`;
    appendMessage(msgHtml);
}

function appendMessage(html) {
    const container = document.getElementById('chatMessages');
    container.insertAdjacentHTML('beforeend', html);
    container.scrollTop = container.scrollHeight;
}