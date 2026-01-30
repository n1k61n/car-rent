$(document).ready(function() {
    connectNotifications();
});

function connectNotifications() {
    const socket = new SockJS('/ws-chat');
    const stompClient = Stomp.over(socket);
    stompClient.debug = null;

    stompClient.connect({}, function(frame) {
        console.log('Notification service connected');

        stompClient.subscribe('/topic/notifications', function(payload) {
            const notification = JSON.parse(payload.body);
            handleNewNotification(notification);
        });
    });
}

function handleNewNotification(notification) {
    // 1. Update counter
    const counter = $('#alertsDropdown .badge-counter');
    const currentCount = parseInt(counter.text() || '0');
    counter.text(currentCount + 1);
    counter.show();

    // 2. Add to dropdown list
    const dropdown = $('#alertsDropdown').next('.dropdown-list');
    // Construct the correct "mark as read" link
    const readLink = `/dashboard/notifications/read/${notification.id}`;
    const notificationHtml = `
        <a class="dropdown-item d-flex align-items-center" href="${readLink}">
            <div class="mr-3">
                <div class="icon-circle bg-primary">
                    <i class="fas fa-comment-dots text-white"></i>
                </div>
            </div>
            <div>
                <div class="small text-gray-500">${new Date(notification.createdAt).toLocaleDateString()}</div>
                <span class="font-weight-bold">${notification.message}</span>
            </div>
        </a>
    `;
    dropdown.find('.dropdown-header').after(notificationHtml);

    // 3. Play a sound
//    const audio = new Audio('https://assets.mixkit.co/active_storage/sfx/2869/2869-preview.mp3');
//    audio.play().catch(e => console.log("Could not play notification sound.", e));
}
