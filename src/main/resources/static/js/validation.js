var NOT_EMPTY = "Pole nie może być puste";


function validateEmail() {
    var field = $("#email");
    var error = field.next();
    var value = field.val();

    field.focus(function() {
        hide(error);
    });

    if(value === "") {
        error.text(NOT_EMPTY);
        show(error);
        setTimeout(function () {
            hide(error);
        }, 5000)
    }
}

function hide(field) {
    field.slideUp();
}

function show(field) {
    field.slideDown();
}
