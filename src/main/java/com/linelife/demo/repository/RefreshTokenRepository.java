package com.linelife.demo.repository;

import com.linelife.demo.model.RefreshToken;
import com.linelife.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Override
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);
    //Optional<RefreshToken> findByUserId(Long userId);

    /*@Modifying
    int deleteByUser(User user);*/
}
