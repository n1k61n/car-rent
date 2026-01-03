document.addEventListener("DOMContentLoaded", function() {
    // URL-dən sort və dir parametrlərini alırıq
    const urlParams = new URLSearchParams(window.location.search);
    const sortField = urlParams.get('sort');
    const sortDir = urlParams.get('dir');

    if (sortField) {
        // th[data-field="..."] daxilindəki ikon elementini tapırıq
        const header = document.querySelector(`th[data-field="${sortField}"]`);
        if (header) {
            const icon = header.querySelector('i');
            if (icon) {
                // Köhnə neytral ikonu silirik
                icon.classList.remove('fa-sort', 'text-muted');

                // İstiqamətə uyğun yeni ikon və rəng əlavə edirik
                if (sortDir === 'asc') {
                    icon.classList.add('fa-sort-up', 'text-primary');
                } else {
                    icon.classList.add('fa-sort-down', 'text-primary');
                }
            }
        }
    }
});