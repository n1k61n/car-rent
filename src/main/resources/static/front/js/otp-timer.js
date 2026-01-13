document.addEventListener("DOMContentLoaded", function() {
    // 5 dəqiqə = 300 saniyə
    let timeLeft = 300;
    const timerElement = document.getElementById('timer');
    const resendContainer = document.getElementById('resend-container');

    if (!timerElement || !resendContainer) {
        return;
    }

    const countdown = setInterval(() => {
        const minutes = Math.floor(timeLeft / 60);
        let seconds = timeLeft % 60;

        // Saniyə tək rəqəmlidirsə qarşısına 0 əlavə et (məs: 05:09)
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
