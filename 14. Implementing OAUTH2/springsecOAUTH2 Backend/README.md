# 14 : OAuth2 Login with Social Providers (Spring Security)

---

## 🎯 Goal

Enable OAuth2-based authentication in a Spring Boot web application by delegating **user authentication** to external providers (GitHub, Facebook), without building an authorization server.

---

## 🧠 Mental Model / Story

OAuth2 login in Spring Security activates when **three pieces click together**:

1. **Security rules allow OAuth2 login**
2. **Spring knows which authorization servers exist**
3. **Client credentials bind this app to those servers**

Once these are present, Spring Security:

- redirects users to the provider
- exchanges authorization codes
- creates an authenticated `Authentication` object
- resumes the original request

No OAuth2 flow code is written manually.

---

## 🧩 Conceptual Core

> **OAuth2 login is not enabled by dependencies alone.
> It is enabled when `oauth2Login()` is part of the security filter chain and a populated `ClientRegistrationRepository` exists.**

---

## 🛠️ Step-by-Step (Complete Flow at One Place)

---

## 1️⃣ Create Security Configuration (Entry Switch)

OAuth2 login **does nothing** unless explicitly enabled in the filter chain.

```java
@Beanpublic SecurityFilterChainsecurityFilterChain(HttpSecurity httpSecurity)throws Exception {

    httpSecurity
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/secure").authenticated()
            .anyRequest().permitAll()
        )
        .formLogin(Customizer.withDefaults())// traditional login
        .oauth2Login(Customizer.withDefaults());// OAuth2 loginreturn httpSecurity.build();
}
```

### What this establishes

- `/secure` requires authentication
- Users may authenticate via:
  - username/password
  - OAuth2 provider
- Spring Security auto-selects the correct flow

---

## 2️⃣ ClientRegistration — What It Represents

A `ClientRegistration` is **Spring’s internal model** of:

> “How *this application* is registered with an OAuth2 provider”

It contains:

- client_id
- client_secret
- scopes
- authorization endpoints
- token endpoints
- user-info endpoints

---

## 3️⃣ Provider Metadata via `CommonOAuth2Provider`

Spring Security ships with preconfigured OAuth2 metadata for popular providers.

```java
CommonOAuth2Provider.GITHUB
CommonOAuth2Provider.FACEBOOK
```

These already define:

- authorization URI
- token URI
- scopes
- user-info mapping

Only **client identity** is missing.

---

## 🛠️ Approach 1 — Explicit Java Configuration

### 3.1 Create ClientRegistration objects

```java
private ClientRegistrationgithubClientRegistration() {return CommonOAuth2Provider.GITHUB
            .getBuilder("github")
            .clientId(gitClientId)
            .clientSecret(gitClientSecret)
            .build();
}private ClientRegistrationfacebookClientRegistration() {return CommonOAuth2Provider.FACEBOOK
            .getBuilder("facebook")
            .clientId(fbClientId)
            .clientSecret(fbClientSecret)
            .build();
}
```

📌 `registrationId` (`github`, `facebook`) becomes the provider key.

---

### 3.2 Register them in memory

```java
@Bean
ClientRegistrationRepositoryclientRegistrationRepository() {returnnewInMemoryClientRegistrationRepository(
            githubClientRegistration(),
            facebookClientRegistration()
    );
}
```

### Result

- OAuth2 providers are now **known** to Spring Security
- Login page auto-renders provider buttons
- OAuth2 login flow is activated

---

## ⚙️ Approach 2 — Properties-Based Auto Configuration

### 3.1 Define properties

```java
spring.security.oauth2.client.registration.github.client-id=...
spring.security.oauth2.client.registration.github.client-secret=...

spring.security.oauth2.client.registration.facebook.client-id=...
spring.security.oauth2.client.registration.facebook.client-secret=...
```

---

### 3.2 What Spring Does Automatically

- Resolves `github`, `facebook` via `CommonOAuth2Provider`
- Builds `ClientRegistration` objects
- Registers an `InMemoryClientRegistrationRepository`
- Wires OAuth2 filters into the chain

📌 **No Java configuration required**

---

## 🔁 Key Observation

> **Both approaches lead to the exact same runtime state.**

| Aspect                     | Java Config | Properties Config |
| -------------------------- | ----------- | ----------------- |
| Uses CommonOAuth2Provider  | ✅           | ✅                 |
| ClientRegistration created | ✅           | ✅                 |
| Repository used            | InMemory    | InMemory          |
| OAuth2 filters             | Same        | Same              |
| Authentication behavior    | Same        | Same              |

Difference is **expression**, not behavior.

---

## 🔐 Authentication Outcome

After login:

- Form login → `UsernamePasswordAuthenticationToken`
- OAuth2 login → `OAuth2AuthenticationToken`

Both implement `Authentication`

Authorization rules stay identical.

---

## ⚠️ Architectural Limitation (Critical)

Social OAuth2 providers:

- ✔ authenticate users
- ✖ cannot manage application roles

No control over:

- admin / user / manager roles
- domain-specific authorization

📌 **Enterprise authorization requires owning the auth server (IAM)** — next section.

---

## 🧠 Quick Recall / Interview Triggers

- OAuth2 login requires `oauth2Login()` in filter chain
- ClientRegistration defines app–provider relationship
- CommonOAuth2Provider removes metadata boilerplate
- Properties vs Java config → same runtime
- Social login ≠ authorization

---

## 🧾 One-Line Summary

**Section 14 establishes OAuth2 login in Spring Security by enabling `oauth2Login()` in the security filter chain and supplying provider registrations—either explicitly via `ClientRegistrationRepository` or implicitly via properties—resulting in delegated authentication but no authorization control.**

---

### 🚧 TODO (Post-Section 14)

##### 1️⃣ ClientRegistration Storage (Production Readiness)

Current:

- `InMemoryClientRegistrationRepository`

Limitations:

- Static registrations (code / config driven)

- Not manageable at runtime

- No multi-tenant support

- Secrets live in config (not ideal for rotation)

- Not suitable for large or dynamic OAuth client sets

**To do**

- Replace with:
  
  - `JdbcClientRegistrationRepository`
  
  - or external config / secrets manager

📌 Deferred intentionally — covered when IAM / Auth Server ownership is introduced.

---

##### 2️⃣ Post-Login Landing Page

Current:

- No explicit default success URL

- Redirect depends on originally requested path

- `/` may result in `404`

**To do**

- Configure:
  
  - `defaultSuccessUrl("/secure")`  
    **or**
  
  - custom `AuthenticationSuccessHandler`

📌 Deferred — login flow is correct, UX completion comes later.

---

### 🔑 One-Line Reality Check

> Section 14 validates **OAuth2 login mechanics**, not production identity ownership.


