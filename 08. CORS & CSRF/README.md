# 🔐 Section 8 — CORS & CSRF (Browser Security Deep Dive)

This section builds a complete mental model of **browser security** in Spring Security.

It explains **why CORS and CSRF exist, how Spring Security implements them, how frontend and backend cooperate, and how to configure them correctly in production applications.**

---

# 🧠 Concepts Covered

By the end of this section, you'll understand:

- What CORS really is
- How CORS is configured in Spring Security
- What CSRF really is
- Why CORS and CSRF solve different problems
- Spring Security's default CSRF protection
- How Spring Security implements CSRF internally
- CookieCsrfTokenRepository & Lazy Token Generation
- Complete CSRF Request Flow
- Complete CSRF Configuration for Browser-Based SPAs
- Frontend Participation in CSRF Protection
- Ignoring CSRF for Public APIs

---

# 1️⃣ CORS (Cross-Origin Resource Sharing)

### What

- Browser-enforced security policy
- Restricts JavaScript running on one origin from accessing responses from another origin
- Applies **only to browsers**

### Key Idea

> CORS controls whether browser JavaScript can access responses from another origin.

### Spring Security Implementation

Configured inside `SecurityFilterChain` using:

```java
http.cors()
```

Typical configuration includes:

- Allowed Origins
- Allowed Methods
- Allowed Headers
- Allow Credentials
- Max Age

### Runtime Flow

```
Browser
     │
OPTIONS (Preflight)
     │
CorsFilter
     │
Access-Control-Allow-*
     │
Browser decides
     │
Actual Request
```

### Common Pitfalls

- `localhost` ≠ `127.0.0.1`
- `allowCredentials(true)` cannot be used with `"*"`
- Postman ignores CORS
- Browser enforces CORS

---

# 2️⃣ CSRF (Cross-Site Request Forgery)

### What

- A real security attack
- Exploits authenticated browser sessions
- Abuses automatic browser cookie transmission

### Key Idea

> Authentication proves identity. CSRF tokens prove user intent.

### Spring Security Default Behavior

Enabled by default.

Protects:

- POST
- PUT
- PATCH
- DELETE

Allows:

- GET
- HEAD
- OPTIONS

Missing or invalid token:

```
403 Forbidden
```

### Mental Model

```
Browser
     │
Automatically sends cookies
     │
Backend cannot distinguish intent
     │
CSRF Token solves this
```

---

# 3️⃣ CORS vs CSRF

| CORS                        | CSRF                         |
| --------------------------- | ---------------------------- |
| Browser policy              | Security attack              |
| Protects browser JavaScript | Protects authenticated users |
| Browser enforces            | Backend enforces             |
| Uses response headers       | Uses CSRF tokens             |

### Interview Insight

> CORS controls browser JavaScript access to responses.
> 
> CSRF protects authenticated users from unintended actions performed by their own browser.

---

# 4️⃣ Spring Security CSRF Architecture

### Core Components

- `CsrfToken`
- `CsrfTokenRepository`
- `CookieCsrfTokenRepository`
- `CsrfFilter`
- `CsrfTokenRequestAttributeHandler`

### Recommended Strategy

Store token in:

```java
XSRF-TOKEN
```

Frontend sends:

```java
X-XSRF-TOKEN
```

### Mental Hook

```java
CsrfToken
      │
Repository
      │
Cookie
      │
Frontend Header
      │
CsrfFilter
      │
Validation
```

---

# 5️⃣ CookieCsrfTokenRepository & Lazy Token Generation

### Key Concepts

- Spring Security generates CSRF tokens lazily
- Tokens are generated only when first accessed
- `CookieCsrfTokenRepository` stores the token inside a cookie
- `CsrfCookieFilter` forces token generation
- Custom filter must be registered inside `SecurityFilterChain`

### Mental Hook

```
GET Request
      │
CsrfCookieFilter
      │
csrfToken.getToken()
      │
Generate Token
      │
XSRF-TOKEN Cookie
```

---

# 6️⃣ Complete CSRF Request Flow

```java
Authentication
      │
SecurityContext
      │
First GET
      │
CsrfCookieFilter
      │
Generate Token
      │
Browser stores XSRF-TOKEN
      │
SPA reads cookie
      │
POST / PUT / DELETE
      │
Browser sends cookie
+
SPA sends X-XSRF-TOKEN
      │
CsrfTokenRequestAttributeHandler
      │
CsrfFilter validates
      │
Controller
```

---

# 7️⃣ Enabling CSRF Support for SPAs

For a browser-based SPA (Angular / React), complete CSRF support requires the following steps.

---

### Step 1 — Store the CSRF Token in a Cookie

Configure Spring Security to use `CookieCsrfTokenRepository`.

```java
http.csrf(csrf -> csrf
    .csrfTokenRepository(
        CookieCsrfTokenRepository.withHttpOnlyFalse()
    )
);
```

---

### Step 2 — Force Lazy Token Generation

Create a custom `CsrfCookieFilter`.

```java
public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken csrfToken =
            (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if (csrfToken != null) {
            csrfToken.getToken();
        }

        filterChain.doFilter(request, response);
    }
}
```

---

### Step 3 — Register the Custom Filter

Register the filter inside `SecurityFilterChain`.

```java
http.addFilterAfter(
    new CsrfCookieFilter(),
    BasicAuthenticationFilter.class
);
```

---

### Step 4 — Configure Token Resolution

Configure `CsrfTokenRequestAttributeHandler`.

```java
CsrfTokenRequestAttributeHandler requestHandler =
        new CsrfTokenRequestAttributeHandler();

http.csrf(csrf -> csrf
    .csrfTokenRequestHandler(requestHandler)
);
```

---

### Step 5 — Frontend Sends the Token

Frontend reads the `XSRF-TOKEN` cookie and sends it in the request header.

```http
X-XSRF-TOKEN: <token>
```

---

### Quick Revision

```java
CookieCsrfTokenRepository
        ↓
CsrfCookieFilter
        ↓
Register Filter
        ↓
CsrfTokenRequestAttributeHandler
        ↓
Frontend sends X-XSRF-TOKEN
```

# ---

# 8️⃣ Frontend Participation (SPA)

The backend alone cannot complete CSRF protection.

The frontend must participate.

### Responsibilities

- Read the `XSRF-TOKEN` cookie
- Send it using the `X-XSRF-TOKEN` request header
- Centralize this logic using an HTTP interceptor (or equivalent)

### Mental Hook

```java
Backend
     │
Creates Token
     │
Browser stores Cookie
     │
Frontend reads Cookie
     │
Interceptor
     │
Adds Header
```

---

# 9️⃣ Ignoring CSRF for Public APIs

Ignore CSRF only when an endpoint:

- does **not** require authentication
- does **not** perform actions on behalf of an authenticated user

Typical examples:

- `/contact`
- `/register`

Configuration:

```java
.csrf(csrf -> csrf
    .ignoringRequestMatchers(
        "/contact",
        "/register"
    )
)
```

Don't confuse this with:

```java
permitAll()
```

| Configuration               | Purpose                              |
| --------------------------- | ------------------------------------ |
| `permitAll()`               | Skips authentication & authorization |
| `ignoringRequestMatchers()` | Skips CSRF validation                |

Many public POST endpoints require **both**.

---

# 🎯 Final Takeaways

- CORS is a browser policy, not backend security.
- CSRF is a real attack against authenticated browser sessions.
- Browser-managed cookies make CSRF attacks possible.
- Spring Security enables CSRF protection by default.
- `CookieCsrfTokenRepository` is the preferred repository for browser-based SPAs.
- CSRF tokens are generated lazily.
- `CsrfCookieFilter` forces token generation so the browser receives the token.
- `CsrfTokenRequestAttributeHandler` resolves the incoming token for validation.
- The frontend must send the `X-XSRF-TOKEN` header.
- Ignore CSRF selectively using `ignoringRequestMatchers()`, never by disabling CSRF globally.

---

# 🧠 One-Line Mental Model

```java
CORS controls browser access.

CSRF validates user intent.

Spring Security + Browser + Frontend together provide complete CSRF protection.
```
