package com.example.springsecuritydemo.repository;

import com.example.springsecuritydemo.entity.JoinsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface HeyNewsUserRepository extends JpaRepository<JoinsUser, Long> {

    JoinsUser findByName(String name);

    @Query("select tbl from JoinsUser tbl where tbl.isDelete = false and tbl.id = :id")
    JoinsUser findEnabledById(Long id);

    @Transactional
    @Modifying
    @Query("update JoinsUser tbl set tbl.isDelete = true where tbl.id = :id")
    int disableById(Long id);
}
