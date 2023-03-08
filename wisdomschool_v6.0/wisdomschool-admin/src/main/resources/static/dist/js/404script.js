$(function () {

    setInterval(function () {
        $('#pacman').toggleClass('pacman_eats');
    }, 300);

    // speed in milliseconds
    let scrollSpeed = 20;

    let bgscroll = '';

    // set the direction
    let direction = 'h';

    // set the default position
    let current = 0;

    //Calls the scrolling function repeatedly
    setInterval(function () {

        // 1 pixel row at a time
        current -= 1;

        // move the background with backgrond-position css properties
        $('body').css("backgroundPosition", (direction == 'h') ? current + "px 0" : "0 " + current + "px");
    }, scrollSpeed);

});