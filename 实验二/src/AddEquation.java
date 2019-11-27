package work;

import java.util.Random;


public class AddEquation extends BinaryEquation{
	
	
	//构造方法
	public AddEquation(){
		getEquation();	//调用函数
		this.Operator = 0; //定义0为加
	}
	//得到方程式
	public void getEquation()
	{	
		generateBinaryOperation(); //得到操作数和结果 
	}
	//判断加法算式之间是否相等
	public boolean equals(BinaryEquation be1,BinaryEquation be2)
	{
		boolean isequal = false;
		//如果运算符相等
		if(be1.Operator==be2.Operator)
		{
			//运算结果相等
			if(be1.value ==be2.value)
			{
				//若式子中有一个操作数相等,即结果相等
				if(be1.operand1 == be2.operand1 || be1.operand1 == be2.operand2 ||be1.operand2 == be2.operand1 || be1.operand2 == be2.operand2 )
				{
					isequal = true;
				}
				else
				{
					isequal = false;
				}
			
			}
			else
			{
				isequal = false;
			}
		}
		else
		{
			isequal = false;
		}
		
		return isequal;
	}
	//计算结果
	public int calculate(int o1,int o2)
	{

		return (o1 + o2);
	}
	//转化为字符串
	public String tostring()

	{
		String op1 = String.format("%-3d", this.operand1);
		String op2= String.format("%-3d", this.operand2);
		String val= String.format("%-3d", this.value);
		return (op1+"+"+op2+"= "+val);
		
	}
	//随机生成加法算式
	public void generateBinaryOperation()
	{
		Random random = new Random();	//生成一个Random对象
		int o2;
		int o1;
		int val;
		do 
		{
			o1 = random.nextInt(UPPER+1);	//生成第一个加法操作数
			o2 = random.nextInt(UPPER+1);	//生成第二个加法操作数
			val = calculate(o1,o2);				//计算结果
			
		} while (!checkResult(val));
		//给成员变量赋值
		operand1 = o1;
		operand2 = o2;
		value = val;
		
	}
	//判断加法算式是否合法
	public boolean checkResult(int value)
	{
		boolean islegal = false;
		if (value<=100) {
			islegal = true;
		}
		else {
			islegal = false;
		}
		return islegal;
	}
	//主函数
	public static void main(String[] args)
	{
		/*test
		//创建一个哈希set
		HashSet<BinaryEquation> set = new HashSet<BinaryEquation>();
		
		AddEquation a1 = new AddEquation(5, 6, 1);
		a1.value = a1.calculate(a1.operand1, a1.operand2);
		set.add(a1);
		
		System.out.println(set.size());
		AddEquation a2 = new AddEquation(5, 6, 1);	
		set.add(a2);
		
		System.out.println(set.size());
		AddEquation a3 = new AddEquation(5, 6, 1);
		set.add(a3);
		
		System.out.println(set.size());
		AddEquation a4 = new AddEquation(5, 7, 1);
		set.add(a4);
		
		System.out.println(set.size());
		AddEquation a5 = new AddEquation(5, 6, 0);
		set.add(a5);
		
		System.out.println(set.size());
		*/
		
		//AddEquation a1 = new AddEquation();

	}
	
}
