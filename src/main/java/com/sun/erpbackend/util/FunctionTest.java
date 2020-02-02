package com.sun.erpbackend.util;

import java.util.Date;

public class FunctionTest {
	public static void main(String args[]) {
		Date date = new Date();
		System.out.println(date.toInstant().toString());
	}
}
