package ExerciseTest;

public class AdditionOperation extends BinaryOperation{
	AdditionOperation(){
		generateBinaryOperation('+');
	}
	public boolean checkingCalculation(int anInteger){
	//������д��ʹ�������ʽ���Ϲ涨�ļӷ���ʽ��׼
		if(anInteger<=upper)
			return true;
		else
			return false;
	}
	int calculate(int left,int right){
		return left + right;	
	}
}