# Database & Custom User Authentication (Spring Security)

This section explains **how Spring Security loads users for authentication**  
— starting from **JDBC-based authentication** and evolving into a **production-ready custom UserDetailsService**.

> **Goal:** Move from framework-provided user storage to **business-controlled authentication**.

---

## What This Section Achieves

- Understand **how Spring Security fetches users from a database**

- Learn **why JdbcUserDetailsManager is limited**

- Implement **Custom UserDetailsService** (industry standard)

- Use **DaoAuthenticationProvider without customization**

- Apply **secure password handling using DelegatingPasswordEncoder**

---

## Authentication Approaches Covered

| Approach                   | Purpose            | Production Ready |
| -------------------------- | ------------------ | ---------------- |
| InMemoryUserDetailsManager | Learning & demos   | ❌                |
| JdbcUserDetailsManager     | DB-backed learning | ⚠️ Limited       |
| Custom UserDetailsService  | Full control       | ✅ Yes            |

---

## JDBC Authentication (Learning Baseline)

JdbcUserDetailsManager is used to understand **database-backed authentication** using Spring Security’s **default schema**.

### Why JDBC Exists

- Shows how Spring Security reads users from DB

- Minimal code, fast setup

- Good for **learning**, not customization

### Limitations

- Forced schema (`users`, `authorities`)

- Hard to extend for real business needs

- Rarely used in production

### Quick Steps — JDBC Authentication

```java
1. Add spring-boot-starter-data-jpa 
2. Create DB using Spring Security default schema 
3. Configure JdbcUserDetailsManager bean 
4. Verify authentication via DB users
```

📌 **Takeaway:** JDBC is a stepping stone, not the destination.

---

## Custom UserDetailsService (Production Approach)

This is the **most important part of the section**.

Spring Security allows **full customization** by implementing `UserDetailsService`.

### Why Custom UserDetailsService

- Full control over **tables & columns**

- Business-driven user model

- No forced schema

- Works with default `DaoAuthenticationProvider`

---

## How Authentication Works (High-Level)

1. User submits username & password

2. `DaoAuthenticationProvider` is used (default)

3. Provider calls `loadUserByUsername()`

4. Custom logic fetches user from DB

5. Domain user → `UserDetails`

6. Password verified using `PasswordEncoder`

7. Authentication result created

📌 **Key Insight:**  
You did **not** replace the AuthenticationProvider —  
you **plugged into it** using UserDetailsService.

---

## Quick Steps — Custom UserDetailsService (Must Know)

### 1️⃣ Create Your Domain User Table

- Use your **own table names & columns**

- Example fields:
  
  - email / username
  
  - password (hashed)
  
  - role / authority

📌 No Spring Security constraints here.

---

### 2️⃣ Create JPA Entity & Repository

- Map your user table to a JPA entity

- Create repository method to fetch user by username/email

```java
Optional<Customer> findByEmail(String email);
```

---

### 3️⃣ Implement UserDetailsService

Create a class that implements `UserDetailsService`.

```java
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                customer.getEmail(),
                customer.getPwd(),
                List.of(new SimpleGrantedAuthority(customer.getRole()))
        );
    }
}
`
```

📌 This is the **only mandatory integration point**.

---

### 4️⃣ Remove Other UserDetails Beans

- ❌ Do NOT keep:
  
  - `InMemoryUserDetailsManager`
  
  - `JdbcUserDetailsManager`

Spring Security must see **only one** `UserDetailsService`.

---

### 5️⃣ Keep PasswordEncoder Configuration As-Is

```java
@Bean
PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
}

```

📌 Password validation remains automatic.

---

### 6️⃣ Authentication Flow Remains Unchanged

- `DaoAuthenticationProvider` is reused

- Calls your `loadUserByUsername()`

- Uses `PasswordEncoder.matches()`

No filters, no managers, no providers need to change.

---

## 🧠 Quick Mental Model

> **Only user loading is customized — authentication logic stays untouched.**

---

## Mapping Domain User → UserDetails (Critical)

Inside `loadUserByUsername()`:

- Username → email (or business identifier)

- Password → hashed password from DB

- Role → converted to GrantedAuthority

📌 This is **where authentication logic meets business data**.

---

## Password Handling (Production Standard)

- Passwords are **never stored in plain text**

- Password hashing handled using `PasswordEncoder`

- `DelegatingPasswordEncoder` is used (recommended)

### Why DelegatingPasswordEncoder

- Supports algorithm upgrades

- Uses `{bcrypt}` prefix by default

- Avoids hard-coding encoders

- Future-proof

📌 **Never define BCryptPasswordEncoder directly as a bean**

---

## Important Design Decisions

- ✔ Used `UserDetailsService`, not `UserDetailsManager`

- ✔ Let Spring Security manage AuthenticationProvider

- ✔ Used JPA for DB interaction

- ✔ Used DelegatingPasswordEncoder

- ❌ Did not customize AuthenticationProvider (yet)

---

## What You Should Remember (Interview Gold)

- `DaoAuthenticationProvider` is default

- `UserDetailsService` controls user loading

- JDBC forces schema, Custom does not

- PasswordEncoder handles hashing + verification

- Spring Security compares passwords — not you

---

# 
