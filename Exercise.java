package work;
import java.util.*;
//BinaEquation��ļ��ϣ���hashset���ϴ洢����50��Ԫ��
public class Exercise {

	//set�ӿڵ��ص㣺������洢�ظ���Ԫ��
	//û��������û�д������ķ�����Ҳ����ʹ����ͨ��forѭ������
	
	/*��Ա����*/
	//����һ��hashset���ϴ洢��ʽ
	public HashSet<BinaryEquation> set = new HashSet<BinaryEquation>();	//��������Ϊ��������
	
	//���캯��
	public Exercise()
	{
		genEquations();
	}
	//���ɶ�Ԫ��ʽ
	public void genEquations()
	{
		//ʵ����һ��random����
		Random random = new Random();
		//ѭ��50��
		while(set.size()<50)
		{
			if(random.nextBoolean())
			{
				//ʵ����һ���ӷ���ʽ
				AddEquation adde = new AddEquation();	//������������ת��Ϊ��������
				//��ӵ�set��
				set.add(adde);//ʵ�������ิ�Ƹ�����
			}
			else
			{
				SubEquation sube = new SubEquation();   //������������ת��Ϊ��������
				
				set.add(sube);//ʵ�������ิ�Ƹ�����
			}
		}

	}
	//��ʽ�����
	public void Formatted()
	{	
		int count = 0;
		System.out.println("����������������������������������Ŀ�������������������������������� ");
		
		Iterator<BinaryEquation> iterator1 = set.iterator();		//����������
		BinaryEquation tempb = null;
		while(iterator1.hasNext())
		{
			tempb = iterator1.next();
			if(tempb.Operator == 1)//���Ϊ��
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
		System.out.println("���������������������������������𰸡������������������������������� ");
		Iterator<BinaryEquation> iterator2 = set.iterator();		//����������
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
