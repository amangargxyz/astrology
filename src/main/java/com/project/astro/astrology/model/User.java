package com.project.astro.astrology.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @Column(name="first_name")
    private String firstName;

    @Size(max = 20)
    @Column(name="last_name")
    private String lastName;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    private Long mobile;

    private String dob;

    @Column(name="birth_time")
    private String birthTime;

    @Column(name="birth_place")
    private String birthPlace;

    @Column(name="is_astrologer")
    private Boolean isAstrologer;

    @Column(name="is_admin")
    private Boolean isAdmin;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Astrologer astrologer;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Client client;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, String username, String email, String password, Long mobile,
                String dob, String birthTime, String birthPlace, Boolean isAstrologer, Boolean isAdmin, Boolean isApproved) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.dob = dob;
        this.birthTime = birthTime;
        this.birthPlace = birthPlace;
        this.isAstrologer = isAstrologer;
        this.isAdmin = isAdmin;
        this.isApproved = isApproved;
    }
}
