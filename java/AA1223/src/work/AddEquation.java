package work;

import java.util.Random;


public class AddEquation extends BinaryEquation{
	
	
	//���췽��
	public AddEquation(){
		getEquation();	//���ú���
		this.Operator = 0; //����0Ϊ��
	}
	//�õ�����ʽ
	public void getEquation()
	{	
		generateBinaryOperation(); //�õ��������ͽ�� 
	}
	//�жϼӷ���ʽ֮���Ƿ����
	public boolean equals(BinaryEquation be1,BinaryEquation be2)
	{
		boolean isequal = false;
		//�����������
		if(be1.Operator==be2.Operator)
		{
			//���������
			if(be1.value ==be2.value)
			{
				//��ʽ������һ�����������,��������
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
	//������
	public int calculate(int o1,int o2)
	{

		return (o1 + o2);
	}
	//ת��Ϊ�ַ���
	public String tostring()

	{
		String op1 = String.format("%-3d", this.operand1);
		String op2= String.format("%-3d", this.operand2);
		String val= String.format("%-3d", this.value);
		return (op1+"+"+op2+"= "+val);
		
	}
	//������ɼӷ���ʽ
	public void generateBinaryOperation()
	{
		Random random = new Random();	//����һ��Random����
		int o2;
		int o1;
		int val;
		do 
		{
			o1 = random.nextInt(UPPER+1);	//���ɵ�һ���ӷ�������
			o2 = random.nextInt(UPPER+1);	//���ɵڶ����ӷ�������
			val = calculate(o1,o2);				//������
			
		} while (!checkResult(val));
		//����Ա������ֵ
		operand1 = o1;
		operand2 = o2;
		value = val;
		
	}
	//�жϼӷ���ʽ�Ƿ�Ϸ�
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
	//������
	public static void main(String[] args)
	{
		/*test
		//����һ����ϣset
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
