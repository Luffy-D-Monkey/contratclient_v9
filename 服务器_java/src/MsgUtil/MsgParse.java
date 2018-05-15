package MsgUtil;

public class MsgParse
{
	public MsgParse()
	{
		
	}
	
	//1登录 2注册 3若首次登录初始化用户下的所有表  4请求所有联系人  5查询用户资料 6修改用户资料
	//7修改密码   8新增类别   9请求类别   10类别删除 11新增联系人 12修改类别名  13删除联系人 14修改联系人
	//15 查询好友请求  16一键添加请求   17一键添加   18注销   19拒绝请求
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
	//解析用户名
	public String parsezUser(String msg)
	{
		
	
		String[] ss = msg.split(" ");
		String user = ss[1].toString();
		return user;
		
	}
	
	//解析密码
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
