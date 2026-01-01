# 👤 Creating Users with InMemoryUserDetailsManager

## 📌 Purpose of This Section

This section introduces **the simplest possible user management model** in Spring Security using:

> `InMemoryUserDetailsManager`

The goal is **NOT production usage**, but to establish:

- How Spring Security loads users

- How authentication works before databases are involved

- A baseline to compare against JDBC and custom user stores

---

## 🎯 Why This Section Matters

Before databases, JPA, or custom tables, you must understand:

- Where Spring Security gets user details from

- How `UserDetailsService` fits into authentication

- How roles/authorities are resolved

This section builds that **mental foundation**.

---

## 🧠 Key Concepts Introduced

### 1️⃣ InMemoryUserDetailsManager

- Framework-provided `UserDetailsService` implementation

- Stores users **in application memory**

- Users are defined **in code**

Used mainly for:

- Learning

- Demos

- Prototypes

- Quick testing

---

### 2️⃣ UserDetails & User

- Spring Security represents users using `UserDetails`

- `User` is a built-in implementation

- Contains:
  
  - username
  
  - password
  
  - authorities

---

### 3️⃣ Authentication Flow (High Level)

- User submits credentials

- `DaoAuthenticationProvider`:
  
  - Calls `UserDetailsService`
  
  - Fetches user from memory
  
  - Verifies password via `PasswordEncoder`

- Authentication succeeds or fails

📌 Same flow applies later for JDBC and custom users.

---

## 🧩 Minimal Code Used

```java
@Bean
UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();

    UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder().encode("admin123"))
            .roles("ADMIN")
            .build();

    return new InMemoryUserDetailsManager(user, admin);
}

```

---

## ⚠️ Important Limitations

- ❌ Users reset on application restart

- ❌ Not scalable

- ❌ Not suitable for production

- ❌ No persistence

- ❌ No user management APIs

This approach exists **only to teach the flow**.

---

## 🧠 One-Line Takeaway

> **InMemoryUserDetailsManager teaches how authentication works — not how real users are stored.**
