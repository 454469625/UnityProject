package pojo;

import java.util.Random;

public class Expression {

    /**
     * 打印表达式的函数
     * @param num	表达式要包含的操作数个数
     * @return
     */
    public static String printExpression(int num) throws Exception {
        if (num > 4) {
            throw new Exception("操作数大于4");
        }
        String s = "";
        Number[] number = new Number[4];
        char[] operate = new char[]{'+','-','×','÷'};
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            number[i] = new Number(r.nextInt(50),r.nextInt(10));
            if (i != num - 1) {
                s = s.concat(number[i] + " " + operate[r.nextInt(4)] + " ");
            } else {
                s = s.concat(number[i] + " =");
            }
        }
        return s;
    }
}
