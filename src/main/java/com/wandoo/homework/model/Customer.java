package com.wandoo.homework.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    @Column(name="id", unique=true)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    @NotBlank
    @Column(name="first_name")
    private String firstName;

    @NotBlank
    @Column(name="last_name")
    private String lastName;

    @NotBlank
    @Column(name="email")
    private String email;

    @NotBlank
    @Column(name="phone_number")
    private String phoneNumber;

    @NotBlank
    @Column(name="person_id_number")
    private String personIdNumber;
}
