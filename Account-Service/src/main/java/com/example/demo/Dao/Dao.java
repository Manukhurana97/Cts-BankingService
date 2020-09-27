package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.Model.Account;

@Repository
@EnableTransactionManagement
public interface Dao extends JpaRepository<Account, Long> {
	public Account findByaccountno(Long accountno);
	public List<Account> findByPan(String pan);
	
}
