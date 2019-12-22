package ExerciseTest;

import java.util.*;
import java.io.*;

public class ExerciseSheet {
	private static final short COLUMN_NUMBER = 5;
	
	public static void main(String[]args) throws Throwable {
		//TestGenerateOperation();//测试方法：阶段一自动生成算式，输出到文件，并再次读入
		TestGenerateOperationBase();//测试方法：阶段二利用算式基生成算式,输出到文件，并再次读入
		//TestJugement();//测试方法,检测练习文件生成批改文件
	}
	
	public void formattedDisplay (Exercise ex, int columns){
	//调用Exercise对象将其算式按规定格式显示输出
		int col = columns;
		int num = 0;
		while(ex.hasNext()){
			col--;
			num++;
			System.out.printf("%-5s%-15s",num+".",ex.next());
			if(col==0){
				System.out.println("");
				col = columns;
			}
		}
		System.out.println("");
	}

	public void formattedDisplay (Exercise ex){
		formattedDisplay(ex,COLUMN_NUMBER);
	}
	
	static public void TestGenerateOperation() throws Throwable {
		//测试方法，利用阶段一的方式生成算式并保存到文件中
		ExerciseSheet sheet = new ExerciseSheet();
		Exercise exercise = new Exercise();
		System.out.println("随机生成加法算式");
		exercise.writeCSVAdditionEercise(50);
		sheet.formattedDisplay(exercise, 5);
		System.out.println("随机生成减法算式");
		exercise.writeCSVSubstractEercise(50);
		sheet.formattedDisplay(exercise, 5);
		System.out.println("随机生成混合算式");
		exercise.writeCSVBinaryEercise(50);
		sheet.formattedDisplay(exercise, 5);
		
		File aFile;
		System.out.println("读入加法算式");
		aFile = new File("d:/加减法练习/Addition_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
		
		System.out.println("读入减法算式");
		aFile = new File("d:/加减法练习/Substract_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
		
		System.out.println("读入混合算式");
		aFile = new File("d:/加减法练习/MixOperation_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
	}
	
	static public void TestGenerateOperationBase() throws Throwable {
		//测试方法二，调用算式基生成算式，并将习题保存到文件中
		ExerciseSheet sheet = new ExerciseSheet();
		Exercise exercise = new Exercise();
		OperationBase Base = new OperationBase();
		
		System.out.println("随机生成加法算式");
		exercise = Base.generateAdditionExercise(50);
		exercise.writeCSVExercise();
		sheet.formattedDisplay(exercise, 5);
		
		System.out.println("随机生成减法算式");
		exercise = Base.generateSubstractExercise(50);
		exercise.writeCSVExercise();
		sheet.formattedDisplay(exercise, 5);
		
		System.out.println("随机生成混合算式");
		exercise = Base.generateBinaryExercise(50);
		exercise.writeCSVExercise();
		sheet.formattedDisplay(exercise, 5);
		
		File aFile;
		System.out.println("Base从文件读入加法算式");
		aFile = new File("d:/加减法练习/Addition_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
		
		System.out.println("Base从文件读入减法算式");
		aFile = new File("d:/加减法练习/Substract_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
		
		System.out.println("Base从文件读入混合算式");
		aFile = new File("d:/加减法练习/MixOperation_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
	}
	
	static void TestJugement() throws Exception {
		//测试方法三，测试批改习题功能
		Exercise exercise = new Exercise();
		Judgement ju = new Judgement();
		
		System.out.println("读入加法习题和练习，批改习题生成结果文件");
		File aFile = new File("d:/加减法练习/Addition_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		ju.Practice(exercise);
		
		System.out.println("读入减法习题和练习，批改习题生成结果文件");
		aFile = new File("d:/加减法练习/Substract_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		ju.Practice(exercise);
		
		System.out.println("读入混合习题和练习，批改习题生成结果文件");
		aFile = new File("d:/加减法练习/MixOperation_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		ju.Practice(exercise);
	}
}