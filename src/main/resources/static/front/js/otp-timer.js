document.addEventListener("DOMContentLoaded", function() {
    let timeLeft = 120;
    const timerElement = document.getElementById('timer');
    const resendContainer = document.getElementById('resend-container');

    if (!timerElement || !resendContainer) {
        return;
    }

    const countdown = setInterval(() => {
        const minutes = Math.floor(timeLeft / 60);
        let seconds = timeLeft % 60;

        seconds = seconds < 10 ? '0' + seconds : seconds;

        timerElement.innerHTML = `0${minutes}:${seconds}`;

        if (timeLeft <= 0) {
            clearInterval(countdown);
            timerElement.innerHTML = "00:00";
            timerElement.classList.remove("text-danger");
            timerElement.classList.add("text-muted");

            // Vaxt bitdikdə "Yenidən göndər" linkini göstər
            resendContainer.style.display = 'block';
        } else {
            timeLeft--;
        }
    }, 1000);
});
