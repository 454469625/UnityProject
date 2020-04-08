package pojo;

import java.util.Random;

public class Number {
	/**
	 * 数的类型:<br/>
	 * 1.自然数<br/>
	 * 2.真分数
	 */
	Type type;

	/** [整数,分子,分母] */
	public int[] value = new int[3];

	/** 整数部分 */
	public static final byte INTEGRAL_NUMBER_PART = 0;

	/** 分子部分 */
	public static final byte NUMERATOR_PART = 1;

	/** 分母部分 */
	public static final byte DENOMINATOR_PART = 2;

	@Override
	public String toString() {
		if(value[NUMERATOR_PART] == 0) {
			// 分子为0
			return String.valueOf(value[INTEGRAL_NUMBER_PART]);
		} else if(value[INTEGRAL_NUMBER_PART] == 0) {
			// 整数部分为0
			return String.valueOf(value[NUMERATOR_PART]) + "/" + String.valueOf(value[DENOMINATOR_PART]);
		}else{
			// 都不为0
			return concat(value[INTEGRAL_NUMBER_PART], value[NUMERATOR_PART], value[DENOMINATOR_PART]);
		}
	}
	
	/**
	 * 拼出x'y/z
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static String concat(int x, int y, int z) {
		return String.valueOf(x) + "'" + String.valueOf(y) + "/" + String.valueOf(z);
	}

	/**
	 * 求最大公约数
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static final int gcd(int a, int b) {
		return a == b ? a : gcd(a > b ? a - b : a, b > a ? b - a : b);
	}

	/**
	 * 
	 * @param range 控制生成范围
	 */
	public Number(int range) {
		Random r = new Random();

		value[INTEGRAL_NUMBER_PART] = r.nextInt(range);
		value[NUMERATOR_PART] = r.nextInt(range);
		value[DENOMINATOR_PART] = 1 + r.nextInt(range - 1);

		// 分子为0,或分母小于等于分子,视为自然数
		if (value[NUMERATOR_PART] == 0 || value[DENOMINATOR_PART] <= value[NUMERATOR_PART]) {
			type = Type.NaturalNumber;
			// 分子,分母分别设置为0和1
			value[NUMERATOR_PART] = 0;
			value[DENOMINATOR_PART] = 1;
		} else {
			type = Type.TrueFraction;
		}
	}

	/**
	 * 直接赋值构造
	 * @param numerator	分子>=0
	 * @param denominator 分母>0
	 * @throws Exception 
	 */
	public Number(int integral, int numerator, int denominator) throws Exception {
		if(numerator < 0 ) {
			throw new Exception("分子小于0");
		}
		if(denominator <= 0) {
			throw new Exception("分母小于等于0");
		}
		int greatestCommonDivisor = gcd(numerator,denominator);
		numerator /= greatestCommonDivisor;
		denominator /= greatestCommonDivisor;
		
		value[INTEGRAL_NUMBER_PART] = integral;
		value[NUMERATOR_PART] =  numerator;
		value[DENOMINATOR_PART] = denominator;
		
		// 分子等于0
		if(value[NUMERATOR_PART] == 0) {
			type = Type.NaturalNumber;
		}else {
			type = Type.TrueFraction;
		}
	}

	public Number(int numerator, int denominator) throws Exception {
		if(numerator < 0 ) {
			throw new Exception("分子小于0");
		}
		if(denominator <= 0) {
			throw new Exception("分母小于等于0");
		}
		int greatestCommonDivisor = gcd(numerator,denominator);
		numerator /= greatestCommonDivisor;
		denominator /= greatestCommonDivisor;

		value[INTEGRAL_NUMBER_PART] = numerator / denominator;
		value[NUMERATOR_PART] =  numerator % denominator;
		value[DENOMINATOR_PART] = denominator;

		// 分子等于0
		if(value[NUMERATOR_PART] == 0) {
			type = Type.NaturalNumber;
		}else {
			type = Type.TrueFraction;
		}
	}

	/**
	 * 加法运算
	 *
	 * @param a , b
	 * @return a.value + b.value
	 * @throws Exception
	 */
	public static String plus(String a,String b) throws Exception {
		Number n1 = stringToNumber(a);
		Number n2 = stringToNumber(b);
		Number result = new Number(
				n1.value[NUMERATOR_PART] * n2.value[DENOMINATOR_PART] + n1.value[DENOMINATOR_PART] * n2.value[NUMERATOR_PART],
				n1.value[DENOMINATOR_PART] * n2.value[DENOMINATOR_PART]
						);
		result.value[INTEGRAL_NUMBER_PART] += n1.value[INTEGRAL_NUMBER_PART] + n2.value[INTEGRAL_NUMBER_PART];

		return result.toString();
	}

	/**
	 * 减法运算
	 * 前参减后参
	 * @param a,b
	 * @return a.value - b.value
	 * @throws Exception 
	 */
	public static String subtract(String a,String b) throws Exception {
		Number n1 = stringToNumber(a);
		Number n2 = stringToNumber(b);
		Number result = new Number(
				n1.value[NUMERATOR_PART] * n2.value[DENOMINATOR_PART] - n1.value[DENOMINATOR_PART] * n2.value[NUMERATOR_PART]
						,
				n1.value[DENOMINATOR_PART] * n2.value[DENOMINATOR_PART]
						);
		result.value[INTEGRAL_NUMBER_PART] += n1.value[INTEGRAL_NUMBER_PART] - n2.value[INTEGRAL_NUMBER_PART];
		
		return result.toString();
	}

	/**
	 * 乘法运算
	 * 
	 * @param a,b
	 * @return a.value * b.value
	 * @throws Exception 
	 */
	public static String multiply(String a,String b) throws Exception {
		Number n1 = stringToNumber(a);
		Number n2 = stringToNumber(b);
		Number result = new Number(
				(n1.value[INTEGRAL_NUMBER_PART]*n1.value[DENOMINATOR_PART] + n1.value[NUMERATOR_PART])*
				(n2.value[INTEGRAL_NUMBER_PART] * n2.value[DENOMINATOR_PART] + n2.value[NUMERATOR_PART])
						,
				n1.value[DENOMINATOR_PART] * n2.value[DENOMINATOR_PART]
						);
		
		return result.toString();

	}

	/**
	 * 除法运算
	 * 
	 * @param a,b
	 * @return a.value / b.value
	 * @throws Exception 
	 */
	public static String divide(String a,String b) throws Exception {
		Number n1 = stringToNumber(a);
		Number n2 = stringToNumber(b);
		Number result = new Number(
				(n1.value[INTEGRAL_NUMBER_PART]*n1.value[DENOMINATOR_PART] + n1.value[NUMERATOR_PART])*n2.value[DENOMINATOR_PART]
						,
				(n2.value[INTEGRAL_NUMBER_PART] * n2.value[DENOMINATOR_PART] + n2.value[NUMERATOR_PART])*n1.value[DENOMINATOR_PART]
						);
		
		return result.toString();

	}

	public static Number stringToNumber(String s) throws Exception{
		//找到 ' 的位置
		int i;								//i用于标识 ’ 的位置
		int j;								//j用于标识 / 的位置
		Number n;
		for (i = 0, j = 0; i < s.length() || j < s.length(); ) {
			if (s.charAt(i) != '\'') {
				i++;
			}
			if (s.charAt(j) != '/') {
				j++;
			} else {
				break;
			}
		}
		if (i < s.length() || j < s.length()) {
			if (s.charAt(i) == '\'') {
				n = new Number(Integer.parseInt(s.substring(0, i)), Integer.parseInt(s.substring(i + 1, j)), Integer.parseInt(s.substring(j + 1)));
			} else{
				n = new Number(Integer.parseInt(s.substring(0, j)), Integer.parseInt(s.substring(j + 1)));
			}
		} else {
			n = new Number(Integer.parseInt(s), 1);
		}
		return n;
	}



}
