# 🔐 Spring Security Handbook

A project-based handbook for learning Spring Security from fundamentals to enterprise-grade authentication and authorization.

Instead of covering isolated features, this repository teaches Spring Security as a connected system—starting with default configuration and progressing through authentication, browser security, authorization, JWT, and OAuth2.

Each section is an independent Spring Boot project with its own README, making it easy to study concepts individually or follow the complete learning path.

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Security 6
- Maven
- MySQL
- Thymeleaf
- Angular (for SPA examples)

---

## 🚀 Getting Started

1. Clone this repository.
2. Complete the local environment setup by following the **[Project Setup Guide](SETUP.md)**.
3. Start with **Section 1** and continue sequentially, as each section builds upon concepts introduced earlier.

> **Recommended:** Follow the sections in order to build a strong mental model of Spring Security.

---

# 📚 Learning Path

## 🟢 Foundations

| Section | Focus | README |
|---------|-------|--------|
| **1** | Default Security Configuration | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/01.%20Using%20Default%20Security%20Configuration/README.md) |
| **2** | Custom Security Configuration | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/02.%20Change%20Default%20Security%20Configuration/README.md) |
| **3** | Creating & Managing Users | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/03.%20Creating%20Users%20using%20InMemoryUserDetailsManager/README.md) |

---

## 🔵 Authentication

| Section | Focus | README |
|---------|-------|--------|
| **4** | JDBC Authentication | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/04.%20Defining%20and%20Managing%20Users%20in%20Spring%20Security%20(Database-backed)/README.md) |
| **5** | Custom User Authentication | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/05.%20Password%20Management%20with%20Password%20Encoders/README.md) |
| **6** | Authentication Providers | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/06.%20Implementing%20Authentication%20Provider/README.md) |

---

## 🟣 Browser Security

| Section | Focus | README |
|---------|-------|--------|
| **7** | Browser Login, Sessions & SecurityContext | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/blob/main/07.%20Sprinng%20Security%20Customizations/README.md) |
| **8** | CORS & CSRF | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/08.%20CORS%20%26%20CSRF) |

---

## 🟠 Authorization

| Section | Focus | README |
|---------|-------|--------|
| **9** | Authorization | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/09.%20Authorization%20Implementation) |
| **10** | Custom Filters | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/10.%20Custom%20Filters) |
| **12** | Method Security | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/12.%20Method%20Level%20Security) |

---

## 🔴 Modern Authentication

| Section | Focus | README |
|---------|-------|--------|
| **11** | JWT Authentication | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/11.%20JSON%20Web%20Token%20(JWT)) |
| **13** | OAuth2 & OpenID Connect (Theory) | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/13.%20OAuth2%20%26%20OpenID%20connect%20(Theory)) |
| **14** | OAuth2 Login | [Open →](https://github.com/Rajeev-singh-git/Spring-Security/tree/main/14.%20Implementing%20OAUTH2) |

---

# 🎯 Who Is This Repository For?

- Java Backend Developers
- Spring Boot Developers
- Engineers preparing for **SDE-2 / Senior Backend** interviews
- Developers who want to understand Spring Security beyond configuration

---

# 🎯 Learning Outcome

After completing this handbook, you'll be able to:

- Understand Spring Security's authentication architecture
- Design role- and authority-based authorization
- Configure and customize the Spring Security filter chain
- Work confidently with `SecurityContext` and session management
- Secure browser-based applications using CORS and CSRF
- Implement stateless authentication using JWT
- Apply method-level security using Spring Security annotations
- Understand OAuth2, OpenID Connect, and modern authentication flows

More importantly, you'll understand **how these concepts fit together to build secure Spring applications**, rather than learning them as isolated features.
