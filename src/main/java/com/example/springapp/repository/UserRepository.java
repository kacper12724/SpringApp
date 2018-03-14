package com.example.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.springapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	User findByRegcode(String code);
	
	@Query("select u from User u where u.regcode = ?1")
    public User findByRegcodev(String regcode);
	
	@Transactional
	@Modifying
	@Query("update User u set u.status = ?3 where u.username = ?1 and u.regcode = ?2")
    void activateAccount(String username, String regcode, String status);
}
