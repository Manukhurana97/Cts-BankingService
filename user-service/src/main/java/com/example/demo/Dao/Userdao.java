package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.Model.Users;

@Repository
@EnableTransactionManagement
public interface Userdao extends JpaRepository<Users, String>{
	public Users  findByUsername(String username);
	public Users  findByPan(String pan);
	

}
