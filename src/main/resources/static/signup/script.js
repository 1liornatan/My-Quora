function submitForm() {
  // Get the form data
  const formData = {
    first_name: document.getElementById("first-name").value,
    last_name: document.getElementById("last-name").value,
    username: document.getElementById("username").value,
    password: document.getElementById("password").value,
    email: document.getElementById("email").value
  };

  // Send the form data as JSON to the server
  fetch("/api/auth/signup", {
    method: "POST",
    body: JSON.stringify(formData),
    headers: {
      "Content-Type": "application/json"
    }
  })
  .then(response => {
    if (response.ok) {
      // Redirect to the login page
      window.location.href = "/login.html";
    } else {
      // Display an error message
      response.text().then(text => { alert(text) })
    }
  })
  .catch(error => {
    console.error(error);
  });
}
