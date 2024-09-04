package com.example.markdownnotes.model;

import javax.persistence.*;

/*
 * Example JPA Entity
 * Maybe, Not to used
 */

@Entity
@Table(name = "userexample")
public class UserExample {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Getters and Setters
}
