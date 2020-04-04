package pojo;

import java.util.Random;

public class Number {
	/**
	 * 数的类型:<br/>
	 * 1.自然数<br/>
	 * 2.真分数
	 */
	Type type;

	/** 自然数形式为x,真分数形式为x'y/z */
	String value;

	/** [整数,分子,分母] */
	int[] valueArray = new int[3];

	/** 整数部分 */
	public static final byte INTEGRAL_NUMBER_PART = 0;

	/** 分子部分 */
	public static final byte NUMERATOR_PART = 1;

	/** 分母部分 */
	public static final byte DENOMINATOR_PART = 2;

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
	 * 
	 * @param range 控制范围
	 */
	public Number(int range) {
		Random r = new Random();

		valueArray[INTEGRAL_NUMBER_PART] = r.nextInt(range);
		valueArray[NUMERATOR_PART] = r.nextInt(range);
		valueArray[DENOMINATOR_PART] = r.nextInt(range);

		// 分子为0
		if (valueArray[NUMERATOR_PART] == 0 || valueArray[DENOMINATOR_PART] == 0) {
			type = Type.NaturalNumber;
			value = String.valueOf(valueArray[INTEGRAL_NUMBER_PART]);
		} else {
			type = Type.TrueFraction;
			value = concat(valueArray[INTEGRAL_NUMBER_PART], valueArray[NUMERATOR_PART], valueArray[DENOMINATOR_PART]);
		}
	}

	/**
	 * 
	 * @param valueArray 数组长度必须为3
	 */
	public Number(int[] valueArray) {
		this.valueArray = valueArray;

		// 分子或分母为0,视为自然数
		if (valueArray[NUMERATOR_PART] == 0 || valueArray[DENOMINATOR_PART] == 0) {
			type = Type.NaturalNumber;
			value = String.valueOf(valueArray[INTEGRAL_NUMBER_PART]);
		} else {
			type = Type.TrueFraction;
			value = concat(valueArray[INTEGRAL_NUMBER_PART], valueArray[NUMERATOR_PART], valueArray[DENOMINATOR_PART]);
		}
	}

	/**
	 * 
	 * @param type  真分数还是自然数
	 * @param value 确定的一个值
	 */
	public Number(Type type, String value) {
		this.type = type;
		this.value = value;
		switch (this.type) {
			case NaturalNumber:
				valueArray[0] = Integer.parseInt(value);
				break;
			case TrueFraction:
				int index1 = value.indexOf("'");
				int index2 = value.indexOf("/");

				// 整数
				valueArray[INTEGRAL_NUMBER_PART] = Integer.parseInt(value.substring(0, index1));
				// 分子
				valueArray[NUMERATOR_PART] = Integer.parseInt(value.substring(index1 + 1, index2));
				// 分母
				valueArray[DENOMINATOR_PART] = Integer.parseInt(value.substring(index2 + 1));

				break;
		}
	}

	/**
	 * 加法运算
	 * 
	 * @param y
	 * @return this.value + y.value
	 */
	public Number plus(Number y) {
		int[] resultArray = new int[3];

		resultArray[INTEGRAL_NUMBER_PART] = this.valueArray[INTEGRAL_NUMBER_PART] + y.valueArray[INTEGRAL_NUMBER_PART];
		resultArray[NUMERATOR_PART] = this.valueArray[NUMERATOR_PART] * y.valueArray[DENOMINATOR_PART] + this.valueArray[DENOMINATOR_PART] * y.valueArray[NUMERATOR_PART];
		resultArray[DENOMINATOR_PART] = this.valueArray[DENOMINATOR_PART] * y.valueArray[DENOMINATOR_PART];

		/**
		 * 这里要求分子和分母最大公因数,然后除掉
		 */

		return new Number(resultArray);
	}

	/**
	 * 减法运算
	 * 
	 * @param y
	 * @return this.value - y.value
	 */
	public Number subtract(Number y) {
		int[] resultArray = new int[3];
		
		/**
		 *  x1'y1/z1 - x2'y2/z2 
		 *  = (x1*z1+y1)/(z1) - (x2*z2+y2)/(z2)
		 *  = ((x1-x2)*z1*z2 + y1*z2 - y2*z1 )/(z1*z2)
		 *  ...判断分子大小
		 *  ...求最大公因数,约分
		 *  ...转换成x'y/z
		 */
		
		return new Number(resultArray);
	}

	/**
	 * 乘法运算
	 * 
	 * @param y
	 * @return this.value * y.value
	 */
	public Number multiply(Number y) {

		return y;

	}

	/**
	 * 除法运算
	 * 
	 * @param y
	 * @return this.value / y.value
	 */
	public Number divide(Number y) {

		return y;

	}

}
