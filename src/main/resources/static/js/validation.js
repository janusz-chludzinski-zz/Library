var NOT_EMPTY = "Pole nie może być puste";
var NOT_EMAIL = "Zły format email";
var NOT_SAME = "Podane hasła nie są takie same";

$("#submit").on("click", function(e) {
    if(isValidEmail() && isValidPassword()) {
        $("#login-form").submit();
    } else {
        e.preventDefault();
    }
});

$("#registration-submit").on("click", function(e) {
    if( isValidEmail()
        && isValidPassword()
        && doPasswordsMatch()
        && isNamePresent()
        && isSurnamePresent()
        && isDateOfBirthPresent()
        && isGenderPresent())
    {
        $("#registration-form").submit();
    }
    else {
        e.preventDefault();
    }
});

function isNamePresent() {
    var field = $("#name");
    var error = field.next();
    var value = field.val();

    hideErrorOnFocus(field, error);

    if(value === "") {
        handleValidation(NOT_EMPTY, error);
        return false;
    }

    return true;
}

function isSurnamePresent() {
    var field = $("#surname");
    var error = field.next();
    var value = field.val();

    hideErrorOnFocus(field, error);

    if(value === "") {
        handleValidation(NOT_EMPTY, error);
        return false;
    }

    return true;
}

function isDateOfBirthPresent() {
    var field = $("#dob");
    var error = field.next();
    var value = field.val();

    hideErrorOnFocus(field, error);

    if(value === "") {
        handleValidation(NOT_EMPTY, error);
        return false;
    }

    return true;
}

function isGenderPresent() {
    var field = $("#gender");
    var error = field.next();
    var value = field.val();

    hideErrorOnFocus(field, error);

    if(value === "") {
        handleValidation(NOT_EMPTY, error);
        return false;
    }

    return true;
}

function doPasswordsMatch() {
    var field = $("#password");
    var error = field.next();
    var value = field.val();
    var passwordRepeat = $("#password-repeat").val();

    hideErrorOnFocus(field, error);

    if(value === "") {
        handleValidation(NOT_EMPTY, error);
        return false;
    } else if(value !== passwordRepeat) {
        handleValidation(NOT_SAME, error);
        return false;
    }

    return true;
}

function isValidEmail() {
    var field = $("#email");
    var error = field.next();
    var value = field.val();

    hideErrorOnFocus(field, error)

    if(value === "") {
        handleValidation(NOT_EMPTY, error);
        return false;

    } else if(!isEmail(value)) {
        handleValidation(NOT_EMAIL, error);
        return false;
    }

    return true;
}

function isValidPassword() {
    var field = $("#password");
    var error = field.next();
    var value = field.val();

    hideErrorOnFocus(field, error)

    if(value === "") {
        handleValidation(NOT_EMPTY, error);
        return false;
    }

    return true;
}

function isEmail(email) {
    var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return regex.test(email);
}

function handleValidation(errorMessage, errorField) {
    errorField.text(errorMessage);
    show(errorField);
    setTimeout(function () {
        hide(errorField);
    }, 5000)
}

function hide(field) {
    field.slideUp();
}

function show(field) {
    field.slideDown();
}

function hideErrorOnFocus(field, error) {
    field.focus(function() {
        hide(error);
    });
}
