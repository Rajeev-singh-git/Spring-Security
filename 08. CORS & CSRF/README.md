# 

# 🔐 8 - CORS & CSRF (Browser Security Deep Dive)

This section focuses on **browser-level security problems** and how Spring Security **actually solves them in production systems**.

It covers **CORS, CSRF, their differences, internal flow, backend + frontend integration**, and common pitfalls.

---

## 1️⃣ CORS (Cross-Origin Resource Sharing)

### What CORS Really Is

- **Browser protection**, not a server-side security feature

- Prevents **JavaScript running on one origin** from reading responses of another origin

- Enforced **only by browsers**

### Key Concept

> CORS controls **who is allowed to CALL an API from a browser**

### When CORS Applies

- Frontend (Angular/React) → Backend

- Different **origin** = protocol + domain + port mismatch

### When CORS Does NOT Apply

- Postman / curl

- Server-to-server calls

- Same-origin requests

---

### CORS Configuration in Spring Security

Configured inside `SecurityFilterChain` using `http.cors()`.

Key properties:

- Allowed Origins

- Allowed Methods

- Allowed Headers

- Allow Credentials

- Max Age (preflight caching)

CORS works via:

- **Preflight request (OPTIONS)**

- Browser checks `Access-Control-Allow-*` headers

- Only then sends the actual request

---

### ⚠️ Important CORS Pitfalls

- `localhost` ≠ `127.0.0.1`

- `*` with credentials = ❌ invalid

- CORS errors are **browser-side**, not backend bugs

---

## 2️⃣ CSRF (Cross-Site Request Forgery)

### What CSRF Really Is

- A **real security attack**

- Exploits **authenticated browser sessions**

- Performs actions **without user consent**

### Key Concept

> CSRF exploits the fact that browsers automatically attach cookies

---

### Why CORS Does NOT Protect Against CSRF

- CSRF uses **HTML forms**, not JS fetch

- Browser treats form submission as **same-site request**

- Cookies are attached automatically

- CORS never gets triggered

➡️ **CORS ≠ CSRF protection**

---

## 3️⃣ Spring Security Default CSRF Behavior

- Enabled by default

- Protects:
  
  - POST
  
  - PUT
  
  - DELETE

- Allows:
  
  - GET

- Missing token → **403 Forbidden**

---

## 4️⃣ CSRF Token Architecture (Spring Security)

### Core Components

- `CsrfToken`

- `CsrfTokenRepository`

- `CookieCsrfTokenRepository`

- `CsrfFilter`

- `CsrfTokenRequestAttributeHandler`

---

### Recommended Strategy

- Store CSRF token in **cookie**

- Send token back via **request header**

- Follow Angular / SPA conventions

Cookie:

```java
XSRF-TOKEN
```

Header:

```java
X-XSRF-TOKEN
```

---

## 5️⃣ CSRF Token Flow (Mental Model)

```java
Login / First Auth Request
 └── Authentication succeeds
 └── CSRF token generated (lazy)
 └── Token stored in cookie (XSRF-TOKEN)

Subsequent POST / PUT / DELETE
 └── Browser sends cookie automatically
 └── UI reads cookie
 └── UI sends token in header (X-XSRF-TOKEN)
 └── CsrfFilter validates

```

Mismatch or missing token → **403**

---

## 6️⃣ Why Custom CSRF Filter Is Required

- CSRF tokens are **lazily generated**

- Cookie is not created unless token is accessed

- Custom `OncePerRequestFilter` forces token generation

- Ensures cookie reaches UI

---

## 7️⃣ Session & SecurityContext Adjustments

When using:

- Custom login page

- HTTP Basic

- Separate UI app

You must configure:

- `sessionCreationPolicy(ALWAYS)`

- `securityContext(requireExplicitSave(false))`

This ensures:

- `JSESSIONID` is created

- Auth state is preserved across requests

---

## 8️⃣ Frontend (Angular) Responsibilities

UI must:

1. Read `XSRF-TOKEN` cookie after login

2. Store token (sessionStorage)

3. Attach token to every mutating request

4. Send cookies using `withCredentials: true`

Interceptor is the **correct place** for this logic.

---

## 9️⃣ Ignoring CSRF for Public APIs

CSRF is **not required** for:

- Public APIs

- No authentication

- No sensitive state change

Example:

- `/contact`

- `/register`

Configuration:

```java
.csrf(csrf -> csrf
    .ignoringRequestMatchers("/contact", "/register")
)
```

⚠️ Never ignore CSRF for authenticated, state-changing APIs.

---

## 🔁 CORS vs CSRF (Quick Comparison)

| Aspect         | CORS           | CSRF            |
| -------------- | -------------- | --------------- |
| Type           | Browser policy | Security attack |
| Protects       | API access     | User intent     |
| Trigger        | JS requests    | Cookies         |
| Solved by      | Headers        | Tokens          |
| Spring Default | Disabled       | Enabled         |

---

## 🎯 Final Takeaways

- CORS is **not security**, it’s browser access control

- CSRF is a **real attack**

- Cookies are the root cause of CSRF

- Tokens bind **user intent**

- Ignore CSRF **selectively**, not globally

- Backend + Frontend must cooperate

---

## 🧠 Interview-Level Insight

> “CORS protects APIs from other origins.  
> CSRF protects users from their own browsers.”

If you can explain this clearly, you **own this topic**
