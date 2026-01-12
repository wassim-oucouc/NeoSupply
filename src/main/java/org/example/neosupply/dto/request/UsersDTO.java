package org.example.neosupply.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.neosupply.enumeration.Role;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class UsersDTO {

    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private String password;
    private Set<Role> roles;
    private Boolean active;
}
