// static/js/booking-validation.js
document.addEventListener('DOMContentLoaded', function() {
    // Bu günün tarixini alırıq
    const today = new Date().toISOString().split('T')[0];

    const startDateInput = document.querySelector('input[name="startDate"]');
    const endDateInput = document.querySelector('input[name="endDate"]');

    if (startDateInput && endDateInput) {
        // Minimum tarixi bu günə bərabər edirik
        startDateInput.setAttribute('min', today);

        startDateInput.addEventListener('change', function() {
            const selectedDate = this.value;

            // Qaytarma tarixi götürmə tarixindən tez ola bilməz
            endDateInput.setAttribute('min', selectedDate);

            if (endDateInput.value && endDateInput.value < selectedDate) {
                endDateInput.value = selectedDate;
            }
        });
    }
});