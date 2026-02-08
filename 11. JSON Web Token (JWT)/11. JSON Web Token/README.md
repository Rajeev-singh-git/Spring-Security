# 🔐 Section 11 — JWT Authentication & Stateless Security

---

## 🎯 Section Goal

Understand **why sessions break at scale**, how **JWT replaces server-side sessions**, and how to **implement end-to-end stateless authentication** in Spring Security using custom filters.

This section moves you from **session-based security → token-based security**, which is mandatory for modern SPAs and microservices.

---

## 🧠 Big Picture Mental Model

```java
Client → Login
       → Credentials validated
       → JWT generated    
       → JWT returned to client
       → Client stores JWT
       → Client sends JWT on every request
       → Backend validates JWT
       → SecurityContext built per request
```

**No server memory.  
No sticky sessions.  
Each request proves identity.**

---

## 🔄 End-to-End Authentication Flow (Core of This Section)

### 🔐 1️⃣ Login Request (Initial Authentication)

**Client**

- Sends username + password

- Via:
  
  - HTTP Basic (`/user`) **or**
  
  - Custom API (`/apiLogin` with JSON body)

**Spring Security**

- Delegates to `AuthenticationManager`

- Uses:
  
  - `UserDetailsService`
  
  - `PasswordEncoder`

- Credentials are fully validated here

---

### 🪙 2️⃣ JWT Generation (Post Authentication)

**JWTTokenGeneratorFilter**

- Executes **after authentication**

- Guard conditions:
  
  - Authentication exists
  
  - Authentication is authenticated
  
  - Not anonymous

- Generates JWT with:
  
  - username
  
  - authorities
  
  - issuedAt
  
  - expiration

- Signs token using secret key

- Sends JWT to client (header / body)

✅ Authentication happens **only once**

---

### 💾 3️⃣ Client Stores Token

**UI Application**

- Reads JWT from response

- Stores it in:
  
  - `sessionStorage`

- JWT lifetime controlled via expiration

🚫 No cookies  
🚫 No JSESSIONID

---

### 🔁 4️⃣ Secured API Call (Every Request)

**Client**

- Sends JWT in:
  
  ```java
  Authorization: <jwt>
  ```

---

### 🛡️ 5️⃣ JWT Validation (Before Authentication)

**JWTTokenValidatorFilter**

- Executes **before BasicAuthenticationFilter**

- Steps:
  
  1. Read JWT from header
  
  2. Verify signature using secret key
  
  3. Parse claims
  
  4. Extract username + authorities
  
  5. Manually create Authentication object
  
  6. Store in SecurityContextHolder

📌 Spring now treats the request as authenticated

---

### 🔓 6️⃣ Authorization Enforcement

**Spring Security**

- Applies rules like:
  
  - `hasRole("USER")`
  
  - `hasAnyRole("ADMIN", "USER")`

- Access granted or denied

➡️ Controller is reached **only if authorized**

---

## 🔑 JWT Fundamentals (Must-Know)

### JWT Structure

```java
Header.Payload.Signature
```

- Header → algorithm, type

- Payload → claims (username, roles, expiry)

- Signature → tamper protection

⚠️ Payload is Base64 encoded (readable)

---

## 🧩 Token Types (Context)

- **Opaque Tokens**
  
  - Random
  
  - Require introspection

- **JWT Tokens**
  
  - Self-contained
  
  - Locally verifiable
  
  - Best for microservices

---

## 🧷 Filters Implemented

### 🔹 JWTTokenGeneratorFilter

- Runs after authentication

- Executes only for login

- Creates and signs JWT

---

### 🔹 JWTTokenValidatorFilter

- Runs before authentication

- Validates JWT

- Rebuilds SecurityContext per request

---

## 🔗 Filter Chain Placement (Critical)

```java
JWTTokenValidatorFilter
 → BasicAuthenticationFilter
 → JWTTokenGeneratorFilter
```

Order matters more than logic.

---

## ⚙️ Core Spring Security Configuration

### Stateless Mode

```java
SessionCreationPolicy.STATELESS
```

Effect:

- No HttpSession

- No SecurityContext persistence

- Each request is independent

---

### CORS Requirement

```java
config.setExposedHeaders(List.of("Authorization"));
```

Required so UI can read JWT from response.

---

## 🌐 UI Integration (Angular)

- Store JWT in sessionStorage

- Send JWT in Authorization header

- Clear JWT on logout

---

## 📦 Dependencies Used

- `jjwt-api`

- `jjwt-impl`

- `jjwt-jackson`

Used for JWT creation and validation.

---

## 🧪 Quick Recall / Interview Cues

**Why JWT?**  
→ Stateless, scalable security

**Where is authentication stored?**  
→ Inside JWT, not server

**Who validates JWT?**  
→ Resource server itself

**When is authentication executed?**  
→ Only during login

**What protects against tampering?**  
→ Digital signature

---

## 🧠 Final Mental Lock

JWT turns authentication into **cryptographic proof** instead of **server memory**.

Every request re-establishes trust.  
Every service can validate identity independently.

> **Stateless security is the foundation of modern backend architectur**

This repository contains **multiple independent Spring Boot projects**,  
each representing a **focused learning stage of Spring Security**.

Each section:

- Is a **fully runnable project**

- Builds **conceptually** on previous sections

- Has its **own dedicated README** for quick revision

---
