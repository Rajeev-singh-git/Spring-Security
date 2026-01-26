# 🔐 Section 7 — Spring Security Customization for most common use cases

---

### HTTPS, Exception Handling, Sessions, Form Login, UI Integration & SecurityContext

> **Purpose of this README**  
> This file is a **high-signal summary** of Section 7.  
> Use it for **quick revision, interviews, or context reload** — not for deep study (that’s what Notion is for).

---

## 🎯 What This Section Teaches (Big Picture)

This section shifts Spring Security from **API-only security** to **real-world web (MVC / monolithic) security**, covering:

- Transport security (HTTPS)

- Authentication & authorization failures

- Session behavior

- Login / logout UX

- UI integration (Thymeleaf)

- SecurityContext internals & access

---

## 7.1 🔒 Enforcing HTTPS (Channel Security)

**What**

- Force all requests to use HTTPS

- Automatically redirect HTTP → HTTPS

**Why**

- Prevent credential leakage

- Mandatory for production systems

**Key idea**

- Channel security works **before authentication**

- Transport-level protection, not application logic

---

## 7.2 🚨 Exception Handling in Spring Security

**Problem**

- Authentication & authorization failures are different

- UI and APIs need different responses

**Key components**

- `AuthenticationEntryPoint` → **401 / unauthenticated**

- `AccessDeniedHandler` → **403 / unauthorized**

**Used for**

- Custom error pages (MVC)

- Custom JSON responses (APIs)

- Centralized security error control

**Mental model**

```java
Not authenticated → EntryPoint (401) 
Authenticated but forbidden → AccessDeniedHandler (403)
```

---

## 7.3 🧠 Session Management in Spring Security

**What Spring Security manages**

- Session creation

- Session reuse

- Concurrent sessions

- Session fixation protection

**Important concepts**

- Authentication is stored in **session**

- SecurityContext is restored per request

- Session ≠ cookie (cookie only carries session id)

**Why it matters**

- Prevent session hijacking

- Control concurrent logins

- Essential for form-login apps

---

## 7.4 📢 Authentication Events

**What**

- Spring Security publishes events during auth lifecycle

**Common events**

- Authentication success

- Authentication failure

**Why useful**

- Audit logs

- Security monitoring

- Alerts (email, SIEM, etc.)

**Key insight**

- Events are **decoupled from auth logic**

- Clean way to observe security behavior

---

## 7.5 🔑 Form Login Configuration

**Why Form Login**

- Required for MVC / monolithic apps

- Browser-based authentication

### Two configuration styles

#### 1️⃣ URL-based (simple)

```java
formLogin() 
    .loginPage("/login") 
    .defaultSuccessUrl("/dashboard")
    .failureUrl("/login?error=true");
```

#### 2️⃣ Handler-based (real projects)

- `AuthenticationSuccessHandler`

- `AuthenticationFailureHandler`

**Why handlers matter**

- Full programmatic control

- Logging, auditing, redirection

- Preferred in production systems

---

## 7.6 🚪 Logout Configuration (Clean Logout)

**What was implemented**

- Custom logout message

- Session invalidation

- SecurityContext cleanup

- Cookie deletion

```java
logout()
   .logoutSuccessUrl("/login?logout=true") 
   .invalidateHttpSession(true) 
   .clearAuthentication(true)
   .deleteCookies("JSESSIONID");
```

**Why this matters**

- Prevents session reuse

- Ensures complete logout

- Security best practice

---

## 7.7 🎨 Thymeleaf + Spring Security Integration

**Problem**

- UI must change based on authentication state

**Solution**

- Spring Security Thymeleaf Dialect

**Common expressions**

```java
sec:authorize="isAnonymous()"
sec:authorize="isAuthenticated()" 
sec:authorize="hasRole('ADMIN')"
```

**Result**

- Anonymous users → Home, Login

- Authenticated users → Dashboard, Logout

**Why important**

- Clean UX

- Secure UI rendering

- Common in legacy & MVC apps

---

## 7.8 🔐 SecurityContext & SecurityContextHolder

### Core hierarchy

```java
Authentication
   → SecurityContext
      → SecurityContextHolder
         → ThreadLocal

```

**Key facts**

- Authentication stored after login

- Available for entire request lifecycle

- Managed internally by Spring Security

### Holding strategies

- `MODE_THREADLOCAL` (default, 90% cases)

- `MODE_INHERITABLETHREADLOCAL` (async flows)

- `MODE_GLOBAL` (desktop apps only)

---

## 👤 Accessing Logged-In User Details

### Two supported approaches

#### 1️⃣ SecurityContextHolder (any layer)

```java
Authentication auth =
  SecurityContextHolder.getContext().getAuthentication();

```

#### 2️⃣ Method parameter injection (controllers)

```java
public String dashboard(Authentication authentication) { }
```

**Rule of thumb**

- Controller → method parameter

- Service / util → SecurityContextHolder

---

## 🧠 Final Mental Model (Interview Gold)

- HTTPS protects **transport**

- EntryPoint handles **unauthenticated**

- AccessDenied handles **unauthorized**

- Session stores **SecurityContext**

- UI reads auth state via **Thymeleaf dialect**

- Business logic reads user via **SecurityContextHolder**

---

## ✅ Section Outcome

After Section 7, you can:

✔ Secure MVC apps end-to-end  
✔ Control login & logout behavior  
✔ Handle security exceptions cleanly  
✔ Manage sessions correctly  
✔ Build auth-aware UIs  
✔ Explain SecurityContext with confidence
