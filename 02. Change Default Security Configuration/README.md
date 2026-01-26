## Section 2 – Changing Default Spring Security Configuration

This project customizes Spring Security by **overriding the default configuration** using a custom `SecurityFilterChain` bean.

This section builds directly on Section 1.

---

## 🎯 Purpose

To learn how to:
- Override Spring Security default behavior
- Define authorization rules based on business requirements
- Control access using `requestMatchers`
- Understand `permitAll`, `authenticated`, and `denyAll`

---

## 🔐 What Changed Compared to Section 1

- Default `SecurityFilterChain` is **replaced**
- Custom authorization rules are defined
- APIs are categorized as:
  - **Public APIs**
  - **Secured APIs**

---

## 🔧 Custom Authorization Configuration

Example behavior implemented:

- Secured APIs (authentication required):
  - `/myAccount`
  - `/myBalance`
  - `/myLoans`
  - `/myCards`

- Public APIs (no authentication required):
  - `/notice`
  - `/contact`
  - `/error`

Authorization is controlled using:
- `authorizeHttpRequests`
- `requestMatchers`
- `authenticated()` and `permitAll()`

---

## 🚫 Important Learnings

- `anyRequest().permitAll()` → disables security entirely (not recommended)
- `anyRequest().denyAll()` → blocks all access (403 for everyone)
- Authentication and authorization are **separate concerns**
- Authorization rules must align with business logic

---

## 🔍 Authentication Behavior Notes (Important)

When authentication mechanisms are customized:

### If `formLogin()` is disabled:
- Default login page is not generated
- `UsernamePasswordAuthenticationFilter` is **skipped**
- Credentials are no longer read from form parameters
- Browser may show a Basic Auth popup (browser behavior)

### If `httpBasic()` is enabled:
- Credentials are sent via `Authorization` header
- Header format:

Authorization: Basic base64(username:password)

- Credentials are extracted and decoded by:
`BasicAuthenticationFilter`

### If both `formLogin()` and `httpBasic()` are disabled:
- No authentication entry mechanism exists
- Spring Security responds with 401 / 403
- Used typically for JWT or custom authentication flows


## 🔑 Authentication Mechanisms

Both mechanisms are still enabled:
- Form Login (for browser testing)
- HTTP Basic Authentication (for API clients like Postman)

(Disabling login mechanisms is handled in later sections.)

---

## 🚀 How to Run

```bash
mvn spring-boot:run
