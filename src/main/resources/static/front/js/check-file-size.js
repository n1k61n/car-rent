    document.getElementById('licenseFile').addEventListener('change', function() {
                const file = this.files[0];
                const errorElement = document.getElementById('fileError');
                const submitBtn = document.querySelector('.btn-book-now');
                const maxSize = 10 * 1024 * 1024; // 10MB

                if (file) {
                    if (file.size > maxSize) {
                        // Xətanı göstər və düyməni sıradan çıxar
                        errorElement.style.display = 'block';
                        submitBtn.disabled = true;
                        this.classList.add('is-invalid'); // Vizual qırmızı çərçivə (Bootstrap)
                        this.value = ""; // Seçimi sıfırla
                    } else {
                        // Hər şey qaydasındadırsa, xətanı gizlə və düyməni aktiv et
                        errorElement.style.display = 'none';
                        submitBtn.disabled = false;
                        this.classList.remove('is-invalid');
                        this.classList.add('is-valid');
                    }
                }
            });