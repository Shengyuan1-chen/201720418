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
		//������insertͨ�����������������ֱ�Ӳ�����ʽ
		left_operand = Integer.valueOf(left);
		right_operand = Integer.valueOf(right);
		value = calculate(left_operand, right_operand);
	}
	public void insert(int left, int right){
		//������int������insert
		left_operand = left;
		right_operand = right;
		value = calculate(left_operand, right_operand);
	}
	//����public String toString
	public String toString(){
		String s;
		String Charop = String.valueOf(operator);
		s = left_operand+Charop+right_operand+"=";
		return s;
	}
	
	
	// getXX ������Ҫ���ʵ����ֺͷ���
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
		//�����anOperator��Ϊ�������ɵ���ʽ���������
		int left,right,result;
		Random random = new Random();
		left = random.nextInt(upper+1);
		do{
		//Լ������λ�����������Լ���������ܸ�ֵ����Ա����
			right = random.nextInt(upper+1);
			result = calculate(left,right);
		}while(!(checkingCalculation(result)));
		left_operand = left;
		right_operand = right;
		operator = anOperator;
		value = result;
	}
	abstract boolean checkingCalculation(int anInteger);
	//����Ƿ������ʽ��׼
	abstract int calculate(int left, int right);
	//������ʽ���

	public boolean equals(BinaryOperation anOperation){
	//�������ö�������ַ�������һ����������ַ������Ƚϣ��𵽼���Ƿ��ظ�������
		return operator==anOperation.getOperator()&&
				right_operand==anOperation.getright_operand()&&
				left_operand==anOperation.getleft_operand();
	}
}