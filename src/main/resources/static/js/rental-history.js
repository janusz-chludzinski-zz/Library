$('.history-btn').click(function(event) {
    event.preventDefault();
    $(this).next().slideToggle("slow");
});