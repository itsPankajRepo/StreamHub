package com.stream.hub.userMgmt.repo;

import com.stream.hub.userMgmt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    @Query("update User u set u.password = ?2 where u.username = ?1")
    @Modifying
    @Transactional
    int updatePassword(String username, String encodedPassword);
}
