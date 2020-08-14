package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.Models.OrderFunds;
import com.example.demo.Models.Temp;

@Repository
@EnableTransactionManagement
public interface OrderDao extends JpaRepository<OrderFunds, Integer> {
	
	public List<OrderFunds> findBypan(String pan);
	public OrderFunds findBypanAndId(String pan , int id);
	

}
