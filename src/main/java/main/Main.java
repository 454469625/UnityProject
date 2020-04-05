package main;

import pojo.Number;

public class Main {
	public static void main(String[] args) throws Exception {
		Number num1 = new Number(8 + 3, 4);
		Number num2 = new Number(4, 2);
		Number rs = num1.plus(num2);
		System.out.println(num1 + " + " + num2 + " = " + rs);
		rs = num1.subtract(num2);
		System.out.println(num1 + " - " + num2 + " = " + rs);
		rs = num1.multiply(num2);
		System.out.println(num1 + " * " + num2 + " = " + rs);
		rs = num1.divide(num2);
		System.out.println(num1 + " / " + num2 + " = " + rs);
	}
}
