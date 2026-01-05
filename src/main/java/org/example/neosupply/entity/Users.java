package org.example.neosupply.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.neosupply.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    private Boolean active;

}
