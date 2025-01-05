package org.example.paymenttest.repository;

import org.example.paymenttest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}
