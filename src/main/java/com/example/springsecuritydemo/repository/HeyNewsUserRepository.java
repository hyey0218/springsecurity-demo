package com.example.springsecuritydemo.repository;

import com.example.springsecuritydemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface HeyNewsUserRepository extends JpaRepository<User, Long>, HeyNewsUserRepositoryCustom {

    User findByName(String name);

    @Query("select tbl from User tbl where tbl.isDelete = false and tbl.id = :id")
    User findEnabledById(Long id);

    @Transactional
    @Modifying
    @Query("update User tbl set tbl.isDelete = true where tbl.id = :id")
    int disableById(Long id);
}
