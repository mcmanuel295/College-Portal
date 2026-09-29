const API_URL = "http://localhost:8082/api/courses";

// 

async function submitEmail(event) {
    event.preventDefault();
    const email = {
        "email": document.getElementById('email').value
    }
    
    try{
        const response = await fetch("http://localhost:8080/api/v1/students/activate", {
            method: 'POST', headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(email)    
        });
    
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

        const data = await response.json();
        console.log('Success POST:', data); // Returns created object with a new ID
        Window.location.href = "./verify-email.html"; // Redirect to verify email page
  } catch (error) {
    console.error('Error posting data:', error);
  }
}


postData();


// 1. GET Request: Fetch courses from Spring Boot
async function loadCourses() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Server error");
        
        const courses = await response.json();
        const listElement = document.getElementById("course-list");
        
        listElement.innerHTML = courses.map(course => `<li>${course}</li>`).join("");
    } catch (error) {
        console.error("Fetch failed:", error);
        document.getElementById("course-list").innerHTML = "<li>Failed to load courses.</li>";
    }
}

// 2. POST Request: Send data to Spring Boot
async function addNewCourse() {
    const input = document.getElementById("course-input");
    const courseName = input.value.trim();
    if (!courseName) return;

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(courseName)
        });

        if (response.ok) {
            input.value = ""; // Clear input
            loadCourses();    // Refresh list
        }
    } catch (error) {
        console.error("Post failed:", error);
    }
}

// Event Listeners
document.getElementById("add-btn").addEventListener("click", addNewCourse);
window.addEventListener("DOMContentLoaded", loadCourses);
