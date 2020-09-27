package com.example.demo.Exception;

@SuppressWarnings("serial")
public class ConflictError extends RuntimeException {
	
	private final String mgs;
	public ConflictError(String mgs) {
		this.mgs = mgs;
	}
	
	public String getmessage()
	{
		return mgs;
	}
}
