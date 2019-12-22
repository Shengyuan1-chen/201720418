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
	private int OperationCount = 0;//��������¼���Exercise����ʽ����
	protected String FileName ;//��������ϰ����
	public String Addr = "D:/�Ӽ�����ϰ/";//��������ϰ��ַ
	private int current = 0;//��¼��ǰ����λ��
	
	class Answers implements Serializable {	//���ڲ���
		private static final long serialVersionUID = -232662116583465565L;
		String content;		//������
		boolean correct;	//��ȷ��
		public Answers() {content = ""; correct = false;}
		public Answers(String ans, boolean cr) {
			content = ans; correct = cr;
		}
	}
	
	private ArrayList<BinaryOperation> operationList = new ArrayList<BinaryOperation>();
	private List <Answers> answers = new ArrayList<>();		//�û���д��������Ŀ�Ĵ��б�
	
	private ExerciseType currentType;		//��ǰ��Ŀ���ͣ�Ϊ���л�������
	public ExerciseType getCurrentType() {return currentType;}
	
	public String getExerciseType() {
		String s = new String();
		switch(currentType) {
		case ADDandSUB:
			s = "�Ӽ����";
			break;
		case ADD:
			s = "���ӷ�";
			break;
		case SUB:
			s = "������";
			break;
		}	
		return s;
	}
	
	private void setCurrentType(ExerciseType type) { this.currentType = type;}
	public void setAnswer(int index, String ans) {		
		//���ô����ݲ��ж���ȷ��
		BinaryOperation op;
		op = operationList.get(index);		//��ȡ��ʽ
		String result = String.valueOf(op.getvalue());		//��ȡ��ȷ���
		String tans = ans.trim();//��ȡ�û�����Ĵ�
		answers.set(index, new Answers(tans, result.equals(tans)));  
		//���ô����ݺ���ȷ�ԣ�indexλ��������Ϊ��Ӧ��answer���󣬶������汣���û��𰸺��Ƿ���ȷ
	}
	public String getAnswer(int index) {	//��ȡ��
		return answers.get(index).content;
	}
	public void clearAnswers() {		//��մ𰸣�ȫ��Ϊ�գ�����
		for (int i = 0; i < answers.size(); i++)
			answers.set(i, new Answers("", false));
	}
	
	public int Correct() {		//ͳ����ȷ����
		int correctAmount = 0;
		for (int i = 0; i < answers.size(); i++) {
			if (getJudgement(i))
				correctAmount++;
		}
		return correctAmount;
	}
	
	public boolean getJudgement(int index) {	
		//��ȡ������
		return answers.get(index).correct;
	}
	
	public void saveObject(String filename, Exercise exercise) throws ExerciseIOException{		
		//���л��洢����
		FileOutputStream f = null;
		try {
			 f = new FileOutputStream(filename);
			 //���Դ���һ���ַ������ļ���ַ,��ַ��Ϊ��ʱ,�Զ�Ϊ�ַ�������File����
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		ObjectOutputStream s = null;
		try {
			 s = new ObjectOutputStream(f);
			 //����ObjectOutputStream������Ҫ��һ��FileOutputStream�������ļ��ֽ����������
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		try {
			s.writeObject(exercise);//���ļ��������ʽ
			s.flush();
			//ˢ�´��������ǿ��д�����л��������ֽڡ�
			//flush �ĳ���Э���ǣ�������������ʵ���Ѿ���������ǰд����κ��ֽڣ�����ô˷���ָʾӦ����Щ�ֽ�����д������Ԥ�ڵ�Ŀ�ꡣ
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		try {
			s.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}
	public static Exercise loadObject(String filename) throws ExerciseIOException{	
		//���л��������
		FileInputStream in = null;
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		ObjectInputStream s = null;
		try {
			s = new ObjectInputStream(in);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		Exercise exercise = null;
		try {
			exercise = (Exercise) s.readObject();
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		try {
			s.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return exercise;
	}
	public void generateWithFormerType(int operationCount) {	
		//����ԭ������������Ŀ
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
	//�ع�����
	
	
	//����һ������BinaryOperation����ʽ���Ķ�̬����
	
	public Exercise() {
		File aFile = new File(Addr);
		if(!aFile.exists())
				aFile.mkdir();
	}
	
	public boolean hasNext(){
		return current<=operationList.size()-1;
	}//�����黹��Ԫ�أ��򷵻��ǣ����򷵻ط�

	public BinaryOperation next(){
		return operationList.get(current++);
	}//����Ԫ�أ����ص�ǰԪ�أ���ǰ����λ���ƶ�����һ��λ��

	public boolean contains(BinaryOperation anOperation){
		boolean flag = true;
		int size = operationList.size();
		if(size==0){//���������û����ʽ������false�������������
			return false;
		}
		for(int i = 0;i<size;i++){//�����������е���ʽ�������ɵ���ʽ���Ƚϣ�����Ƿ�����ظ�
			if(!operationList.get(i).equals(anOperation)){
				flag = false;
			}
		}
		return flag;
	}
	
	private BinaryOperation generateOperation(){
		//�������һ���ӷ����߼�����ʽ������
		Random random = new Random();
		int opValue = random.nextInt(2);
		if(opValue==1){
			return new AdditionOperation();
		}
		return new SubstractOperation();
	}

	public void generateBinaryExercise(int operationCount){
	//��������generateOperation����������ɵļӷ����߼�����ʽ���뵽��̬������
		setCurrentType(ExerciseType.ADDandSUB ); //������Ŀ����
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
				//��anOperation ���뵽Exercise��
				operationCount--;
			}
	}

	public void generateAdditionExercise(int operationCount){
		//������ɼӷ���ʽ����������
		setCurrentType(ExerciseType.ADD ); //������Ŀ����
		
		answers.clear();
		operationList.clear();
		current = 0;
		OperationCount = operationCount;
		FileName= "Addition_exercise_"+operationCount+"_";
		BinaryOperation anOperation1;
			while(operationCount>0){
				do{anOperation1 = new AdditionOperation();}while(contains(anOperation1));
				operationList.add(anOperation1);//��anOperation ���뵽Exercise��
				answers.add(new Answers());
				operationCount--;
			}
	}
	
	public void generateSubstractExercise(int operationCount){
	//������ɼ�����ʽ����������
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
				//��anOperation ���뵽Exercise��
				operationCount--;
		}
	}

	public void writeCSVExercise()//�����ļ�
	{
		int count = OperationCount;//��ʽ����
		FileName = Addr + FileName+".csv";//����ϰ���ļ�����
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
				fileWritter.write(operationList.get(i).toString());//����ʽת��Ϊ�ַ�����ʽȻ��������ĵ�����
				fileWritter.write(",");
				if((i+1)%5==0)
					fileWritter.write("\r\n");
			}
			System.out.println("ϰ���ļ�������");
			fileWritter.close();
      	 } catch (IOException e) {
		   e.printStackTrace();
  		}
	}
	
	public void readCSVExercise(File aFile)throws Exception{
		//��ϰ���ļ��н�ϰ����������
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
			System.out.println("ϰ���Ѿ�����");
			in.close();
	}
	void Add(BinaryOperation op) {
		//Add��������һ����ʽ���뵽exercise��
		operationList.add(op);
		OperationCount++;
	}
	//��������׶�һһ��ֱ��������ʽ����û�е�����ʽ��
	public void writeCSVAdditionEercise(int count)
	//������ɼӷ�ϰ��
	{
		generateAdditionExercise(count);//����ϰ��
		writeCSVExercise();//������ļ�
	}
	public void writeCSVSubstractEercise(int count)
	//������ɼ���ϰ��
	{
		generateSubstractExercise(count);//����ϰ��
		writeCSVExercise();//������ļ�
	}
	public void writeCSVBinaryEercise(int count)
	//������ɻ��ϰ��
	{
		generateBinaryExercise(count);//����ϰ��
		writeCSVExercise();//������ļ�
	}

	static boolean AddOrSub(String s){
		//����һ���ַ������жϸ��ַ����Ǽӷ����Ǽ���
		int pos = s.indexOf("+");
		if(pos!=-1)
			return true;//�ӷ�
		else
			return false;//����
	}
	public static BinaryOperation AddSubOperation(String s){
		//����һ����ʽ�ַ����������ַ���ת������ʽ���󣬷��������ʽ����
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