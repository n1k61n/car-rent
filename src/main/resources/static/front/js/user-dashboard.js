document.addEventListener("DOMContentLoaded", function() {
    // HTML-dəki ID-lərə əsasən elementləri seçirik
    const sidebar = document.querySelector(".sidebar");
    const sidebarToggle = document.getElementById("sidebarToggle");
    const overlay = document.getElementById("sidebarOverlay");

    if (sidebarToggle) {
        sidebarToggle.addEventListener("click", function() {
            // "active" sinfini əlavə edirik və ya silirik
            sidebar.classList.toggle("active");
            overlay.classList.toggle("active");
        });
    }

    // Arxa fon klikləndikdə menyunu bağla
    if (overlay) {
        overlay.addEventListener("click", function() {
            sidebar.classList.remove("active");
            overlay.classList.remove("active");
        });
    }
});