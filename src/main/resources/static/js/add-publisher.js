$('#publishers').on('change', function(event) {
    event.preventDefault();
    var newPublisher = $(this).next();

    if(this.value === "Dodaj nowy") {
        newPublisher.slideDown("slow");
    } else {
        newPublisher.slideUp("slow");
    }
});

$('#multipartImage').on('change', function(event) {
    var output = document.getElementById('book-cover');
    output.src = URL.createObjectURL(event.target.files[0]);
});