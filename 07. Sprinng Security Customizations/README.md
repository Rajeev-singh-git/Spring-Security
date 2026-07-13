# 🔐 Section 7 — Spring Security Customizations

---

# 🎯 What You'll Learn

This section moves from **REST API security** to **browser-based web application security** by covering:

- HTTPS (Channel Security)
- Security Exception Handling
- Session Management
- Authentication Events
- Form Login & Logout
- Thymeleaf Integration
- SecurityContext & SecurityContextHolder

---

# 📂 Project Structure

This section uses **two applications** because the remaining topics target different types of clients.

## 1. REST-Security-Customizations

A REST API project that continues from the previous sections.

**Topics Covered**

- 7.1 Enforcing HTTPS (Channel Security)
- 7.2 Exception Handling
- 7.3 Session Management
- 7.4 Authentication Events

**Client**

- REST APIs
- Postman

---

## 2. EazySchool

A Spring MVC + Thymeleaf application used to demonstrate browser-based authentication.

```
EazySchool
├── before-customization
└── after-customization
```

### before-customization

The application before any Spring Security UI customization.

Use this project if you want to practice the implementation yourself.

### after-customization

The completed implementation with all customizations applied.

Use this project as a reference while revising or comparing your implementation.

**Topics Covered**

- 7.5 Form Login
- 7.6 Form Logout
- 7.7 Thymeleaf Integration
- 7.8 SecurityContext & SecurityContextHolder

---

# 📚 Section Roadmap

## 7.1 🔒 Enforcing HTTPS

- Force HTTP → HTTPS
- Secure communication before authentication

---

## 7.2 🚨 Exception Handling

- `AuthenticationEntryPoint` (401)
- `AccessDeniedHandler` (403)
- Custom responses for REST APIs

---

## 7.3 🧠 Session Management

- Session timeout
- Concurrent sessions
- Session fixation protection
- Invalid session handling

---

## 7.4 📢 Authentication Events

- Authentication success & failure events
- Audit logging
- Security monitoring
- Event listeners

---

## 7.5 🔑 Form Login

- Custom login page
- Login success & failure handling
- Browser-based authentication

---

## 7.6 🚪 Form Logout

- Custom logout
- Session invalidation
- Cookie cleanup
- Logout success handling

---

## 7.7 🎨 Thymeleaf Integration

- Authentication-aware UI
- `sec:authorize`
- Show/Hide UI elements based on authentication

---

## 7.8 🔐 SecurityContext & SecurityContextHolder

- SecurityContext architecture
- Accessing the authenticated user
- `SecurityContextHolder`
- ThreadLocal strategy

---

# ✅ After This Section

You'll be able to:

- Secure both REST APIs and MVC applications.
- Customize login, logout, and security exception handling.
- Manage sessions securely.
- Build authentication-aware UIs with Thymeleaf.
- Access the authenticated user using `SecurityContextHolder`.
