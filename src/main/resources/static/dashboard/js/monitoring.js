// monitoring.js

document.addEventListener("DOMContentLoaded", function() {
    const ctx = document.getElementById('memoryChart').getContext('2d');

    // Chart.js obyektini yaradırıq
    const memoryChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [],
            datasets: [{
                label: 'İstifadə olunan RAM (MB)',
                data: [],
                backgroundColor: 'rgba(78, 115, 223, 0.05)',
                borderColor: 'rgba(78, 115, 223, 1)',
                pointRadius: 3,
                pointBackgroundColor: 'rgba(78, 115, 223, 1)',
                pointBorderColor: 'rgba(78, 115, 223, 1)',
                pointHoverRadius: 3,
                pointHoverBackgroundColor: 'rgba(78, 115, 223, 1)',
                pointHoverBorderColor: 'rgba(78, 115, 223, 1)',
                pointHitRadius: 10,
                pointBorderWidth: 2,
                fill: true,
            }]
        },
        options: {
            maintainAspectRatio: false,
            scales: {
                x: { time: { unit: 'date' }, grid: { display: false, drawBorder: false } },
                y: { ticks: { maxTicksLimit: 5, padding: 10 }, grid: { color: "rgb(234, 236, 244)", zeroLineColor: "rgb(234, 236, 244)", drawBorder: false, borderDash: [2], zeroLineBorderDash: [2] } }
            },
            plugins: { legend: { display: false } }
        }
    });

    function updateMetrics() {
        // Server Sağlamlığını Yoxla
        fetch('/actuator/health')
            .then(res => res.json())
            .then(data => {
                const statusEl = document.getElementById('serverStatus');
                statusEl.innerText = data.status;
                statusEl.className = data.status === 'UP' ? 'h5 mb-0 font-weight-bold text-success' : 'h5 mb-0 font-weight-bold text-danger';
            })
            .catch(err => console.error("Health fetch error:", err));

        // RAM Metrikalarını Çək
        fetch('/actuator/metrics/jvm.memory.used')
            .then(res => res.json())
            .then(data => {
                const usedMB = (data.measurements[0].value / 1024 / 1024).toFixed(2);
                const now = new Date().toLocaleTimeString();

                // Qrafikdə maksimum 15 nöqtə saxla
                if(memoryChart.data.labels.length > 15) {
                    memoryChart.data.labels.shift();
                    memoryChart.data.datasets[0].data.shift();
                }

                memoryChart.data.labels.push(now);
                memoryChart.data.datasets[0].data.push(usedMB);
                memoryChart.update();
            })
            .catch(err => console.error("Metrics fetch error:", err));
    }

    // Hər 5 saniyədən bir yenilə
    setInterval(updateMetrics, 5000);
    updateMetrics(); // İlk yüklənmədə çalışdır
});