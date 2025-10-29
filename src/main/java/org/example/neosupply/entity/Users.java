package org.example.neosupply.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.neosupply.enumeration.Role;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private String password;
    private Role role;
    private Boolean active;

}
