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
	 * 
	 * @param numerator	分子>=0
	 * @param denominator 分母>0
	 * @throws Exception 
	 */
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
	 * @param y
	 * @return this.value + y.value
	 * @throws Exception 
	 */
	public Number plus(Number y) throws Exception {
		Number result = new Number(
				this.value[NUMERATOR_PART] * y.value[DENOMINATOR_PART] + this.value[DENOMINATOR_PART] * y.value[NUMERATOR_PART], 
				this.value[DENOMINATOR_PART] * y.value[DENOMINATOR_PART]
						);
		result.value[INTEGRAL_NUMBER_PART] += this.value[INTEGRAL_NUMBER_PART] + y.value[INTEGRAL_NUMBER_PART];
		
		return result;
	}

	/**
	 * 减法运算
	 * 
	 * @param y
	 * @return this.value - y.value
	 * @throws Exception 
	 */
	public Number subtract(Number y) throws Exception {
		Number result = new Number(
				this.value[NUMERATOR_PART] * y.value[DENOMINATOR_PART] - this.value[DENOMINATOR_PART] * y.value[NUMERATOR_PART]
						,
				this.value[DENOMINATOR_PART] * y.value[DENOMINATOR_PART]
						);
		result.value[INTEGRAL_NUMBER_PART] += this.value[INTEGRAL_NUMBER_PART] - y.value[INTEGRAL_NUMBER_PART];
		
		return result;
	}

	/**
	 * 乘法运算
	 * 
	 * @param y
	 * @return this.value * y.value
	 * @throws Exception 
	 */
	public Number multiply(Number y) throws Exception {
		Number result = new Number(
				(this.value[INTEGRAL_NUMBER_PART]*this.value[DENOMINATOR_PART] + this.value[NUMERATOR_PART])*
				(y.value[INTEGRAL_NUMBER_PART] * y.value[DENOMINATOR_PART] + y.value[NUMERATOR_PART])
						,
				this.value[DENOMINATOR_PART] * y.value[DENOMINATOR_PART]
						);
		
		return result;

	}

	/**
	 * 除法运算
	 * 
	 * @param y
	 * @return this.value / y.value
	 * @throws Exception 
	 */
	public Number divide(Number y) throws Exception {
		Number result = new Number(
				(this.value[INTEGRAL_NUMBER_PART]*this.value[DENOMINATOR_PART] + this.value[NUMERATOR_PART])*y.value[DENOMINATOR_PART]
						,
				(y.value[INTEGRAL_NUMBER_PART] * y.value[DENOMINATOR_PART] + y.value[NUMERATOR_PART])*this.value[DENOMINATOR_PART]
						);
		
		return result;

	}

}
