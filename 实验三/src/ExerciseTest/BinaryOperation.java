package ExerciseTest;

import java.io.Serializable;
import java.util.Random;

public abstract class BinaryOperation implements Serializable{
	static final int upper = 100;
	static final int lower = 0;
	private int left_operand = 0,right_operand = 0;
	private char operator = '+';
	private int value = 0;
	
	public void insert(String left, String right){
		//新增：insert通过调用这个方法可以直接插入算式
		left_operand = Integer.valueOf(left);
		right_operand = Integer.valueOf(right);
		value = calculate(left_operand, right_operand);
	}
	public void insert(int left, int right){
		//新增：int参数的insert
		left_operand = left;
		right_operand = right;
		value = calculate(left_operand, right_operand);
	}
	//重载public String toString
	public String toString(){
		String s;
		String Charop = String.valueOf(operator);
		s = left_operand+Charop+right_operand+"=";
		return s;
	}
	
	
	// getXX 返回需要访问的数字和符号
	public char getOperator(){
		return operator;
	}
	public int getleft_operand(){
		return left_operand;
	}
	public int getright_operand(){
		return right_operand;
	}
	public int getvalue() {
		return value;
	}
	
	protected void generateBinaryOperation(char anOperator){
		//传入的anOperator即为该类生成的算式的运算符号
		int left,right,result;
		Random random = new Random();
		left = random.nextInt(upper+1);
		do{
		//约束，两位整数必须符合约束条件才能赋值给成员函数
			right = random.nextInt(upper+1);
			result = calculate(left,right);
		}while(!(checkingCalculation(result)));
		left_operand = left;
		right_operand = right;
		operator = anOperator;
		value = result;
	}
	abstract boolean checkingCalculation(int anInteger);
	//检查是否符合算式标准
	abstract int calculate(int left, int right);
	//计算算式结果

	public boolean equals(BinaryOperation anOperation){
	//方法将该对象的数字符号与另一个对象的数字符号做比较，起到检查是否重复的作用
		return operator==anOperation.getOperator()&&
				right_operand==anOperation.getright_operand()&&
				left_operand==anOperation.getleft_operand();
	}
}