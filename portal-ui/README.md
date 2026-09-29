# University Portal - Frontend

A comprehensive web-based portal system designed for University of the Learned, enabling seamless interaction between students, lecturers, administrators, and alumni through an intuitive user interface.

## Overview

The University Portal Frontend provides a modern, responsive web application that serves as the primary interface for multiple user roles within the institution. The platform facilitates course registration, grade management, resource sharing, and administrative operations through role-based access control.

## Features

### User Management
- **Student Portal** - Access course registration, view grades, update academic information
- **Lecturer Dashboard** - Manage courses, view enrolled students, submit grades
- **Administrator Panel** - User management, course administration, system oversight
- **Alumni Network** - Resource sharing, institutional support, donation management

### Authentication & Authorization
- Multi-step email verification process
- OTP-based account verification
- Role-based access control (Student/Lecturer/Admin)
- Password recovery and reset functionality
- Secure session management

### Core Functionality
- **Course Management** - Registration, enrollment tracking, grade submission
- **User Profiles** - Editable academic and personal information
- **Dashboard Analytics** - Statistics, activity tracking, performance metrics
- **Responsive Design** - Full compatibility across desktop, tablet, and mobile devices

## frontend Project Structure

```
portal-ui/
├── public/               # Landing page & shared components
│   ├── footer.css
│   ├── head.css
│   ├── head.js
│   └── home.html
├── auth/                 # Authentication & registration
│   ├── email-submit.html      # Email verification entry point
│   ├── otp.html               # OTP verification
│   ├── register.html          # Registration form
│   ├── student.html           # Student login
│   ├── lecturer.html          # Lecturer login
│   ├── admin.html             # Admin login
│   ├── verify-email.html      # Email verification confirmation
│   ├── activate.html          # Account activation
│   ├── forgot-password.html   # Password recovery
│   ├── reset-password.html    # Password reset
│   ├── auth-flow.css
│   ├── register.css
│   ├── student.css
│   ├── lecturer.css
│   └── admin.css
├── student/              # Student dashboard
│   ├── student.html      # Login page
│   ├── studAcc.html      # Student dashboard
│   ├── student.css
│   ├── studAcc.css
│   └── studAcc.js
├── lecturer/             # Lecturer dashboard
│   ├── lecturer.html     # Login page
│   ├── lecturerAcc.html  # Lecturer dashboard
│   ├── lecturer.css
│   ├── lecturerAcc.css
│   └── lecturerAcc.js
├── admin/                # Administrator dashboard
│   ├── admin.html        # Login page
│   ├── adminAcc.html     # Admin dashboard
│   └── admin.css
├── home/                 # Landing page
│   ├── home.html
│   └── home.css
├── file/                 # Shared assets (images, media)
│   ├── learned.png
│   ├── facebook.png
│   ├── twitter.png
│   ├── user*.png
│   └── [other media assets]
└── README.md
```

## Technology Stack

- **HTML5** - Semantic markup and form structures
- **CSS3** - Advanced layouts (Grid, Flexbox), responsive design, CSS variables for theming
- **JavaScript (Vanilla)** - Client-side interactivity, form validation, session management
- **Java** - Backend, business logic and data binding
- **Responsive Design** - Breakpoints: 1024px, 900px, 720px, 480px

## Registration Flow

The registration process follows a three-step verification and onboarding sequence:

1. **Email Submission** (`email-submit.html`)
	- User enters email address
	- System initiates verification process

2. **OTP Verification** (`otp.html`)
	- 6-digit one-time password sent to email
	- User enters OTP with auto-focus digit navigation
	- 60-second resend countdown timer

3. **Account Registration** (`register.html`)
	- Tab-based form selection (Student/Lecturer)
	- Pre-filled verified email (read-only)
	- Comprehensive user information collection
	- Account creation and activation

## Design System

### Color Palette
- Primary: #0284c7 (Blue)
- Accent: #0f766e (Teal)
- Administrative: #4f46e5 (Purple)
- Success: #22c55e (Green)
- Warning: #f59e0b (Amber)

### Typography
- Font Family: Inter, system-ui, -apple-system, Segoe UI
- Responsive scales across breakpoints

### Component Library
- Unified card layouts with consistent shadows and borders
- Standardized button styles (primary, secondary, link)
- Form inputs with focus states and validation styling
- Navigation menus with dropdown support

## Getting Started

### Prerequisites
- Modern web browser (Chrome, Firefox, Safari, Edge)
- Local or remote web server with PHP support
- Database backend (implementation pending)

### Installation

1. Clone the repository
	```bash
	git clone <repository-url>
	```

2. Navigate to the project directory
	```bash
	cd portal-ui
	```

3. Configure your web server to serve files from this directory

4. Access the application through your web browser
	```
	http://localhost/portal-ui/home/home.html
	```

## Usage

### For End Users

**Students:**
- Navigate to the landing page
- Click "Register Now" to create an account
- Log in with credentials via `student.html`
- Access dashboard at `studAcc.html`

**Lecturers:**
- Select "Lecturer Login" from the Access menu
- Register or log in with credentials
- Access management dashboard at `lecturerAcc.html`

**Administrators:**
- Select "Admin Portal" from the Access menu
- Log in with administrative credentials
- Access control panel at `adminAcc.html`

### For Developers

#### File Organization
All files are organized by functionality:
- **public/** - Shared pages and header/footer components
- **auth/** - Authentication and account management pages
- **student/**, **lecturer/**, **admin/** - Role-specific dashboards
- **file/** - Static assets accessed across all modules

#### CSS Architecture
CSS uses CSS custom properties (variables) for theming:
```css
:root {
	 --primary: #0284c7;
	 --text: #0f172a;
	 --muted: #475569;
	 --bg: #f8fafc;
	 /* ... additional variables */
}
```

#### JavaScript Patterns
- Form validation and submission handling
- Session storage for multi-step processes
- Tab switching for tabbed interfaces
- Auto-focus navigation for OTP input

## Navigation Map

```
home.html
├── student.html (login)
│   └── studAcc.html (dashboard)
├── lecturer.html (login)
│   └── lecturerAcc.html (dashboard)
├── admin.html (login)
│   └── adminAcc.html (dashboard)
└── email-submit.html (registration start)
	 └── otp.html (verification)
		  └── register.html (account creation)
				├── studAcc.html (student path)
				└── lecturerAcc.html (lecturer path)
```

## Backend Integration

Currently, the frontend contains placeholder logic for:
- Form submission handlers
- Email verification processing
- User authentication
- Dashboard data population

**Required Backend Implementations:**
- User authentication and session management
- Email service for OTP and verification links
- Database schema for users, courses, grades
- RESTful API endpoints for CRUD operations
- Password hashing and security protocols
- CSRF token validation

## Browser Compatibility

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+
- Mobile browsers (iOS Safari, Chrome Mobile)

## Performance Considerations

- Lazy loading for images and media
- CSS Grid/Flexbox for efficient layouts
- Minimal JavaScript dependencies
- Optimized asset delivery (file/ directory)

## Security Notes

- All passwords must be hashed server-side
- CSRF protection required on all state-changing operations
- Rate limiting on authentication endpoints
- Input validation and sanitization required
- Secure session cookies with HttpOnly flag

## Future Enhancements

- Progressive Web App (PWA) capabilities
- Real-time notifications
- Advanced analytics dashboard
- Integration with external services
- Accessibility improvements (WCAG 2.1 AA compliance)
- Internationalization (i18n) support

## Contact & Support

For technical support or inquiries, please contact:
- **Developer:** [Development Team]
- **Institution:** University of the Learned
- **Email:** [support email]

## License

All rights reserved. University of the Learned © 2026

---

**Last Updated:** July 3, 2026
**Version:** 1.0 (Beta)
