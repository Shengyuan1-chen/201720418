package work;



public abstract class BinaryEquation {
	/*成员变量*/
	//定义操作的上界和下界
	static final int UPPER = 100;
	static final int LOWER = 0;
	public int operand1;
	public int operand2;
	public int value = 0;
	public int Operator;

	/*抽象类*/
	
	//得到方程式
	public abstract void getEquation();
	//生成操作数
	public abstract void generateBinaryOperation();
	//判断算式是否相等
	public abstract boolean equals(BinaryEquation be1,BinaryEquation be2);
	//计算结果
	public abstract int calculate(int o1,int o2);
	//转换为字符串
	public abstract String tostring();
	//检查结果是否合法
	public abstract boolean checkResult(int value);
	
	/*重写方法*/
	
	//重写Object顶层类中的equals方法，定义自己判断重复的规则，返回false即存入，返回true则不存入
	public boolean equals(Object obj)
	{
		if(this == obj)	//如果传入的对象是自己
		{
			return true; //不存入
		}
		if(obj!=null && obj instanceof BinaryEquation)	//如果传入的对象不为空，并且obj对象是本类的子类或者为本类的对象
		{
			BinaryEquation trans = (BinaryEquation) obj;	//obj类型的对象强制转换为本类对象
			if(trans.operand1 == this.operand1 && trans.operand2 ==this.operand2 && trans.Operator == this.Operator)	//如果操作数，计算符号都相等的话就返回true
			{
				return true;	//相同的不存入后者
			}
		}
		return false;	//默认是存入的
	}
	//重写object顶层类中的hashCode方法，并且以计算结果作为哈希值返回
	public int hashCode()
	{
		int hash;
		hash = this.value;
		return hash;
	}
	//主函数
	public static void main(String[] args)
	{
		
			
			
		
		
	}

}
