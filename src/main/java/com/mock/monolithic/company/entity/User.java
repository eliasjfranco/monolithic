package com.mock.monolithic.company.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Company company;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {}
}