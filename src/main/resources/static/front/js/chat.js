document.addEventListener('DOMContentLoaded', function() {
    var chatInterface = document.getElementById('chatInterface');
    var chatToggleBtn = document.getElementById('chatToggleBtn');
    var chatCloseBtn = document.getElementById('chatCloseBtn');
    var chatSendBtn = document.getElementById('chatSendBtn');
    var chatInput = document.getElementById('chatInput');
    var chatMessages = document.getElementById('chatMessages');
    var stompClient = null;
    var username = null;

    if (chatToggleBtn) {
        chatToggleBtn.addEventListener('click', function() {
            if (chatInterface.style.display === 'none' || chatInterface.style.display === '') {
                chatInterface.style.display = 'flex';
                connect();
            } else {
                chatInterface.style.display = 'none';
                disconnect();
            }
        });
    }

    if (chatCloseBtn) {
        chatCloseBtn.addEventListener('click', function() {
            chatInterface.style.display = 'none';
            disconnect();
        });
    }

    chatSendBtn.addEventListener('click', function() {
        sendMessage();
    });

    function connect() {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
    }

    function onConnected() {
        stompClient.subscribe('/topic/public', onMessageReceived);
    }

    function onError(error) {
        console.log('Could not connect to WebSocket server. Please refresh this page to try again!');
    }

    function sendMessage() {
        var messageContent = chatInput.value.trim();
        if(messageContent && stompClient) {
            var chatUserNameElement = document.getElementById('chatUserName');
            if (chatUserNameElement) {
                username = chatUserNameElement.textContent;
            } else {
                var anonymousUserNameElement = document.getElementById('anonymousUserName');
                if (anonymousUserNameElement) {
                    username = anonymousUserNameElement.value;
                }
            }

            var chatMessage = {
                from: username,
                text: messageContent
            };
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));

            // Display the message immediately
            var messageElement = document.createElement('div');
            messageElement.style.marginBottom = '10px';
            messageElement.style.textAlign = 'right';

            var messageText = document.createElement('div');
            messageText.style.padding = '10px';
            messageText.style.borderRadius = '10px';
            messageText.style.display = 'inline-block';
            messageText.style.backgroundColor = '#dcf8c6';
            messageText.innerText = 'You: ' + messageContent;

            messageElement.appendChild(messageText);
            chatMessages.appendChild(messageElement);
            chatMessages.scrollTop = chatMessages.scrollHeight;

            chatInput.value = '';
        }
    }

    function onMessageReceived(payload) {
        var message = JSON.parse(payload.body);
        var messageElement = document.createElement('div');
        messageElement.style.marginBottom = '10px';

        var messageText = document.createElement('div');
        messageText.style.padding = '10px';
        messageText.style.borderRadius = '10px';
        messageText.style.display = 'inline-block';

        if(message.from === username) {
            // This is a message from the current user, but it's already displayed.
            // You might want to add a check here to avoid displaying it twice.
        } else {
            messageText.style.backgroundColor = '#fff';
            messageText.innerText = message.from + ': ' + message.text;
            messageElement.appendChild(messageText);
            chatMessages.appendChild(messageElement);
            chatMessages.scrollTop = chatMessages.scrollHeight;
        }
    }
});