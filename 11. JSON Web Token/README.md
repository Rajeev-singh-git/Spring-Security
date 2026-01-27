# 🔐 Section 10 — Custom Filters in Spring Security

## 🎯 Section Goal

Understand **how Spring Security processes requests using a filter chain**, and learn **how, when, and where to inject your own custom filters** safely in real-world applications.

This section moves you from *using* Spring Security to **extending it correctly**.

---

## 🧠 Big Picture Mental Model

Spring Security works by intercepting **every incoming request** using a **chain of security filters**.

Each filter has **one responsibility**, such as:

- extracting credentials

- handling CORS / CSRF

- performing authentication

- enforcing authorization

Your application logic is reached **only after** this security filter chain completes.

> **Custom filters let you plug your own logic into this chain — before, after, or alongside Spring’s filters.**

---

## 🔗 Spring Security Filter Chain (Reality)

- A request passes through **20+ filters** (depends on config)

- Filters execute **in order**

- Each filter **must call `chain.doFilter()`** to continue

- Final destination:
  
  ```java
  Security Filters → DispatcherServlet → Controller
  ```

Using `@EnableWebSecurity(debug = true)` reveals the exact filters and order at runtime (⚠️ never enable in prod).

---

## 🧩 Ways to Create a Custom Filter

Spring offers **three main approaches**, each suited for different needs.

### 1️⃣ `Filter` (Servlet Standard)

- Lowest-level, container-based

- Full control

- Manual handling of request/response

Use when logic is **simple and framework-independent**.

---

### 2️⃣ `GenericFilterBean` (Spring-aware)

- Access to:
  
  - Environment
  
  - ServletContext
  
  - Init parameters

- Still flexible

Use when filter needs **Spring context awareness**.

---

### 3️⃣ `OncePerRequestFilter` (Recommended)

- Ensures filter executes **only once per request**

- Prevents duplicate execution during forwards/dispatches

- Supports conditional skipping via `shouldNotFilter()`

👉 **Preferred for most real-world filters**

---

## 🧷 Injecting Custom Filters into the Chain

Spring Security provides **four APIs**, but **only three are recommended**.

### ✅ `addFilterBefore()`

Executes your filter **before** a specific Spring Security filter.

Use when:

- validating input **before authentication**

- blocking requests early

---

### ✅ `addFilterAfter()`

Executes your filter **after** a specific filter.

Use when:

- authentication is already complete

- logging user details

- auditing successful login

---

### ⚠️ `addFilterAt()` (Advanced / Risky)

Places your filter at the **same position** as another filter.

Important truths:

- Does **not replace** the existing filter

- Execution order is **non-deterministic**

- Use only if logic is **order-independent**

---

### ❌ `addFilter()`

Requires manual `@Order` management.  
Not recommended due to **version fragility**.

---

## 🛠️ Implemented Filters in This Section

### 🔹 RequestValidationBeforeFilter

- Runs **before authentication**

- Blocks login if email contains `"test"`

- Demonstrates:
  
  - decoding Basic Auth header
  
  - early request rejection
  
  - correct use of `chain.doFilter()`

---

### 🔹 AuthoritiesLoggingAfterFilter

- Runs **after authentication**

- Logs:
  
  - authenticated username
  
  - granted roles/authorities

- Demonstrates:
  
  - reading `SecurityContextHolder`
  
  - post-authentication hooks

---

### 🔹 AuthoritiesLoggingAtFilter

- Runs **at same position** as `BasicAuthenticationFilter`

- Logs “authentication in progress”

- Demonstrates:
  
  - when `addFilterAt()` is safe
  
  - order-independent logic

---

## ⚠️ Critical Rule (Never Forget)

> **If you don’t call `chain.doFilter(request, response)`  
> the request will NEVER reach authentication or controllers.**

This is the **#1 real-world filter bug**.

---

## 🧪 Quick Recall / Interview Cues

- **Why filters?**  
  → To intercept and process requests before controllers

- **Best base class for custom filters?**  
  → `OncePerRequestFilter`

- **Before vs After vs At?**  
  → Before = pre-auth  
  → After = post-auth  
  → At = same order (non-deterministic)

- **Does `addFilterAt()` replace filters?**  
  → ❌ No, both run

- **How to debug filter chain?**  
  → `@EnableWebSecurity(debug = true)`

- **Biggest filter mistake?**  
  → Forgetting `chain.doFilter()`

---

## 🧠 Final Mental Lock

Spring Security is not magic — it is **a carefully ordered filter chain**.  
Custom filters are powerful, but **placement matters more than logic**.  
When in doubt:

- **Before** → validation

- **After** → logging / auditing

- **At** → only if order doesn’t matter
