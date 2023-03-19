function submitForm() {
  // Get the form data
  const formData = {
    username: document.getElementById("username").value,
    password: document.getElementById("password").value
  };

  // Send the form data as JSON to the server
  fetch("/api/auth/signin", {
    method: "POST",
    body: JSON.stringify(formData),
    headers: {
      "Content-Type": "application/json"
    }
  })
  .then(response => {
    if (response.ok) {
      // Redirect to the dashboard page
      window.location.href = "/api/user/hello";
    } else {
      // Display an error message
      alert("Invalid username or password");
    }
  })
  .catch(error => {
    console.error(error);
  });
}
