package entity;

public enum Type {
	// 枚举实例
	NaturalNumber(0),TrueFraction(1);
	
	// 自然数的flag为0,真分数的flag为1
	int flag;
	
	// 构造器,不使用public修饰
	Type(int flag) {
		this.flag = flag;
	}
}
