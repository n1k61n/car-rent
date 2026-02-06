(function() {
    // Encapsulate the entire script to avoid global scope issues
    // and ensure execution order.

    let stompClient = null;
    let currentUserName = null;
    let sessionId = null;
    let isAuth = false;
    let isConnecting = false; // Flag to prevent double connection attempts

    /**
     * Main initialization function, runs when the DOM is ready.
     */
    function init() {
        // 1. Read authentication data from the DOM
        const authUserNameInput = document.getElementById('authUserName');
        const authUserName = authUserNameInput ? authUserNameInput.value.trim() : null;
        const isAuthInput = document.getElementById('isAuth');
        isAuth = isAuthInput ? (isAuthInput.value === 'true') : false;

        // 2. Determine user identity and session ID
        if (isAuth && authUserName) {
            currentUserName = authUserName;
            sessionId = authUserName;
        } else {
            // For anonymous users, get or create a session ID from localStorage
            sessionId = localStorage.getItem('chat_session_id');
            if (!sessionId) {
                sessionId = "user_" + Math.random().toString(36).substr(2, 9);
                localStorage.setItem('chat_session_id', sessionId);
            }
        }

        console.log("Chat Initialized:", { isAuth, currentUserName, sessionId });

        // 3. Set up all event listeners
        initChatControls();

        // 4. If authenticated, show and connect the chat immediately
        if (isAuth && currentUserName) {
            const namePrompt = document.getElementById('namePromptScreen');
            const mainChat = document.getElementById('mainChatArea');
            if (namePrompt) namePrompt.style.display = 'none';
            if (mainChat) mainChat.style.display = 'flex';
            connectChat();
        }
    }

    /**
     * Attaches all necessary event listeners to the chat UI elements.
     */
    function initChatControls() {
        const chatToggleBtn = document.getElementById('chatToggleBtn');
        const chatInterface = document.getElementById('chatInterface');
        const chatCloseBtn = document.getElementById('chatCloseBtn');
        const startChatBtn = document.getElementById('startChatBtn');
        const sendBtn = document.getElementById('chatSendBtn');
        const chatInput = document.getElementById('chatInput');

        if (chatToggleBtn) {
            chatToggleBtn.onclick = () => {
                if (chatInterface) chatInterface.style.display = (chatInterface.style.display === 'none' || chatInterface.style.display === '') ? 'flex' : 'none';
            };
        }

        if (chatCloseBtn) {
            chatCloseBtn.onclick = () => {
                if (chatInterface) chatInterface.style.display = 'none';
            };
        }

        if (startChatBtn) {
            startChatBtn.onclick = () => {
                const nameInput = document.getElementById('anonymousUserName');
                if (nameInput && nameInput.value.trim() !== "") {
                    // Disable button to prevent double clicks
                    startChatBtn.disabled = true;
                    startChatBtn.innerText = "Qoşulur...";

                    currentUserName = nameInput.value.trim();
                    sessionId = currentUserName; // Use email as session ID
                    localStorage.setItem('chat_session_id', sessionId);

                    const namePrompt = document.getElementById('namePromptScreen');
                    const mainChat = document.getElementById('mainChatArea');
                    if (namePrompt) namePrompt.style.display = 'none';
                    if (mainChat) mainChat.style.display = 'flex';
                    connectChat();
                } else {
                    alert("Zəhmət olmasa emailinizi daxil edin.");
                }
            };
        }

        if (sendBtn) sendBtn.onclick = sendMessage;
        if (chatInput) {
            chatInput.onkeypress = (e) => {
                if (e.key === 'Enter') sendMessage();
            };
        }
    }

    /**
     * Establishes WebSocket connection and subscribes to topics.
     */
    function connectChat() {
        if (!sessionId) {
            console.error("Cannot connect chat: sessionId is missing.");
            return;
        }

        // Prevent multiple connections
        if (stompClient && stompClient.connected) {
            console.log("Chat already connected.");
            return;
        }
        if (isConnecting) {
            console.log("Chat connection already in progress.");
            return;
        }

        isConnecting = true;
        const socket = new SockJS('/ws-chat');
        stompClient = Stomp.over(socket);
        stompClient.debug = null;

        stompClient.connect({}, function (frame) {
            isConnecting = false;
            console.log("Connected to chat with sessionId: " + sessionId);

            stompClient.subscribe('/topic/user/' + sessionId, function (payload) {
                const message = JSON.parse(payload.body);
                showReceivedMessage(message);
            });

            fetchHistory();
        }, function(error) {
            isConnecting = false;
            console.error("STOMP connection error: " + error);
            // Re-enable start button if connection fails
            const startChatBtn = document.getElementById('startChatBtn');
            if (startChatBtn) {
                startChatBtn.disabled = false;
                startChatBtn.innerText = "Start Chat"; // Or localized text
            }
        });
    }

    /**
     * Fetches chat history from the server.
     */
    function fetchHistory() {
        if (!sessionId) return;
        fetch('/dashboard/chat/history/' + sessionId)
            .then(response => {
                if (!response.ok) throw new Error("History fetch failed with status: " + response.status);
                return response.json();
            })
            .then(messages => {
                const container = document.getElementById('chatMessages');
                if (container) {
                    container.innerHTML = '<div style="text-align: center;"><small style="color: #aaa;">Söhbət başladı</small></div>';
                    messages.forEach(msg => {
                        if (msg.from === "ADMIN" || msg.from === "AI") {
                            showReceivedMessage(msg);
                        } else {
                            showMyMessage(msg.content);
                        }
                    });

                    // If history is empty, send initial AI greeting
                    if (messages.length === 0) {
                        sendInitialAiGreeting();
                    }
                }
            })
            .catch(err => console.error("Could not fetch history:", err));
    }

    function sendInitialAiGreeting() {
        if (stompClient && stompClient.connected) {
            const chatMessage = {
                from: currentUserName,
                email: currentUserName,
                content: "Salam", // Initial trigger message
                to: "ADMIN",
                sessionId: sessionId
            };
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            // We don't show "Salam" in the UI to make it look like the AI started the conversation
        }
    }

    /**
     * Sends a chat message to the server.
     */
    function sendMessage() {
        const input = document.getElementById('chatInput');
        if (!input) return;
        const messageText = input.value.trim();

        if (messageText && stompClient && stompClient.connected) {
            const chatMessage = {
                from: currentUserName,
                email: currentUserName,
                content: messageText,
                to: "ADMIN",
                sessionId: sessionId
            };

            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            showMyMessage(messageText);
            input.value = '';
        } else {
            if (!stompClient || !stompClient.connected) {
                console.error("Cannot send message: Not connected to chat server.");
                alert("Çat serveri ilə əlaqə yoxdur. Zəhmət olmasa səhifəni yeniləyin.");
            }
        }
    }

    function showMyMessage(text) {
        const msgHtml = `<div style="align-self: flex-end; background: #0779e4; color: white; padding: 10px 15px; border-radius: 15px 15px 0 15px; max-width: 80%; font-size: 14px; box-shadow: 0 3px 10px rgba(7,121,228,0.2);">${text}</div>`;
        appendMessage(msgHtml);
    }

    function showReceivedMessage(msg) {
        let contentHtml = msg.content;

        // Check if there are recommended cars
        if (msg.recommendedCarIds && msg.recommendedCarIds.length > 0) {
            fetchCarDetailsAndAppend(msg, contentHtml);
            return; // Async handling
        }

        const msgHtml = `<div style="align-self: flex-start; background: white; color: #333; padding: 10px 15px; border-radius: 15px 15px 15px 0; max-width: 80%; font-size: 14px; border: 1px solid #eee; box-shadow: 0 3px 10px rgba(0,0,0,0.02);">${contentHtml}</div>`;
        appendMessage(msgHtml);
    }

    function fetchCarDetailsAndAppend(msg, contentHtml) {
        let carsHtml = '<div style="margin-top: 10px; display: flex; flex-direction: column; gap: 5px;">';
         msg.recommendedCarIds.forEach(id => {
           carsHtml += `<a href="/listing/car/${id}" target="_blank"
             style="display:block; padding:5px 10px; background:#f1f3f4; color:#0779e4; border-radius:5px; text-decoration:none; font-size:12px;">
             🚗 Avtomobilə bax (ID: ${id})
           </a>`;
         });
        carsHtml += '</div>';

        const msgHtml = `<div style="align-self: flex-start; background: white; color: #333; padding: 10px 15px; border-radius: 15px 15px 15px 0; max-width: 80%; font-size: 14px; border: 1px solid #eee; box-shadow: 0 3px 10px rgba(0,0,0,0.02);">
            <div>${contentHtml}</div>
            ${carsHtml}
        </div>`;
        appendMessage(msgHtml);
    }

    function appendMessage(html) {
        const container = document.getElementById('chatMessages');
        if (container) {
            container.insertAdjacentHTML('beforeend', html);
            container.scrollTop = container.scrollHeight;
        }
    }

    // Start the script once the DOM is fully loaded.
    document.addEventListener('DOMContentLoaded', init);

})();