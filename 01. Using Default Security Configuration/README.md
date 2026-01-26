## Section 1 – Using Default Spring Security Configuration

This project demonstrates **Spring Security’s default behavior** when no custom security configuration is provided.

No `SecurityFilterChain` bean is defined in this section.

---

## 🎯 Purpose

To understand:
- How Spring Security secures applications **out of the box**
- What default authentication mechanisms are enabled
- Why all APIs are secured by default

This section serves as the **baseline** for all subsequent sections.

---

## 🔐 Default Spring Security Behavior

When Spring Security is added to the project:

- All HTTP requests are **secured by default**
- Anonymous access is **not allowed**
- Default authentication mechanisms enabled:
  - **Form Login** (browser-based login page)
  - **HTTP Basic Authentication** (Authorization header)

These defaults are provided by:
- `SpringBootWebSecurityConfiguration`
- Default `SecurityFilterChain` bean

---

## 🧠 Key Concepts Observed

- Default `SecurityFilterChain`
- Automatic login page generation
- Browser redirect to `/login`
- Authentication required for all endpoints
- No custom authorization rules

---

## 🚀 How to Run

```bash
mvn spring-boot:run
