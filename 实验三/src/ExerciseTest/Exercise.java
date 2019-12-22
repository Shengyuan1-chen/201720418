package ExerciseTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Exercise implements Serializable{
	private static final long serialVersionUID = 6829563053067592708L;
	private int OperationCount = 0;//新增：记录这个Exercise的算式总数
	protected String FileName ;//新增：练习名称
	public String Addr = "D:/加减法练习/";//新增：练习地址
	private int current = 0;//记录当前访问位置
	
	class Answers implements Serializable {	//答案内部类
		private static final long serialVersionUID = -232662116583465565L;
		String content;		//答案内容
		boolean correct;	//正确性
		public Answers() {content = ""; correct = false;}
		public Answers(String ans, boolean cr) {
			content = ans; correct = cr;
		}
	}
	
	private ArrayList<BinaryOperation> operationList = new ArrayList<BinaryOperation>();
	private List <Answers> answers = new ArrayList<>();		//用户填写的所有题目的答案列表
	
	private ExerciseType currentType;		//当前题目类型，为串行化保存用
	public ExerciseType getCurrentType() {return currentType;}
	
	public String getExerciseType() {
		String s = new String();
		switch(currentType) {
		case ADDandSUB:
			s = "加减混合";
			break;
		case ADD:
			s = "仅加法";
			break;
		case SUB:
			s = "仅减法";
			break;
		}	
		return s;
	}
	
	private void setCurrentType(ExerciseType type) { this.currentType = type;}
	public void setAnswer(int index, String ans) {		
		//设置答案内容并判断正确性
		BinaryOperation op;
		op = operationList.get(index);		//获取算式
		String result = String.valueOf(op.getvalue());		//获取正确结果
		String tans = ans.trim();//获取用户输入的答案
		answers.set(index, new Answers(tans, result.equals(tans)));  
		//设置答案内容和正确性，index位置上设置为对应的answer对象，对象里面保存用户答案和是否正确
	}
	public String getAnswer(int index) {	//获取答案
		return answers.get(index).content;
	}
	public void clearAnswers() {		//清空答案（全设为空，错误）
		for (int i = 0; i < answers.size(); i++)
			answers.set(i, new Answers("", false));
	}
	
	public int Correct() {		//统计正确题数
		int correctAmount = 0;
		for (int i = 0; i < answers.size(); i++) {
			if (getJudgement(i))
				correctAmount++;
		}
		return correctAmount;
	}
	
	public boolean getJudgement(int index) {	
		//获取判题结果
		return answers.get(index).correct;
	}
	
	public void saveObject(String filename, Exercise exercise) throws ExerciseIOException{		
		//串行化存储对象
		FileOutputStream f = null;
		try {
			 f = new FileOutputStream(filename);
			 //可以传入一个字符串的文件地址,地址不为空时,自动为字符串创建File对象
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		ObjectOutputStream s = null;
		try {
			 s = new ObjectOutputStream(f);
			 //定义ObjectOutputStream对象需要用一个FileOutputStream来构造文件字节输出流对象
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		try {
			s.writeObject(exercise);//向文件中输出算式
			s.flush();
			//刷新此输出流并强制写出所有缓冲的输出字节。
			//flush 的常规协定是：如果此输出流的实现已经缓冲了以前写入的任何字节，则调用此方法指示应将这些字节立即写入它们预期的目标。
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		try {
			s.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	public static Exercise loadObject(String filename) throws ExerciseIOException{	
		//串行化载入对象
		FileInputStream in = null;
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		ObjectInputStream s = null;
		try {
			s = new ObjectInputStream(in);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Exercise exercise = null;
		try {
			exercise = (Exercise) s.readObject();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			s.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return exercise;
	}
	public void generateWithFormerType(int operationCount) {	
		//采用原题型生成新题目
		switch(currentType) {
		case ADDandSUB:
			this.generateBinaryExercise(operationCount);
			break;
		case ADD:
			this.generateAdditionExercise(operationCount);
			break;
		case SUB:
			this.generateBinaryExercise(operationCount);
			break;
		}	
	}
	public enum ExerciseType {
		ADD , SUB, ADDandSUB, 
	}
	//重构结束
	
	
	//创建一个关于BinaryOperation（算式）的动态数组
	
	public Exercise() {
		File aFile = new File(Addr);
		if(!aFile.exists())
				aFile.mkdir();
	}
	
	public boolean hasNext(){
		return current<=operationList.size()-1;
	}//若数组还有元素，则返回是，否则返回否

	public BinaryOperation next(){
		return operationList.get(current++);
	}//若有元素，返回当前元素，当前访问位置移动到下一个位置

	public boolean contains(BinaryOperation anOperation){
		boolean flag = true;
		int size = operationList.size();
		if(size==0){//如果数组里没有算式，返回false，允许加入数组
			return false;
		}
		for(int i = 0;i<size;i++){//将数组里已有的算式和新生成的算式做比较，检查是否存在重复
			if(!operationList.get(i).equals(anOperation)){
				flag = false;
			}
		}
		return flag;
	}
	
	private BinaryOperation generateOperation(){
		//随机生成一个加法或者减法算式并返回
		Random random = new Random();
		int opValue = random.nextInt(2);
		if(opValue==1){
			return new AdditionOperation();
		}
		return new SubstractOperation();
	}

	public void generateBinaryExercise(int operationCount){
	//方法调用generateOperation，将随机生成的加法或者减法算式加入到动态数组中
		setCurrentType(ExerciseType.ADDandSUB ); //设置题目类型
		operationList.clear();
		answers.clear();
		current = 0;
		OperationCount = operationCount;
		FileName="MixOperation_exercise_"+operationCount+"_";
			BinaryOperation anOperation;
			while(operationCount>0){
				do{
					anOperation = generateOperation();
					}while(contains(anOperation));
				operationList.add(anOperation);
				answers.add(new Answers());
				//将anOperation 加入到Exercise中
				operationCount--;
			}
	}

	public void generateAdditionExercise(int operationCount){
		//随机生成加法算式并加入数组
		setCurrentType(ExerciseType.ADD ); //设置题目类型
		
		answers.clear();
		operationList.clear();
		current = 0;
		OperationCount = operationCount;
		FileName= "Addition_exercise_"+operationCount+"_";
		BinaryOperation anOperation1;
			while(operationCount>0){
				do{anOperation1 = new AdditionOperation();}while(contains(anOperation1));
				operationList.add(anOperation1);//将anOperation 加入到Exercise中
				answers.add(new Answers());
				operationCount--;
			}
	}
	
	public void generateSubstractExercise(int operationCount){
	//随机生成减法算式并加入数组
		setCurrentType(ExerciseType.SUB );
		operationList.clear();
		answers.clear();
		
		operationList.clear();
		current = 0;
		OperationCount = operationCount; 
		FileName= "Substract_exercise_"+operationCount+"_";
		BinaryOperation anOperation1;
			while(operationCount>0){
				do{anOperation1 = new SubstractOperation();}while(contains(anOperation1));
				operationList.add(anOperation1);
				answers.add(new Answers());
				//将anOperation 加入到Exercise中
				operationCount--;
		}
	}

	public void writeCSVExercise()//生成文件
	{
		int count = OperationCount;//算式数量
		FileName = Addr + FileName+".csv";//生成习题文件名称
		String PracticeName = FileName.replaceAll("exercise", "practice");
		System.out.println(FileName);
		System.out.println(PracticeName);
		try {
			File file =new File(FileName);
			 if(!file.exists()){
				   file.createNewFile();
			}
			 File pfile = new File(PracticeName);
			 if(!pfile.exists()) {
				 pfile.createNewFile();
			 }
			FileWriter fileWritter = new FileWriter(file,false);
			for(int i=0;i<count;i++)
			{
				fileWritter.write(operationList.get(i).toString());//把算式转换为字符串形式然后输出到文档里面
				fileWritter.write(",");
				if((i+1)%5==0)
					fileWritter.write("\r\n");
			}
			System.out.println("习题文件已生成");
			fileWritter.close();
      	 } catch (IOException e) {
		   e.printStackTrace();
  		}
	}
	
	public void readCSVExercise(File aFile)throws Exception{
		//从习题文件中将习题读入程序中
		FileName = aFile.getName();
		operationList.clear();
		current = 0;
		String eqString = "";
		BinaryOperation op;
		FileInputStream in = new FileInputStream(aFile);
			int b = 0;
			while(b!=-1){
				b = in.read();
				if((char)b==','){
					op = AddSubOperation(eqString);
					Add(op);
					eqString = "";
				}else if(b!='\r'&&b!='\n')
					eqString+=(char)b;
			}
			System.out.println("习题已经读入");
			in.close();
	}
	void Add(BinaryOperation op) {
		//Add方法，将一个算式加入到exercise中
		operationList.add(op);
		OperationCount++;
	}
	//以下是像阶段一一样直接生成算式，并没有调用算式基
	public void writeCSVAdditionEercise(int count)
	//随机生成加法习题
	{
		generateAdditionExercise(count);//生成习题
		writeCSVExercise();//输出成文件
	}
	public void writeCSVSubstractEercise(int count)
	//随机生成减法习题
	{
		generateSubstractExercise(count);//生成习题
		writeCSVExercise();//输出成文件
	}
	public void writeCSVBinaryEercise(int count)
	//随机生成混合习题
	{
		generateBinaryExercise(count);//生成习题
		writeCSVExercise();//输出成文件
	}

	static boolean AddOrSub(String s){
		//传入一个字符串，判断该字符串是加法还是减法
		int pos = s.indexOf("+");
		if(pos!=-1)
			return true;//加法
		else
			return false;//减法
	}
	public static BinaryOperation AddSubOperation(String s){
		//传入一个算式字符串，将该字符串转换成算式对象，返回这个算式对象
		BinaryOperation bo;
		String left,right;
		int len = s.length();
		int pos;
		if(AddOrSub(s)){
			pos = s.indexOf('+');
			bo = new AdditionOperation();
		}
		else{
			pos = s.indexOf('-');
			bo = new SubstractOperation();
		}
		left = s.substring(0,pos);
		right = s.substring(pos+1,len-1);
		bo.insert(left,right);
		return bo;
	}
	public BinaryOperation getOperation (int index) {
		if (index < operationList.size())
			return operationList.get(index);
		else 
			return null;
	}
	
	public int length() {
		return OperationCount;
	}

}