function loadAreaChart(labels, data) {
    const ctx = document.getElementById("myAreaChart");

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: "Aylıq Qazanc (₼)",
                data: data,
                fill: true,
                tension: 0.4,
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: true }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}