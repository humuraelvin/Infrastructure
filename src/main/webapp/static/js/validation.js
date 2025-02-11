// /webapp/static/js/validation.js

function validateRegistrationForm() {
    const email = document.getElementById('email').value;

    // Prevent form submission
    const form = document.getElementById('registrationForm');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        // Check if email exists using fetch API
        fetch('/check-email?email=' + email)
            .then(response => response.json())
            .then(data => {
                if (data.exists) {
                    alert('Email already registered!');
                } else {
                    form.submit();
                }
            });
    });
}