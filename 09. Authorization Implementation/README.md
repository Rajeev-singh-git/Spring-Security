# 🔐 Section 9 — Authorization in Spring Security

This section focuses on **authorization** — deciding **what an authenticated user is allowed to do**.

Up to previous sections, the application verified **identity (Authentication)**.  
From this section onward, it enforces **permissions (Authorization)** using **authorities, roles, and Spring Security rules**.

---

## 🎯 Section Goal

By the end of this section, you will clearly understand:

- How Spring Security stores and evaluates **authorities and roles**

- How to implement **fine-grained and coarse-grained authorization**

- How authorization failures (**403**) are enforced and observed

- How real production systems design **roles vs authorities**

This section reflects **real-world, database-driven authorization**.

---
## Authorization in Spring Security

During **authentication**, custom `UserDetailsService` loads the user's roles/authorities from the database, converts them into `GrantedAuthority` objects, and attaches them to the authenticated `UserDetails`. Spring Security then stores these authorities inside the `Authentication` object in the `SecurityContext`.

For every protected request, Spring Security **does not query the database again**. It simply reads the `GrantedAuthority` collection from the `Authentication` object and compares it with the authorization rules (`hasAuthority()`, `hasRole()`, `access()`) configured in the `SecurityFilterChain` to decide whether to allow the request (`200 OK`) or deny it (`403 Forbidden`).

Spring Security loads authorities from the database only during authentication. During authorization, it performs an in-memory comparison between the authenticated user's GrantedAuthority collection and the rules configured in the SecurityFilterChain, without querying the database again.

## 🧠 Authentication vs Authorization (Mental Model)

- **Authentication (AuthN)** → *Who are you?*
  
  - Validates identity (username, password, OTP, etc.)
  
  - Failure → **401 Unauthorized**

- **Authorization (AuthZ)** → *What are you allowed to access?*
  
  - Validates privileges (roles / authorities)
  
  - Failure → **403 Forbidden**

> Authentication **always happens first**, authorization comes **after**.

---

## 🧩 How Spring Security Represents Authorization

### 1️⃣ GrantedAuthority (Core Contract)

- Spring Security represents **both roles and authorities** using:
  
  `GrantedAuthority`

- Internally, permissions are just **Strings**

- Most commonly used implementation:
  
  `SimpleGrantedAuthority`

---

## 🏷️ Authorities vs Roles (Key Design Decision)

### Authorities (Fine-grained)

- Represent **individual actions**

- Examples:
  
  - `VIEWACCOUNT`
  
  - `VIEWCARDS`
  
  - `VIEWLOANS`

**Best for:**

- API-level permissions

- Precise access control

- Micro-level security rules

---

### Roles (Coarse-grained)

- Represent a **group of authorities**

- Examples:
  
  - `ROLE_USER`
  
  - `ROLE_ADMIN`

**Best for:**

- Simpler configuration

- Large systems with many actions

- Business-level access control

---

### ⚠️ Important Role Rule (Spring Security)

- Roles are stored as:
  
  `ROLE_USER, ROLE_ADMIN`

- But in configuration:
  
  `hasRole("USER")   ✅ hasRole("ROLE_USER") ❌`

Spring Security **automatically adds `ROLE_` prefix** internally.

---

## 🗄️ Database-Driven Authorization (Production Style)

### Schema Design

- `customer` table → stores identity

- `authorities` table → stores permissions

- Relationship:
  
  `One Customer → Many Authorities / Roles`

This allows:

- Multiple permissions per user

- Easy role upgrades

- Real-world flexibility

---

## 🔄 Authorization Flow (End-to-End)

1. User logs in → **Authentication succeeds**

2. UserDetails loaded from DB

3. Authorities / Roles loaded and converted to `GrantedAuthority`

4. Stored inside `Authentication` object

5. Saved in `SecurityContext`

6. On API access:
   
   - Spring Security checks required authority/role
   
   - If missing → **403 Forbidden**

---

## 🔐 Enforcing Authorization in Configuration

### Common Methods

- `hasAuthority("VIEWACCOUNT")`

- `hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")`

- `hasRole("USER")`

- `hasAnyRole("USER", "ADMIN")`

- `access()` → for complex rules using Spring Expression Language

**Rule of thumb:**

- Simple needs → `hasAuthority` / `hasRole`

- Complex logic → `access()`

---

## 🚫 Authorization Failures (403 Handling)

### What happens on failure?

- Spring Security returns **403 Forbidden**

- Client can:
  
  - Show error message
  
  - Redirect user

- Backend can:
  
  - Log incident
  
  - Trigger audit
  
  - Send alerts

---

## 🔔 Authorization Events (Advanced but Important)

- Spring Security **publishes events on authorization failure**
  
  - `AuthorizationDeniedEvent`

- You can listen and:
  
  - Log missing roles
  
  - Track suspicious access
  
  - Build audit trails

### Why no success events by default?

- Authorization success happens **on every request**

- Publishing success events would be **too noisy**

- Can be enabled manually — but rarely needed

---

## 🌍 Profiles + HTTPS + CORS (Practical Gotcha)

### Non-Prod / Local

```java
.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
```

- Allows HTTP

- Suitable for localhost development

### Prod

```java
.requiresChannel(rcc -> rcc.anyRequest().requiresSecure())
```

- Forces HTTPS

- Must align with:
  
  - CORS allowed origins
  
  - Frontend protocol (https)

⚠️ **Mismatch between HTTPS enforcement and CORS origins is a common production bug**

---

## 🧠 Key Takeaways (Quick Revision)

- Authentication ≠ Authorization

- Authorities = fine-grained permissions

- Roles = grouped permissions

- Both are stored as `GrantedAuthority`

- Missing permission → **403**, not 401

- Authorization failures can trigger backend events

- Roles require `ROLE_` prefix (internally)

- `hasRole()` auto-adds prefix — don’t repeat it

---

# 🧭 Step-by-Step Authorization Flow (Runtime)

### **1️⃣ Login / First Authenticated Request**

- User sends credentials (form login / HTTP Basic / token)

- Spring Security authenticates the user

- On success:
  
  - `UserDetails` → converted to `Authentication`
  
  - Authorities / Roles are attached
  
  - Stored in **SecurityContext**
  
  - SecurityContext stored in **session** (or stateless store)

✅ **Authentication complete**

---

### **2️⃣ SecurityContext Is Now Available**

- Every subsequent request:
  
  - Spring Security retrieves `SecurityContext`
  
  - Extracts `Authentication`
  
  - Extracts `GrantedAuthority` list

> At this point, Spring knows:
> 
> - who the user is
> 
> - what authorities / roles they have

---

### **3️⃣ Request Hits Security Filter Chain**

- Request reaches protected endpoint

- Authorization rules are evaluated:
  
  - `hasAuthority("VIEWACCOUNT")`
  
  - `hasRole("USER")`
  
  - `hasAnyAuthority(...)`
  
  - `access("SpEL expression")`

---

### **4️⃣ Authorization Decision**

Spring internally asks:

> **Does this user’s authorities satisfy the rule for this endpoint?**

- ✅ YES → request proceeds to controller

- ❌ NO → request blocked

---

### **5️⃣ If Authorization FAILS**

- Spring Security:
  
  - Throws `AccessDeniedException`
  
  - Returns **403 Forbidden**
  
  - Publishes `AuthorizationDeniedEvent`

Possible backend actions:

- Audit logging

- Security alerts

- Email / monitoring hooks

---

### **6️⃣ If Authorization SUCCEEDS**

- Controller executes normally

- **No authorization success event by default**  
  (to avoid massive noise)

---

## 🧩 Where Authorities & Roles Are Checked

| Layer                       | What Happens                           |
| --------------------------- | -------------------------------------- |
| `UserDetailsService`        | Authorities loaded from DB             |
| `AuthenticationProvider`    | Authorities attached to Authentication |
| `SecurityContextHolder`     | Stores Authentication                  |
| `FilterSecurityInterceptor` | Enforces authorization rules           |
| `AuthorizationManager`      | Makes allow / deny decision            |

---

## 🏷️ Authorities vs Roles (In the Flow)

- **Authorities**
  
  - Compared **directly**
  
  - Exact string match
  
  - Fine-grained

- **Roles**
  
  - Internally treated as authorities
  
  - Always prefixed with `ROLE_`
  
  - `hasRole("USER")` → checks `ROLE_USER`

---

## 🚨 Failure Codes (Interview Gold)

| Scenario                    | HTTP Code |
| --------------------------- | --------- |
| Wrong credentials           | **401**   |
| Logged in but no permission | **403**   |

---

## 🧠 One-Line Memory Hook

> **AuthN creates the SecurityContext, AuthZ evaluates it on every request.**

---

## 🧾 When to Use What (Decision Shortcut)

- Few APIs, many actions → **Authorities**

- Many APIs, business-level access → **Roles**

- Complex logic → `access()` with SpEL

- Auditing denied access → Authorization events
