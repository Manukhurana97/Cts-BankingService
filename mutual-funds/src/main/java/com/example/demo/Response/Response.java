package com.example.demo.Response;

import java.util.List;
import java.util.Optional;

import com.example.demo.Models.MutualFunds;
import com.example.demo.Models.OrderFunds;
import com.example.demo.Models.Temp;

public class Response {

	public String mgs;
	public List<MutualFunds> list;
	public Optional<MutualFunds> byid;  
	public List<Temp> temp_list;
	
	List<OrderFunds> O_list;
	public OrderFunds orderFunds;
	
	
	

	public List<Temp> getTemp_list() {
		return temp_list;
	}

	public void setTemp_list(List<Temp> temp_list) {
		this.temp_list = temp_list;
	}

	public Optional<MutualFunds> getByid() {
		return byid;
	}

	public void setByid(Optional<MutualFunds> byid) {
		this.byid = byid;
	}

	public List<MutualFunds> getList() {
		return list;
	}

	public void setList(List<MutualFunds> list) {
		this.list = list;
	}

	public String getMgs() {
		return mgs;
	}

	public void setMgs(String mgs) {
		this.mgs = mgs;
	}

	public List<OrderFunds> getO_list() {
		return O_list;
	}

	public void setO_list(List<OrderFunds> o_list) {
		O_list = o_list;
	}

	public OrderFunds getOrderFunds() {
		return orderFunds;
	}

	public void setOrderFunds(OrderFunds orderFunds) {
		this.orderFunds = orderFunds;
	}

	
	
	
	

}
