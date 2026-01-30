let stompClient = null;
let selectedGuestId = null;

$(document).ready(function() {
    connectAdmin();
    loadActiveUsers();

    // Siyahıda istifadəçiyə klik edəndə
    $(document).on('click', '.user-list-item', function() {
        const email = $(this).data('email');
        const name = $(this).data('name');
        selectGuest(email, name);
    });

    // Mesaj göndərmə forması
    $('#messageForm').on('submit', function(e) {
        e.preventDefault();
        sendMessageToUser();
    });

    // URL-dən gələn istifadəçini avtomatik seç
    const urlParams = new URLSearchParams(window.location.search);
    const userToSelect = urlParams.get('user');
    if (userToSelect) {
        // We need a small delay to ensure the user list has been loaded via fetch
        setTimeout(() => {
            const userElement = $(`[data-email="${userToSelect}"]`);
            if (userElement.length > 0) {
                const name = userElement.data('name');
                selectGuest(userToSelect, name);
            } else {

                selectGuest(userToSelect, userToSelect);
            }
        }, 500); // 500ms delay might need adjustment
    }
});

function connectAdmin() {
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;

    stompClient.connect({}, function(frame) {
        console.log('Admin connected');

        // Admin ümumi kanalı dinləyir (Yeni gələn mesajları görmək üçün)
        stompClient.subscribe('/topic/admin', function(payload) {
            const message = JSON.parse(payload.body);
            const guestId = message.sessionId;
            const guestName = message.from;

            // Əgər mesaj admin-dən gəlibsə, heç nə etmə (öz mesajımızdır)
            if (guestName === "ADMIN") return;

            // Play notification sound
//            const audio = new Audio('https://assets.mixkit.co/active_storage/sfx/2869/2869-preview.mp3');
//            audio.play().catch(e => console.log("Could not play notification sound.", e));

            // 1. İstifadəçi siyahıda yoxdursa, əlavə et
            if ($(`[data-email="${guestId}"]`).length === 0) {
                addUserToSidebar(guestId, guestName);
            }

            // 2. Əgər gələn mesaj hal-hazırda açıq olan çatın sahibindəndirsə, ekrana çıxar
            if (selectedGuestId === guestId) {
                renderMessage({
                    from: guestName,
                    text: message.content,
                    isMe: false
                });
            } else {
                // Digər halda sol tərəfdəki istifadəçi siyahısını vizual olaraq yenilə
                $(`[data-email="${guestId}"]`).addClass('unread-highlight');
                // Siyahıda yuxarı qaldır
                const userItem = $(`[data-email="${guestId}"]`);
                $('#user-list').prepend(userItem);
            }
        });
    });
}

function loadActiveUsers() {
    fetch('/dashboard/chat/active-sessions')
        .then(response => response.json())
        .then(users => {
            const userList = document.getElementById('user-list');
            if (!userList) return;
            userList.innerHTML = ''; // Təmizlə

            users.forEach(user => {
                // user obyekti: { sessionId: "...", from: "..." }
                // Bizim halda sessionId email-dir.
                addUserToSidebar(user.sessionId, user.from);
            });
        })
        .catch(err => console.error("Siyahı yüklənmədi:", err));
}

function addUserToSidebar(email, name) {
    const userList = document.getElementById('user-list');
    // Dublikat yoxlanışı
    if ($(`[data-email="${email}"]`).length > 0) return;

    const userHtml = `
        <div class="user-list-item" data-email="${email}" data-name="${name}">
            <div class="avatar-wrapper">
                <img src="/dashboard/img/undraw_profile.svg" alt="User">
            </div>
            <div class="user-details">
                <div class="user-name">${name}</div>
                <div class="user-status text-success">Online</div>
            </div>
        </div>`;

    // insertAdjacentHTML istifadə edərək əlavə edirik
    userList.insertAdjacentHTML('afterbegin', userHtml);
}

function selectGuest(email, name) {
    selectedGuestId = email;

    // Vizual olaraq seçilən istifadəçini işarələ
    $('.user-list-item').removeClass('active');
    $(`[data-email="${email}"]`).addClass('active').removeClass('unread-highlight');
    // Başlıqda adı göstər (əgər varsa)
    // $('#chatWith').text(name);

    const chatWin = document.getElementById('chat-windows');
    chatWin.innerHTML = '<div class="text-center p-3">Yüklənir...</div>';

    // Mesaj tarixçəsini gətir
    fetch(`/dashboard/chat/history/${email}`)
        .then(res => res.json())
        .then(messages => {
            chatWin.innerHTML = ''; // "Yüklənir" yazısını sil
            if (messages.length === 0) {
                chatWin.innerHTML = '<div class="text-center p-3 text-muted">Hələ mesaj yoxdur.</div>';
            } else {
                messages.forEach(msg => {
                    renderMessage({
                        from: msg.from,
                        text: msg.content,
                        isMe: msg.from === "ADMIN"
                    });
                });
            }
            chatWin.scrollTop = chatWin.scrollHeight;
        });
}

function sendMessageToUser() {
    const input = document.getElementById('messageInput');
    const text = input.value.trim();

    if (text && selectedGuestId && stompClient) {
        const chatMessage = {
            from: "ADMIN",
            to: selectedGuestId,      // Mesajın gedəcəyi istifadəçi ID-si
            content: text,
            sessionId: selectedGuestId // BAZADA BU ID İLƏ SAXLANACAQ
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));

        renderMessage({
            from: "ADMIN",
            text: text,
            isMe: true
        });

        input.value = '';
    }
}

function renderMessage(data) {
    const chatWin = document.getElementById('chat-windows');
    const align = data.isMe ? 'flex-end' : 'flex-start';
    const bg = data.isMe ? '#007bff' : '#f1f1f1';
    const color = data.isMe ? 'white' : '#333';
    const radius = data.isMe ? '15px 15px 0 15px' : '15px 15px 15px 0';

    const html = `
        <div style="display: flex; justify-content: ${align}; margin-bottom: 10px;">
            <div style="background: ${bg}; color: ${color}; padding: 8px 15px; border-radius: ${radius}; max-width: 70%; font-size: 14px; box-shadow: 0 2px 5px rgba(0,0,0,0.05);">
                ${data.text}
            </div>
        </div>
    `;

    chatWin.insertAdjacentHTML('beforeend', html);
    chatWin.scrollTop = chatWin.scrollHeight;
}