package MsgUtil;

public class MsgParse
{
	public MsgParse()
	{
		
	}
	
	//1��¼ 2ע�� 3���״ε�¼��ʼ���û��µ����б�  4����������ϵ��  5��ѯ�û����� 6�޸��û�����
	//7�޸�����   8�������   9�������   10���ɾ�� 11������ϵ�� 12�޸������  13ɾ����ϵ�� 14�޸���ϵ��
	//15 ��ѯ��������  16һ���������   17һ�����   18ע��   19�ܾ�����
	public int getMsgType(String msg)
	{
		System.out.print("ssssssssss5555555555555555555sssssssssssssssssssssss"+"\n");
		String []a = msg.split(" ");
		if(a[0].equals("1"))
			return 1;
		else if(a[0].equals("2"))
			return 2;
		else if(a[0].equals("3"))
			return 3;
		else if(a[0].equals("4"))
			return 4;
		else if(a[0].equals("5"))
			return 5;
		else if(a[0].equals("6"))
			return 6;
		else if(a[0].equals("7"))
			return 7;
		else if(a[0].equals("8"))
			return 8;
		else if(a[0].equals("9"))
			return 9;
		else if(a[0].equals("10"))
		{
			return 10;
		}
		else if(a[0].equals("11"))
			return 11;
		else if(a[0].equals("12"))
			return 12;
		else if(a[0].equals("13"))
			return 13;
		else if(a[0].equals("14"))
			return 14;
		else if(a[0].equals("15"))
			return 15;
		else if(a[0].equals("16"))
			return 16;
		else if(a[0].equals("17"))
			return 17;
		else if(a[0].equals("18"))
			return 18;
		else if(a[0].equals("19"))
			return 19;
		else
			
			return 7;
	}
	//�����û���
	public String parsezUser(String msg)
	{
		
	
		String[] ss = msg.split(" ");
		String user = ss[1].toString();
		return user;
		
	}
	
	//��������
	public String parsezPwd(String msg)
	{
		String[] ss = msg.split(" ");
		String pwd = ss[2].toString();
		return pwd;
	}
	
	public String parseSql(String msg)
	{
		String sql = msg.substring(2,msg.length());
		return sql;
		//System.out.print(sql+"\n");
	}
}
