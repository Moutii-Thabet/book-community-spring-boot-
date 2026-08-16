package com.moutii.book_community.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByPwResetToken(String resetToken);
    Optional<User> findByEmailIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String username);

}
