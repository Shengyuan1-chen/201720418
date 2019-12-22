package ExerciseTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class OperationBase{//算式基类
	static final int UPPER = 100;
	
	private BinaryOperation[][]AdditionBase;//加法算式基
	private BinaryOperation[][]SubstractBase;//减法算式基
	
	public void displayBase(BinaryOperation[][] base) {
	//测试方法，显示传进来的算式基的所有算式
		for(BinaryOperation[]ba:base) {
			for(BinaryOperation aa:ba) {
				if(aa!=null)
					System.out.print(aa.toString()+'\t');
			}
			System.out.println();
		}
	}
	
	public Exercise generationExercise(int count, BinaryOperation[][] Base) {
	//传入算式基，该算式基可以是加法算式基也可以是减法算式基，根据这个算式基随机生成算式并返回习题对象
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
	//传入两个算式基，既加法和减法算式基，根据这两个算式基随机生成混合算式并返回习题对象
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
	//以下三个方法被直接调用，返回一个习题，并生成文件
	public Exercise generateAdditionExercise(int count) {
		//生成加法习题
		Exercise exercise;
		exercise = generationExercise(count,AdditionBase);
		exercise.FileName = "Addition_exercise_"+count+"_";
		return exercise;
	}
	
	public Exercise generateSubstractExercise(int count) {
		//生成减法习题
		Exercise exercise;
		exercise = generationExercise(count,SubstractBase);
		exercise.FileName = "Substract_exercise_"+count+"_";
		return exercise;
	}
	
	public Exercise generateBinaryExercise(int count) {
		//生成加法习题
		Exercise exercise;
		exercise = generationExercise(count,AdditionBase,SubstractBase);
		exercise.FileName = "MixOperation_exercise_"+count+"_";
		return exercise;
	}
	
	public OperationBase() throws Throwable {
		//构造函数，算式基对象实例化就判断算式基文件是否存在，若存在则直接读入，若不存在就生成
		File aFile = new File("d:/加减法练习/AdditionOperationBase.csv");
		File bFile = new File("d:/加减法练习/SubstractOperationBase.csv");
		if(aFile.exists()&&bFile.exists()) {
			readOperationBase(aFile, bFile);
			System.out.println("已从文件读入算式基");
		}
		else {
			writeOperationBase();
			System.out.println("已将算式基写入文件");
		}
	}
	public void readOperationBase(File aFile,File bFile) throws IOException {
		//读入算式基
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
		//生成算式基文件
		AdditionBase = new BinaryOperation[UPPER+1][UPPER+1];
		SubstractBase = new BinaryOperation[UPPER+1][UPPER+1];
		BinaryOperation op;
		File aFile = new File("d:/加减法练习/AdditionOperationBase.csv");
		File bFile = new File("d:/加减法练习/SubstractOperationBase.csv");
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