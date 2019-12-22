package ExerciseTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class OperationBase{//��ʽ����
	static final int UPPER = 100;
	
	private BinaryOperation[][]AdditionBase;//�ӷ���ʽ��
	private BinaryOperation[][]SubstractBase;//������ʽ��
	
	public void displayBase(BinaryOperation[][] base) {
	//���Է�������ʾ����������ʽ����������ʽ
		for(BinaryOperation[]ba:base) {
			for(BinaryOperation aa:ba) {
				if(aa!=null)
					System.out.print(aa.toString()+'\t');
			}
			System.out.println();
		}
	}
	
	public Exercise generationExercise(int count, BinaryOperation[][] Base) {
	//������ʽ��������ʽ�������Ǽӷ���ʽ��Ҳ�����Ǽ�����ʽ�������������ʽ�����������ʽ������ϰ�����
		Exercise anExercise = new Exercise();
		BinaryOperation anOperation;
		Random random = new Random();
		int i, j;
		while(count>0) {
			do {
				i = random.nextInt(UPPER+1);
				j = random.nextInt(UPPER+1);
				anOperation = Base[i][j];
			}while(anOperation==null||anExercise.contains(anOperation));
			anExercise.Add(anOperation);
			count--;
		}
		return anExercise;
	}
	
	public Exercise generationExercise(int count,BinaryOperation[][]AddBase,BinaryOperation[][]SubBase) {
	//����������ʽ�����ȼӷ��ͼ�����ʽ����������������ʽ��������ɻ����ʽ������ϰ�����
		Exercise anExercise = new Exercise();
		BinaryOperation anOperation;
		Random random = new Random();
		int i, j, ran;
		while(count>0) {
			ran = random.nextInt(2);
			switch(ran) {
			case 0:
				do {
					i = random.nextInt(UPPER+1);
					j = random.nextInt(UPPER+1);
					anOperation = AddBase[i][j];
				}while(anOperation==null||anExercise.contains(anOperation));
				anExercise.Add(anOperation);
				count--;
				break;
			case 1:
				do {
					i = random.nextInt(UPPER+1);
					j = random.nextInt(UPPER+1);
					anOperation = SubBase[i][j];
				}while(anOperation==null||anExercise.contains(anOperation));
				anExercise.Add(anOperation);
				count--;
				break;
			}
		}
		return anExercise;
	}
	//��������������ֱ�ӵ��ã�����һ��ϰ�⣬�������ļ�
	public Exercise generateAdditionExercise(int count) {
		//���ɼӷ�ϰ��
		Exercise exercise;
		exercise = generationExercise(count,AdditionBase);
		exercise.FileName = "Addition_exercise_"+count+"_";
		return exercise;
	}
	
	public Exercise generateSubstractExercise(int count) {
		//���ɼ���ϰ��
		Exercise exercise;
		exercise = generationExercise(count,SubstractBase);
		exercise.FileName = "Substract_exercise_"+count+"_";
		return exercise;
	}
	
	public Exercise generateBinaryExercise(int count) {
		//���ɼӷ�ϰ��
		Exercise exercise;
		exercise = generationExercise(count,AdditionBase,SubstractBase);
		exercise.FileName = "MixOperation_exercise_"+count+"_";
		return exercise;
	}
	
	public OperationBase() throws Throwable {
		//���캯������ʽ������ʵ�������ж���ʽ���ļ��Ƿ���ڣ���������ֱ�Ӷ��룬�������ھ�����
		File aFile = new File("d:/�Ӽ�����ϰ/AdditionOperationBase.csv");
		File bFile = new File("d:/�Ӽ�����ϰ/SubstractOperationBase.csv");
		if(aFile.exists()&&bFile.exists()) {
			readOperationBase(aFile, bFile);
			System.out.println("�Ѵ��ļ�������ʽ��");
		}
		else {
			writeOperationBase();
			System.out.println("�ѽ���ʽ��д���ļ�");
		}
	}
	public void readOperationBase(File aFile,File bFile) throws IOException {
		//������ʽ��
		FileInputStream fisa = new FileInputStream(aFile);
		FileInputStream fisb = new FileInputStream(bFile);
		AdditionBase = new BinaryOperation[UPPER+1][UPPER+1];
		SubstractBase = new BinaryOperation[UPPER+1][UPPER+1];
		BinaryOperation op;
		String eqString = "";
		int b ;
		for(int i = 0;i<=UPPER;i++) {
			for(int j = 0;j<=UPPER-i;j++) {
				while(true) {
					b = fisa.read();
					if(b=='\r'||b=='\n')
						continue;
					if(b==',')
						break;
					else
						eqString+=(char)b;
				}
				op = Exercise.AddSubOperation(eqString);
				AdditionBase[i][j] = op;
				eqString = "";
			}
		}
		
		for(int i = 0;i<=UPPER;i++) {
			for(int j = 0;j<=i;j++) {
				while(true) {
					b = fisb.read();
					if(b=='\r'||b=='\n')
						continue;
					if(b==',')
						break;
					else
						eqString+=(char)b;
				}
				op = Exercise.AddSubOperation(eqString);
				SubstractBase[i][j] = op;
				eqString = "";
			}
		}
		fisa.close();
		fisb.close();
	}
	public void writeOperationBase() throws Throwable  {
		//������ʽ���ļ�
		AdditionBase = new BinaryOperation[UPPER+1][UPPER+1];
		SubstractBase = new BinaryOperation[UPPER+1][UPPER+1];
		BinaryOperation op;
		File aFile = new File("d:/�Ӽ�����ϰ/AdditionOperationBase.csv");
		File bFile = new File("d:/�Ӽ�����ϰ/SubstractOperationBase.csv");
		if(!aFile.exists())
				aFile.createNewFile();
		FileWriter FW = new FileWriter(aFile,false);
		for(int i = 0;i<=UPPER;i++){
			for(int j = 0;j<=UPPER-i;j++) {
				op = new AdditionOperation();
				op.insert(i, j);
				AdditionBase[i][j] = op;
				FW.write(op.toString());
				FW.write(",");
			}
			FW.write("\r\n");
		}
		FW.close();
		
		op = new SubstractOperation();
		if(!bFile.exists())
				bFile.createNewFile();
		FW = new FileWriter(bFile,false);
		for(int i = 0;i<=UPPER;i++){
			for(int j = 0;j<=i;j++) {
				op = new SubstractOperation();
				op.insert(i, j);
				SubstractBase[i][j] = op;
				FW.write(op.toString());
				FW.write(",");
			}
			FW.write("\r\n");
		}
		FW.close();
		displayBase(AdditionBase);
		displayBase(SubstractBase);
	}
}