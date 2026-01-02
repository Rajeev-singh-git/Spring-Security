# 🔐 Section 6 — Authentication Providers & Profile-Based Security

## 🎯 Goal of This Section

This section focuses on **how Spring Security actually authenticates users** and **how to customize that process** for real-world enterprise needs.

By the end of this section, we:

- Fully understand **AuthenticationProvider**

- Implement a **custom AuthenticationProvider**

- Control authentication behavior **based on environment (profiles)**

---

## 🧠 Core Concepts Covered

### 1️⃣ AuthenticationProvider (The Real Auth Engine)

- `AuthenticationProvider` is where **authentication decisions are made**

- Default provider used by Spring Security:
  
  - `DaoAuthenticationProvider`

- It:
  
  - Loads user via `UserDetailsService`
  
  - Validates password using `PasswordEncoder`

---

### 2️⃣ Why Custom AuthenticationProvider?

Default provider is **not enough** when:

- You need **custom authentication rules**
  
  - Age check
  
  - Country check
  
  - Environment-based behavior

- You want **multiple authentication styles**
  
  - Username/password
  
  - OAuth2
  
  - Legacy systems (JAAS)

👉 Solution: **Write your own AuthenticationProvider**

---

## 🧩 AuthenticationProvider Contract

Every `AuthenticationProvider` must implement:

```java
Authentication authenticate(Authentication authentication);
boolean supports(Class<?> authentication);
```

### 🔹 `supports()`

- Tells Spring Security **which AuthenticationToken this provider handles**

- Example:

```java
return UsernamePasswordAuthenticationToken.class 
       .isAssignableFrom(authentication);
```

### 🔹 `authenticate()`

- Actual authentication logic

- Load user

- Validate credentials

- Return authenticated `Authentication` object or throw exception

---

## 🏗️ Custom AuthenticationProvider Implemented

### `EazyBankUsernamePwdAuthenticationProvider`

- Replaces `DaoAuthenticationProvider`

- Uses:
  
  - Custom `UserDetailsService`
  
  - `PasswordEncoder`

- Fully controls authentication flow

---

## 🌍 Environment-Based Authentication (Profiles)

### Real-World Problem

| Environment    | Requirement                |
| -------------- | -------------------------- |
| DEV / QA / UAT | Easy access (any password) |
| PROD           | Strict security            |

QA teams **should not remember passwords**  
Production **must remain secure**

---

## ✅ Solution: Profile-Based Providers

We implemented **two AuthenticationProviders**:

| Profile           | Provider                                     | Password Check |
| ----------------- | -------------------------------------------- | -------------- |
| `!prod` (default) | `EazyBankUsernamePwdAuthenticationProvider`  | ❌ Skipped      |
| `prod`            | `EazyBankProdUsernameAuthenticationProvider` | ✅ Enforced     |

Activated using:

```java
@Profile("prod") 
@Profile("!prod")
```

✔ Only **one provider bean exists at runtime**  
✔ ProviderManager automatically uses the correct one

---

## 🔐 Profile-Based Security Configuration

Same profile strategy applied to:

- `ProjectSecurityConfig` (non-prod)

- `ProjectSecurityProdConfig` (prod)

This enables:

- Relaxed rules in lower environments

- Strict rules in production

---

## ⚙️ Spring Boot Profiles Used

- `application.properties` → default / non-prod

- `application_prod.properties` → production

- Profile activation via:
  
  - `spring.profiles.active`
  
  - Environment variables (recommended)

---

## 🧠 Key Takeaways

- **AuthenticationProvider is the core of authentication**

- `supports()` decides **which provider is used**

- Profiles decide **which provider exists**

- No conditionals, no hacks — only clean Spring design

- This is **how authentication is customized in real projects**

---

## 🚀 Outcome

After this section, we can:

- Control authentication behavior per environment

- Extend authentication safely

- Confidently reason about Spring Security internals

## 
