package entity;

import lombok.Data;

@Data
public class Number {
	private Type type;
	private String value;
	// [整数部分,分子部分,分母部分]
	private Integer[] valueArray = new Integer[3];
	
	public Number(Type type, String value) {
		this.type = type;
		this.value = value;
		switch (this.type) {
			case NaturalNumber:
				
				break;
			case TrueFraction:
				
				break;
		}
	}

	/**
	 * 加法运算
	 * @param y
	 * @return this.value + y.value
	 */
	public Number plus(Number y) {
		Type resultType;
		// 自然数的flag为0,真分数的flag为1
		if( (this.type.flag + y.type.flag) == 0) {
			resultType = Type.NaturalNumber;
		}else {
			resultType = Type.TrueFraction;
		}
		
		return new Number(resultType,"asdasds");
	}

	/**
	 * 	减法运算
	 * @param y
	 * @return this.value - y.value
	 */
	public Number subtract(Number y) {
		return y;

	}

	/**
	 *  乘法运算
	 * @param y
	 * @return this.value * y.value
	 */
	public Number multiply(Number y) {
		return y;

	}
	
	/**
	 * 	除法运算
	 * @param y
	 * @return this.value / y.value
	 */
	public Number divide(Number y) {
		return y;

	}

}
