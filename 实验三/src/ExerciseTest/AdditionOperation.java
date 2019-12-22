package ExerciseTest;

public class AdditionOperation extends BinaryOperation{
	AdditionOperation(){
		generateBinaryOperation('+');
	}
	public boolean checkingCalculation(int anInteger){
	//方法重写，使该类的算式符合规定的加法算式标准
		if(anInteger<=upper)
			return true;
		else
			return false;
	}
	int calculate(int left,int right){
		return left + right;	
	}
}