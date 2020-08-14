package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.Models.MutualFunds;

@Repository
@EnableTransactionManagement
public interface FundsDao extends JpaRepository<MutualFunds, Integer> {
	
}
