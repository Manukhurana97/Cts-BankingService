package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.Models.Temp;

@Repository
@EnableTransactionManagement
public interface Tempdao extends JpaRepository<Temp, Integer> {
	
	public Temp findByusername(String username);
	public Temp findByusernameAndId(String username, int id);
	public Temp findByPan(String Pan);
	public void deleteByIdAndPan(int id, String pan);
	
}
