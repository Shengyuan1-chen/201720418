package ExerciseTest;

import java.util.*;
import java.io.*;

public class ExerciseSheet {
	private static final short COLUMN_NUMBER = 5;
	
	public static void main(String[]args) throws Throwable {
		//TestGenerateOperation();//���Է������׶�һ�Զ�������ʽ��������ļ������ٴζ���
		TestGenerateOperationBase();//���Է������׶ζ�������ʽ��������ʽ,������ļ������ٴζ���
		//TestJugement();//���Է���,�����ϰ�ļ����������ļ�
	}
	
	public void formattedDisplay (Exercise ex, int columns){
	//����Exercise��������ʽ���涨��ʽ��ʾ���
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
		//���Է��������ý׶�һ�ķ�ʽ������ʽ�����浽�ļ���
		ExerciseSheet sheet = new ExerciseSheet();
		Exercise exercise = new Exercise();
		System.out.println("������ɼӷ���ʽ");
		exercise.writeCSVAdditionEercise(50);
		sheet.formattedDisplay(exercise, 5);
		System.out.println("������ɼ�����ʽ");
		exercise.writeCSVSubstractEercise(50);
		sheet.formattedDisplay(exercise, 5);
		System.out.println("������ɻ����ʽ");
		exercise.writeCSVBinaryEercise(50);
		sheet.formattedDisplay(exercise, 5);
		
		File aFile;
		System.out.println("����ӷ���ʽ");
		aFile = new File("d:/�Ӽ�����ϰ/Addition_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
		
		System.out.println("���������ʽ");
		aFile = new File("d:/�Ӽ�����ϰ/Substract_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
		
		System.out.println("��������ʽ");
		aFile = new File("d:/�Ӽ�����ϰ/MixOperation_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
	}
	
	static public void TestGenerateOperationBase() throws Throwable {
		//���Է�������������ʽ��������ʽ������ϰ�Ᵽ�浽�ļ���
		ExerciseSheet sheet = new ExerciseSheet();
		Exercise exercise = new Exercise();
		OperationBase Base = new OperationBase();
		
		System.out.println("������ɼӷ���ʽ");
		exercise = Base.generateAdditionExercise(50);
		exercise.writeCSVExercise();
		sheet.formattedDisplay(exercise, 5);
		
		System.out.println("������ɼ�����ʽ");
		exercise = Base.generateSubstractExercise(50);
		exercise.writeCSVExercise();
		sheet.formattedDisplay(exercise, 5);
		
		System.out.println("������ɻ����ʽ");
		exercise = Base.generateBinaryExercise(50);
		exercise.writeCSVExercise();
		sheet.formattedDisplay(exercise, 5);
		
		File aFile;
		System.out.println("Base���ļ�����ӷ���ʽ");
		aFile = new File("d:/�Ӽ�����ϰ/Addition_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
		
		System.out.println("Base���ļ����������ʽ");
		aFile = new File("d:/�Ӽ�����ϰ/Substract_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
		
		System.out.println("Base���ļ���������ʽ");
		aFile = new File("d:/�Ӽ�����ϰ/MixOperation_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		sheet.formattedDisplay(exercise);
	}
	
	static void TestJugement() throws Exception {
		//���Է���������������ϰ�⹦��
		Exercise exercise = new Exercise();
		Judgement ju = new Judgement();
		
		System.out.println("����ӷ�ϰ�����ϰ������ϰ�����ɽ���ļ�");
		File aFile = new File("d:/�Ӽ�����ϰ/Addition_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		ju.Practice(exercise);
		
		System.out.println("�������ϰ�����ϰ������ϰ�����ɽ���ļ�");
		aFile = new File("d:/�Ӽ�����ϰ/Substract_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		ju.Practice(exercise);
		
		System.out.println("������ϰ�����ϰ������ϰ�����ɽ���ļ�");
		aFile = new File("d:/�Ӽ�����ϰ/MixOperation_exercise_50_.csv");
		exercise.readCSVExercise(aFile);
		ju.Practice(exercise);
	}
}