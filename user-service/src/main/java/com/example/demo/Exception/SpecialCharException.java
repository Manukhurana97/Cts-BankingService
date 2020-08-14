package com.example.demo.Exception;

@SuppressWarnings("serial")
public class SpecialCharException extends RuntimeException  {

	private final String mgs;
	public SpecialCharException(String mgs) {
		this.mgs = mgs;
	}
	
	public String getmessage()
	{
		return mgs;
	}
}
