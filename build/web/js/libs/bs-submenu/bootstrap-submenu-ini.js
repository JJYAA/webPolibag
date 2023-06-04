$(function() {


  // Dropdown fix
  $('.dropdown > a[tabindex]').on('keydown', function(event) {
    // 13: Return

    if (event.keyCode === 13) {
      $(this).dropdown('toggle');
    }
  });

  $('[data-submenu]').submenupicker();
});


