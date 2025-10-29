package org.example.neosupply.dto.response;

import lombok.Data;
import org.example.neosupply.enumeration.Role;


@Data
public class UserDtoResponse {

    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private Role role;
    private Boolean active;
}
