package com.itm.ithive.repository;

import com.itm.ithive.model.Enums.Status;
import com.itm.ithive.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    List<Users> findByStatus(Status status);
}
