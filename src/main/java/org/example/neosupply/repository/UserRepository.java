package org.example.neosupply.repository;


import org.example.neosupply.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Boolean existsUsersByEmail(String email);

}
