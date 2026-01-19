// Pie Chart - Booking Status Distribution
if (document.getElementById("myPieChart")) {

    // Buraya DB-dən gələn data əlavə et
    const statusLabels = ["APPROVED", "PENDING", "CANCELLED"]; // Məsələn
    const statusData = [12, 5, 3]; // Məsələn

    const ctx = document.getElementById("myPieChart");

    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: statusLabels,
            datasets: [{
                data: statusData,
                backgroundColor: [
                    '#1cc88a', // APPROVED → Yaşıl
                    '#f6c23e', // PENDING → Sarı
                    '#e74a3b'  // CANCELLED → Qırmızı
                ],
                hoverBackgroundColor: [
                    '#17a673',
                    '#d4ac1f',
                    '#c0392b'
                ],
                hoverBorderColor: "rgba(234, 236, 244, 1)"
            }]
        },
        options: {
            maintainAspectRatio: false,
            cutout: '80%',
            plugins: {
                legend: {
                    display: true,
                    position: 'bottom',
                    labels: {
                        color: '#858796',
                        font: {
                            family: 'Nunito, -apple-system, system-ui, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'
                        }
                    }
                },
                tooltip: {
                    backgroundColor: "rgb(255,255,255)",
                    bodyColor: "#858796",
                    borderColor: '#dddfeb',
                    borderWidth: 1,
                    padding: 15,
                    displayColors: false,
                    callbacks: {
                        label: function(context) {
                            const label = context.label || '';
                            const value = context.parsed;
                            const total = context.chart._metasets[context.datasetIndex].total;
                            const percentage = ((value / total) * 100).toFixed(1) + '%';
                            return label + ': ' + value + ' (' + percentage + ')';
                        }
                    }
                }
            }
        }
    });
}
