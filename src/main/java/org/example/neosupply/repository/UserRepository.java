package org.example.neosupply.repository;


import org.apache.catalina.User;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.entity.Users;
import org.example.neosupply.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Boolean existsUsersByEmail(String email);

    Optional<Users> findUsersByEmail(String email);

    List<Users> findByRolesContaining(Role role);

    Role getUsersByEmail(String email);
}
