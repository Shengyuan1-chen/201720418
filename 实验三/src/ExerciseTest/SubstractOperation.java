package ExerciseTest;

public class SubstractOperation extends BinaryOperation{
	SubstractOperation(){
		generateBinaryOperation('-');
	}
	public boolean checkingCalculation(int anInteger){
	//方法重写，使该类的算式符合规定的减法算式标准
		if(anInteger>=lower)
			return true;
		else
			return false;
	}
	int calculate(int left,int right){
		return left - right;
	}
}