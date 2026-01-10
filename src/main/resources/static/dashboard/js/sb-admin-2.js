(function($) {
  "use strict";

  // --- KRİTİK HİSSƏ: Səhifə yüklənən kimi sıçrayışı önləmək üçün ---
  // jQuery-nin tam yüklənməsini gözləmədən yaddaşı yoxlayırıq
  if (localStorage.getItem('sidebar-state') === 'toggled') {
    document.documentElement.classList.add('sidebar-toggled');
    // Sidebar elementi HTML-də görünən kimi dərhal klası əlavə edirik
    var checkExist = setInterval(function() {
       var sidebar = document.querySelector('.sidebar');
       if (sidebar) {
          sidebar.classList.add('toggled');
          clearInterval(checkExist);
       }
    }, 1); // 1ms-lik yoxlanış
  }

  // Normal jQuery funksiyaları başlayır
  $(function() {
    // Əgər yaddaşda varsa, Bootstrap-ın collapse funksiyalarını təmizləyirik
    if (localStorage.getItem('sidebar-state') === 'toggled') {
      $('.sidebar .collapse').collapse('hide');
    }

    // Toggle düymələri
    $("#sidebarToggle, #sidebarToggleTop").on('click', function(e) {
      $("body").toggleClass("sidebar-toggled");
      $(".sidebar").toggleClass("toggled");

      if ($(".sidebar").hasClass("toggled")) {
        localStorage.setItem('sidebar-state', 'toggled');
        $('.sidebar .collapse').collapse('hide');
      } else {
        localStorage.setItem('sidebar-state', 'expanded');
      }
    });
  });

  // Digər orijinal SB Admin kodları (Resize, Scroll və s.)
  $(window).resize(function() {
    if ($(window).width() < 768) {
      $('.sidebar .collapse').collapse('hide');
    }
    if ($(window).width() < 480 && !$(".sidebar").hasClass("toggled")) {
      $("body").addClass("sidebar-toggled");
      $(".sidebar").addClass("toggled");
      $('.sidebar .collapse').collapse('hide');
    }
  });

  $('body.fixed-nav .sidebar').on('mousewheel DOMMouseScroll wheel', function(e) {
    if ($(window).width() > 768) {
      var e0 = e.originalEvent, delta = e0.wheelDelta || -e0.detail;
      this.scrollTop += (delta < 0 ? 1 : -1) * 30;
      e.preventDefault();
    }
  });

  $(document).on('scroll', function() {
    if ($(this).scrollTop() > 100) {
      $('.scroll-to-top').fadeIn();
    } else {
      $('.scroll-to-top').fadeOut();
    }
  });

  $(document).on('click', 'a.scroll-to-top', function(e) {
    var $anchor = $(this);
    $('html, body').stop().animate({
      scrollTop: ($($anchor.attr('href')).offset().top)
    }, 1000, 'easeInOutExpo');
    e.preventDefault();
  });

})(jQuery);