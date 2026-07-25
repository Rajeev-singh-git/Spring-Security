# 🔐 Spring Security – Section-wise Learning Projects

This repository contains **multiple independent Spring Boot projects**,  
each representing a **focused learning stage of Spring Security**.

Each section:

- Is a **fully runnable project**

- Builds **conceptually** on previous sections

- Has its **own dedicated README** for quick revision

---

## 📘 Sections

---

### 1️⃣ Using Default Spring Security Configuration

**Goal:** Understand what Spring Security does *out of the box*

**Key Concepts**

- Default SecurityFilterChain

- Auto-configured login page

- Default authentication & authorization behavior

👉 [Go to Section 1 README](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/1.%20Using%20Default%20Security%20Configuration/README.md)

---

### 2️⃣ Changing Default Security Configuration

**Goal:** Learn how to take control of Spring Security

**Key Concepts**

- Custom `SecurityFilterChain`

- `permitAll`, `authenticated`, `denyAll`

- `requestMatchers`

- `formLogin()` vs `httpBasic()`

👉 [Go to Section 2 README](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/2.%20Change%20Default%20Security%20Configuration/README.md)

---

### 3️⃣ Creating and Managing Users

**Goal:** Understand user management & password handling

**Key Concepts**

- `InMemoryUserDetailsManager`

- `UserDetailsService`

- `UserDetails`

- Password encoders

- Authentication flow with users

👉 [Go to Section 3 README](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/3.%20Creating%20Users%20using%20InMemoryUserDetailsManager/README.md)

---

### 4️⃣ JDBC-based Authentication

**Goal:** Move from in-memory users to database-backed users

**Key Concepts**

- `JdbcUserDetailsManager`

- Default Spring Security schema

- Database-driven authentication

- Limitations of fixed schema approach

👉 [Go to Section 4 README](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/4.%20Defining%20and%20Managing%20Users%20in%20Spring%20Security%20(Database-backed)/README.md)

---

### 5️⃣ Custom User Authentication (Production Style)

**Goal:** Implement real-world user authentication

**Key Concepts**

- Custom `UserDetailsService`

- Domain-based user loading

- `DaoAuthenticationProvider`

- Password validation flow

- Why this approach is used in production

👉 [Go to Section 5 README](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/5.%20Password%20Management%20with%20Password%20Encoders/README.md)

---

### 6️⃣ Authentication Providers & Profiles

**Goal:** Deep dive into authentication mechanics & flexibility

**Key Concepts**

- `AuthenticationProvider` internals

- `supports()` vs `authenticate()`

- `ProviderManager`

- Multiple authentication strategies

- Spring Profiles for conditional security

- Environment-specific authentication behavior

👉 [Go to Section 6 README](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/6.%20Implementing%20Authentication%20Provider/README.md)

---

### 7️⃣ Web Security, Sessions & SecurityContext

**Goal:** Master Spring Security for MVC / monolithic applications

**Key Concepts**

- Enforcing HTTPS (Channel Security)

- Exception handling (401 vs 403)

- Session management

- Authentication events

- Form login & logout customization

- Thymeleaf + Spring Security integration

- `SecurityContext` & `SecurityContextHolder`

- Accessing logged-in user details

👉 [Go to Section 7 README](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/7.%20Sprinng%20Security%20Customizations/README.md)

---

### 8️⃣ Browser Security: CORS & CSRF (Production-Critical)

**Goal:** Understand and correctly implement browser-level security

**Key Concepts**

- What CORS **is** and **is not**

- Preflight requests & browser enforcement

- Why CORS does **not** protect against CSRF

- CSRF attack flow (real-world mental model)

- CSRF token lifecycle (generation → storage → validation)

- `CookieCsrfTokenRepository`

- `CsrfFilter` & `CsrfTokenRequestAttributeHandler`

- Custom CSRF cookie filter

- Session & SecurityContext implications

- Frontend (Angular) + Backend coordination

- Ignoring CSRF for public APIs (safely)

👉 [Go to Section 8 README](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/8.%20CORS%20%26%20CSRF)

---

### 9️⃣ Authorization: Authorities & Roles

**Goal:** Control *what an authenticated user can access*

**Key Takeaways**

- Authentication vs Authorization (401 vs 403)

- Authority-based vs Role-based authorization

- Endpoint protection using `hasAuthority()` / `hasRole()`

- Handling authorization failures & events

👉 [Go to Section 9 README](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/9.%20Authorization%20Implementation)

---

### 🔟 Custom Filters in Spring Security

**Goal:** Understand and extend Spring Security at the **filter-chain level**

**What this section covers**

- How Spring Security filter chain really works

- Internal filter execution & ordering

- Creating custom filters using:
  
  - `Filter`
  
  - `GenericFilterBean`
  
  - `OncePerRequestFilter`

- Injecting filters using:
  
  - `addFilterBefore`
  
  - `addFilterAfter`
  
  - `addFilterAt` (and when *not* to use it)

- Real custom filters:
  
  - Pre-auth request validation
  
  - Post-auth logging & auditing
  
  - Order-independent filters

- Debugging filters with `@EnableWebSecurity(debug = true)`

- Common production pitfalls (missing `chain.doFilter()`)

👉 **Outcome:** You understand **how Spring Security actually works internally**.

👉 [Go to Section 10 README](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/10.%20Custom%20Filters)

---

### 🔐 1️⃣1️⃣ JWT & Stateless Authentication (Production Grade)

**Goal:** Replace session-based security with scalable, stateless JWT authentication

**Key Takeaways**

- Why sessions (`JSESSIONID`) don’t scale

- Opaque tokens vs JWT (when & why)

- JWT structure (header, payload, signature)

- Token tampering & signature validation

- Stateless authentication using JWT

- Custom JWT generator & validator filters

- `SessionCreationPolicy.STATELESS`

- Secret key handling via environment/properties

- Token expiration & trust boundaries

- Angular UI → JWT propagation via headers

- Manual authentication using `AuthenticationManager`

👉 **Outcome:** You can design and implement real-world stateless security using JWT.  

👉 [Go to Section 11 README](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/10.%20Custom%20Filters)

---

### 🔐 **1️⃣2️⃣ Method Level Security (Invocation & Filtering Authorization)**

**Goal:** Secure business logic directly at the Java method level

**Key Concepts**

- Enabling method security with `@EnableMethodSecurity`

- Invocation authorization (`@PreAuthorize`, `@PostAuthorize`)

- Filtering authorization (`@PreFilter`, `@PostFilter`)

- Method security across controller, service & repository layers

👉 [Go to Section 12 README](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/12.%20Method%20Level%20Security)

---

### 🔐 **1️⃣3️⃣ OAuth 2.0, OpenID Connect & IAM (Theory Deep-Dive)**

**Goal:** Build a rock-solid mental model of modern authentication, authorization, and identity systems used in enterprises

**Key Takeaways**

- Why OAuth 2.0 solves **authorization**, not identity

- Core OAuth roles: Resource Owner, Client, Authorization Server, Resource Server

- Grant types and when to use them (Auth Code, PKCE, Client Credentials, Refresh Token)

- Why Implicit & Password grants are deprecated

- How access tokens are validated (JWT vs opaque, local vs introspection)

- OpenID Connect as the missing **identity layer**

- Access Token vs ID Token (why both exist)

- How OAuth + OIDC together form **IAM (Identity & Access Management)**

👉 **Outcome:** You can reason clearly about OAuth, OIDC, and IAM without confusion or cargo-culting.

👉 [Go to Section 13 README](#)

---

### 🔐 **1️⃣4️⃣ OAuth 2.0 Login with Spring Security (Social Login Integration)**

**Goal:** Implement OAuth 2.0 login in a Spring Boot application using real social providers

**Key Takeaways**

- Enabling OAuth 2.0 login via `oauth2Login()`

- How Spring Security acts as:
  
  - OAuth Client
  
  - Resource Server

- Two configuration approaches:
  
  - Explicit `ClientRegistrationRepository` (code-driven)
  
  - Auto-configuration via `application.properties`

- Role of `ClientRegistration`, `CommonOAuth2Provider`, and in-memory repositories

- How Spring creates `OAuth2AuthenticationToken` after login

- Mapping social identity attributes into application context

- Why social login is **authentication-only**, not authorization

- Where this approach fits (small apps) and where it breaks (enterprise systems)

👉 **Outcome:** You can integrate social login correctly and know **exactly why it’s not enough for enterprise security**.

👉 [Go to Section 14 README](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/14.%20Implementing%20OAUTH2/springsecOAUTH2%20Backend)


