package main;

import pojo.Number;

import java.util.Random;

public class Main {
	public static void main(String[] args) throws Exception {
		String s = "1'13/16";
		String s1 = "7/16";
		String s3 = new String();

		s3 = Number.divide(s, s1);
		System.out.println(s3);


	}

}
