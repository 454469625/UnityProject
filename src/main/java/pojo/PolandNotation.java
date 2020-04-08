package pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 当前的问题在于中缀怎么转后缀
 * 怎么使用number类
 */
//后缀表达式的计算
public class PolandNotation {
    public static void main(String[] args) throws Exception{

        //String expression = "1+((2+9)÷4)-5";
        String expression = "1'2/3 ÷ 2/7 + 8 × 7/3 ";
        String s = infixToSuffix(expression);
        System.out.println("后缀表达式： " + s);
        s = suffixToArithmetic(s);
        System.out.println(s);
        //！！！！计算后的÷还是计算机的除，即11÷4 = 2！！！！
        //System.out.println("expression = "+calculate(suffixExpList));

    }

    public static String infixToSuffix(String exp) {
        //将字符串按照空格分割存入exps数组
        String exps[] = exp.split("\\s");
        Stack<Character> s = new Stack<Character>();                // 创建操作符堆栈
        String suffix = "";                                         // 要输出的后缀表达式字符串
        int length = exps.length;                                  // 输入的中缀表达式数组的长度
        for (int i = 0; i < length; i++) {
            char temp;
            char ch = exps[i].charAt(0);                              // 获取该中缀表达式的每一个字符并进行判断
            switch (ch) {
                case '(':
                    s.push(ch);
                    break;
                case '+':                              // 碰到'+' '-'，将栈中的所有运算符全部弹出去，直至碰到左括号为止，输出到队列中去
                case '-':
                    suffix += " ";
                    while (s.size() != 0) {
                        temp = s.pop();
                        if (temp == '(') {
                            s.push('(');
                            break;
                        }
                        suffix += temp;
                        suffix += " ";
                    }
                    s.push(ch);
                    break;
                case '×':                               // 如果是乘号或者除号，则弹出所有序列，直到碰到加好、减号、左括号为止，最后将该操作符压入堆栈
                case '÷':
                    suffix += " ";
                    while (s.size() != 0) {
                        temp = s.pop();
                        if (temp == '+' || temp == '-' || temp == '(') {
                            s.push(temp);
                            break;
                        } else {
                            suffix += temp;
                            suffix += " ";
                        }
                    }
                    s.push(ch);
                    break;
                case ')':
                    while (!s.isEmpty()) {
                        temp = s.pop();
                        if (temp == '(') {
                            break;
                        } else {
                            suffix += " ";
                            suffix += temp;
                        }
                    }
                    break;
                default:
                    suffix = suffix.concat(exps[i]);
                    break;
            }
        }
        while (s.size() != 0) {                            // 如果堆栈不为空，则把剩余运算符一次弹出，送至输出序列
            suffix += " ";
            suffix += s.pop();
        }
        return suffix;
    }

    /*
        int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
    if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("x")) {
                    res = num1 * num2;
                } else if (item.equals("÷")){
                    res = num1 / num2;
                }else {
                    throw new RuntimeException("运算符有误");
                }

     */

    public static String suffixToArithmetic(String exp) throws Exception{
        String[] strings = exp.split(" ");                  //按空格分解字符串
        Stack<String> stack = new Stack<String>();          //操作数栈
        for (int i = 0; i < strings.length; i++) {
            if(strings[i].equals("+")||strings[i].equals("-")||strings[i].equals("×")||strings[i].equals("÷")){
                String y=stack.pop();                        //读取到运算符，提取栈顶的两个操作数，先出的操作数为运算符后的数
                String x=stack.pop();
                //此处使用的参数全为字符串，是否应该考虑将Number中的加减乘除换成字符串
                if (strings[i].equals("+")){
                    stack.push(Number.plus(x,y));
                } else if (strings[i].equals("-")) {
                    //前参减后参，而我们需要做的是让后pop减前pop
                    stack.push(Number.plus(x,y));
                }else if (strings[i].equals("×")) {
                    //前参减后参，而我们需要做的是让后pop减前pop
                    stack.push(Number.multiply(x,y));
                }else if (strings[i].equals("÷")) {
                    //前参减后参，而我们需要做的是让后pop减前pop
                    stack.push(Number.divide(x,y));
                }
            }else{
                stack.push(strings[i]);
            }
        }
        return stack.pop();
    }















    /**
     * 将中缀表达式转化为后缀表达式
     * @param ls
     * @return
     */
    public static List<String> parseSuffixExpList(List<String> ls) {
        //定义两个栈
        Stack<String> s1 = new Stack<>();//符号栈
        List<String> s2 = new ArrayList<>();//存储中间结果的list s2
        //遍历ls
        for (String item :
                ls) {
            //如果是一个数，则加入s2
            if (item.matches("\\d+")){
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                //如果是右括号，则依次弹出s1栈顶的运算符，并压入s2，指导遇到左括号
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();//将(弹出s1栈，消除小括号
            }else{
                //当item优先级小于等于s1栈顶运算符,将s1栈顶运算符弹出加入s2中，再次与s1中的新栈顶运算符相比较
                while (s1.size() != 0 && Opeartion.getVAalue(s1.peek()) >= Opeartion.getVAalue(item)) {
                    s2.add(s1.pop());
                }
                //还需要将item压入栈
                s1.push(item);
            }
        }
        //将s1中剩余的运算符依次弹出加入s2
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        return s2;
    }

    //中缀表达式字符串转list，(list方便操作)
    public static List<String> toInfixExpList(String s){
        //list用于存放中缀表达式的内容
        List<String> ls = new ArrayList<>();
        int i = 0;//用于遍历中缀表达式
        String str;//对多位数进行拼接
        char c;//没遍历一个字符，就放入到c
        do {
            //如果c是一个非数字，加入到ls
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                ls.add("" + c);
                i++;
            } else {
                str = "";
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {
                    str += c;
                    i++;
                }
                ls.add(str);

            }
        } while (i < s.length());
        return ls;
    }

    //暂时没什么用的一个方法
    public static List<String> getListString(String suffixExpression){
        //将suffExp分割
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList();
        for (String ele :
                split) {
            list.add(ele);
        }
        return list;
    }

    /**
     * 用于计算出后缀表达式的解
     * @param ls
     * @return
     */
    public static int calculate(List<String> ls) {
        //当走到这一步的时候,我们已经取得了后缀表达式
        Stack<String> stack = new Stack<>();
        for (String item : ls) {
            //使用正则表达式取出数
            //将操作数入栈
            if (item.matches("\\d+")){//匹配多位数
                //入栈
                stack.push(item);
            }else{
                //如果遇到了操作符
                //pop出两个数，并且运算,这里pop出的应该是number类型才对
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("x")) {
                    res = num1 * num2;
                } else if (item.equals("÷")){
                    res = num1 / num2;
                }else {
                    throw new RuntimeException("运算符有误");
                }
                stack.push("" + res);
            }
        }
        return Integer.parseInt(stack.pop());
    }
}

//opeartion 返回一个运算符的优先级
class Opeartion {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    public static int getVAalue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "x":
                result =MUL;
                break;
            case "÷":
                result = DIV;
                break;
            default:
                System.out.println("error");
                break;
        }
        return result;
    }
}