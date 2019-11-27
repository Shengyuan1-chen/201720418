package work;

import java.util.Random;

public class SubEquation extends BinaryEquation {
	
	
	
	//���췽��
	public SubEquation(){
		getEquation();	//�������ɷ���ʽ����
		this.Operator = 1; //����1Ϊ��
	}
	//�õ�����ʽ
	public void getEquation()
	{	
		generateBinaryOperation(); //�õ��������ͽ�� ��˳��ִ�У��õ���������ʽ
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
				//��ʽ���в�����ֻҪ��һ�Զ�Ӧ��ȼ�Ϊ��ȵ���ʽ
				if(be1.operand1 == be2.operand1 || be1.operand2 == be2.operand2 )
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
		return (o1 - o2);
	}
	//ת��Ϊ�ַ���
	public String tostring()
	{
		String op1 = String.format("%-3d", this.operand1);
		String op2= String.format("%-3d", this.operand2);
		String val= String.format("%-3d", this.value);
		return (op1+"-"+op2+"= "+val);
	}
	//������ɼӷ���ʽ
	public void generateBinaryOperation()
	{
		Random random = new Random();	//����һ��Random����
		int o1;
		int o2;
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
		if (value>=0) {
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
		
		
	}
}
