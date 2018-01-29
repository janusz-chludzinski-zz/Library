$('#multipartImage').on('change', function(event) {
    var output = document.getElementById('book-cover');
    output.src = URL.createObjectURL(event.target.files[0]);
});

$('#avatar').on('change', function(event) {
    var output = document.getElementById('avatar-image');
    output.src = URL.createObjectURL(event.target.files[0]);
});