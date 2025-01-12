package com.stream.hub.userMgmt.repo;

import com.stream.hub.userMgmt.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    @Query("Update users set status = 'INACTIVE'  where id in (:ids)")
    @Modifying
    @Transactional
    int deleteUserById(List<Long> ids);

    @Query("Update users set status = 'ACTIVE'  where id in (:ids)")
    @Modifying
    @Transactional
    int restoreUser(List<Long> ids);
}
