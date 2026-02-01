# 🔐 Section 12 — Method Level Security (Spring Security)

## 📌 Section Overview

This section focuses on **Method Level Security in Spring Security**, which protects **business logic directly at the Java method level**, independent of API or endpoint security.

While API-level security controls *who can access endpoints*, method-level security controls *who can execute critical business logic* — making it essential for secure, real-world applications.

---

## 🎯 Why Method Level Security?

- Business logic lives inside **Java methods**, not APIs

- Internal method calls can bypass endpoint security

- Non-web applications (batch jobs, desktop apps) have **no APIs**

- Enterprises often require **multiple security layers**

👉 Method-level security provides **defense-in-depth**.

---

## 🧩 Core Concepts Covered

### 1️⃣ Invocation Authorization

Controls **whether a method is allowed to execute**.

**Annotations used:**

- `@PreAuthorize` → authorization **before** method execution

- `@PostAuthorize` → authorization **after** method execution (based on return data)

**Key idea:**

- Execution itself is protected

- Unauthorized access results in **403 Forbidden**

---

### 2️⃣ Filtering Authorization

Controls **what data enters or leaves a method**, without blocking execution.

**Annotations used:**

- `@PreFilter` → filters **input collections**

- `@PostFilter` → filters **output collections**

**Key idea:**

- Method always executes

- Unauthorized data is **silently removed**

- Requires **collection-based parameters or return types**

---

## ⚙️ Enabling Method Level Security

Method-level security is enabled globally using:

```java
@EnableMethodSecurity( 
    securedEnabled = true,
     jsr250Enabled = true 
)
```

### Notes:

- `prePostEnabled` is **true by default**

- Enables:
  
  - `@PreAuthorize`
  
  - `@PostAuthorize`
  
  - `@PreFilter`
  
  - `@PostFilter`

- Replaces deprecated `@EnableGlobalMethodSecurity`

---

## 🛠️ What Was Implemented in This Section

### Backend

- API authorization downgraded to `authenticated()` for demos

- Method-level authorization applied at:
  
  - Controller layer
  
  - Repository layer

- Demonstrated:
  
  - Blocking execution (`@PreAuthorize`)
  
  - Blocking response (`@PostAuthorize`)
  
  - Input filtering (`@PreFilter`)
  
  - Output filtering (`@PostFilter`)

---

### Frontend (Angular)

- Updated API contracts to handle:
  
  - Collection-based inputs
  
  - Collection-based responses

- Handled:
  
  - Empty responses (PostFilter scenarios)
  
  - Silent filtering behavior

---

## 🧠 Key Behavioral Differences

| Feature          | Invocation Authorization | Filtering Authorization   |
| ---------------- | ------------------------ | ------------------------- |
| Method execution | May be blocked           | Always executes           |
| Data handling    | All-or-nothing           | Selective filtering       |
| Typical response | 403 Forbidden            | 200 OK with filtered data |
| Use case         | Access control           | Data visibility control   |

---

## 🚫 Common Pitfalls

- Assuming API security protects business logic

- Expecting filtering annotations to block execution

- Forgetting that filtering works **only with collections**

- Not handling empty lists after filtering

- Breaking UI contracts after switching to filters

- Using deprecated method security annotations

---

## ⚡ Interview Takeaways

- API security ≠ Method security

- Method-level security protects **business logic**

- `@PreAuthorize` blocks execution

- `@PostAuthorize` blocks response

- `@PreFilter` filters inputs

- `@PostFilter` filters outputs

- Filtering is **silent**, not an error

---

## 🧾 Section Summary

**Method Level Security in Spring Security provides fine-grained control over who can execute methods and what data can flow through them, enabling secure, layered, production-grade applications.**
