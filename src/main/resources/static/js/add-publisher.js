$('#multipartImage').on('change', function(event) {
    var output = document.getElementById('book-cover');
    output.src = URL.createObjectURL(event.target.files[0]);
});