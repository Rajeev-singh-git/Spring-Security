
---

# 📘 Section 2 README  
### **Changing Default Spring Security Configuration**

```md
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

## 🔑 Authentication Mechanisms

Both mechanisms are still enabled:
- Form Login (for browser testing)
- HTTP Basic Authentication (for API clients like Postman)

(Disabling login mechanisms is handled in later sections.)

---

## 🚀 How to Run

```bash
mvn spring-boot:run
