package io.github.fysia.auction_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "display_name", nullable = false, length = 255)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private UserRole role;

    protected User() {}

    public User(String email, String passwordHash, String displayName, UserRole role){
        this.email = email;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
        this.role = role;
    }

    public Long getId() {return id; }
    public String getEmail() {return email;}
    public String getPasswordHash() {return  passwordHash;}
    public String getDisplayName() {return displayName;}
    public UserRole getRole() {return role;}
}
