package com.constx.demo.test.base;


public class BaseTester {

	protected void print(Object result){
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		StackTraceElement element = elements[2];
		System.out.println();
		System.out.println("test method:"+element.getMethodName() + "--------------------------------------------------" );
		System.out.println("test result:" +result);
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println();
	}
}
