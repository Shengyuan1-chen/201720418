package work;



public abstract class BinaryEquation {
	/*��Ա����*/
	//����������Ͻ���½�
	static final int UPPER = 100;
	static final int LOWER = 0;
	public int operand1;
	public int operand2;
	public int value = 0;
	public int Operator;

	/*������*/
	
	//�õ�����ʽ
	public abstract void getEquation();
	//���ɲ�����
	public abstract void generateBinaryOperation();
	//�ж���ʽ�Ƿ����
	public abstract boolean equals(BinaryEquation be1,BinaryEquation be2);
	//������
	public abstract int calculate(int o1,int o2);
	//ת��Ϊ�ַ���
	public abstract String tostring();
	//������Ƿ�Ϸ�
	public abstract boolean checkResult(int value);
	
	/*��д����*/
	
	//��дObject�������е�equals�����������Լ��ж��ظ��Ĺ��򣬷���false�����룬����true�򲻴���
	public boolean equals(Object obj)
	{
		if(this == obj)	//�������Ķ������Լ�
		{
			return true; //������
		}
		if(obj!=null && obj instanceof BinaryEquation)	//�������Ķ���Ϊ�գ�����obj�����Ǳ�����������Ϊ����Ķ���
		{
			BinaryEquation trans = (BinaryEquation) obj;	//obj���͵Ķ���ǿ��ת��Ϊ�������
			if(trans.operand1 == this.operand1 && trans.operand2 ==this.operand2 && trans.Operator == this.Operator)	//�����������������Ŷ���ȵĻ��ͷ���true
			{
				return true;	//��ͬ�Ĳ��������
			}
		}
		return false;	//Ĭ���Ǵ����
	}
	//��дobject�������е�hashCode�����������Լ�������Ϊ��ϣֵ����
	public int hashCode()
	{
		int hash;
		hash = this.value;
		return hash;
	}
	//������
	public static void main(String[] args)
	{
		
			
			
		
		
	}

}
