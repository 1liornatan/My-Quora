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
  fetch("/api/auth/register", {
    method: "POST",
    body: JSON.stringify(formData),
    headers: {
      "Content-Type": "application/json"
    }
  })
  .then(response => {
    if (response.ok) {
      // Redirect to the login page
      window.location.href = "/api/auth/login";
    } else {
      // Display an error message
      alert("Registration failed");
    }
  })
  .catch(error => {
    console.error(error);
  });
}