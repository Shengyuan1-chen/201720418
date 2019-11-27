package work;
import java.util.*;
//BinaEquation类的集合，用hashset集合存储内有50个元素
public class Exercise {

	//set接口的特点：不允许存储重复的元素
	//没有索引，没有带索引的方法，也不能使用普通的for循环遍历
	
	/*成员变量*/
	//声明一个hashset集合存储算式
	public HashSet<BinaryEquation> set = new HashSet<BinaryEquation>();	//类型声明为父类类型
	
	//构造函数
	public Exercise()
	{
		genEquations();
	}
	//生成二元算式
	public void genEquations()
	{
		//实例化一个random对象
		Random random = new Random();
		//循环50次
		while(set.size()<50)
		{
			if(random.nextBoolean())
			{
				//实例化一个加法算式
				AddEquation adde = new AddEquation();	//子类类型向上转型为父类类型
				//添加到set中
				set.add(adde);//实例化子类复制给父类
			}
			else
			{
				SubEquation sube = new SubEquation();   //子类类型向上转型为父类类型
				
				set.add(sube);//实例化子类复制给父类
			}
		}

	}
	//格式化输出
	public void Formatted()
	{	
		int count = 0;
		System.out.println("————————————————题目———————————————— ");
		
		Iterator<BinaryEquation> iterator1 = set.iterator();		//声明索引器
		BinaryEquation tempb = null;
		while(iterator1.hasNext())
		{
			tempb = iterator1.next();
			if(tempb.Operator == 1)//如果为减
			{
				System.out.printf("%-3d-%-3d =        ",tempb.operand1 , tempb.operand2) ;
			}
			else
			{
				System.out.printf("%-3d+%-3d =        ", tempb.operand1 ,tempb.operand2) ;
			}
			
			count++;
			
			if (count%5==0) 
			{
				System.out.println();
			}
		}
		System.out.println();
		System.out.println("————————————————答案———————————————— ");
		Iterator<BinaryEquation> iterator2 = set.iterator();		//声明索引器
		while(iterator2.hasNext())
		{
			System.out.print(iterator2.next().tostring()+"     ");
			count++;
			if (count%5==0) 
			{
				System.out.println();
			}
		}
	}

}
