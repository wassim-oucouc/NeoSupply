package org.example.neosupply.dto.response;

import lombok.Data;
import org.example.neosupply.enumeration.Role;

import java.util.HashSet;
import java.util.Set;


@Data
public class UserDtoResponse {

    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private Set<Role> roles;
    private Boolean active;
}
