package maincontract;
import DButil.DBcheckUser;
import DButil.UserSignUp;
import MsgUtil.MsgParse;
//我的github:https://github.com/ygj0930
//我的博客：http://www.cnblogs.com/ygj0930/
import java.net.*;
import java.io.*;
import java.util.*;
import DButil.*;

public class maincontract extends Thread
{      
	private ServerSocket server = null;  
    private static final int PORT = 6000;  
    private BufferedWriter writer;  
    private BufferedReader reader;  
    ArrayList<String> resultList = new ArrayList<String>();
    private maincontract() 
    {  
        // 创建Socket服务器  
        try
		{
			CreateSocket();
		} catch (Exception e)
		{
			e.printStackTrace();
		}  
    }  

    public void run() {  
        Socket client;  
        String txt;  
      	
        try {  
            // 线程无限循环，实时监听socket端口  
            while (true)
            {  
                client = ResponseSocket();  //得到一个socket
                // 响应客户端链接请求。。  
                while (true) 
                {  
                    txt = ReceiveMsg(client);    
                    // 链接获得客户端发来的命令  
                    System.out.println(txt); 
                    if (txt != null) 
                    {  
                        // 执行命令  
                        resultList = exceCommand(txt);  //得到要发送的信息
                    }  
                    // 向客户端返回消息  
                    SendMsg(client, resultList);  
                    // 中断，关闭此次连接  
                    break;  
                }  
                CloseSocket(client);  
            }  
        } catch (  Exception e) {  
            System.out.println(e);  
        }  

    }  
    //创建一个ServerSocket实例，绑定到一个特定的端口。一个服务器端口最多可以支持50个链接，超出了则拒绝链接。  
    //所以，最多只能有50个客户端同时使用这个指定的端口向服务器发送消息  
    private void CreateSocket()
    {  
        try
		{
			server = new ServerSocket(PORT);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println("Server starting..");  
    }  
    private Socket ResponseSocket() 
    {  
        Socket client = null;
		try
		{
			client = server.accept();
		} catch (IOException e)
		{
			e.printStackTrace();
		}  
        System.out.println("client connected..");  
        return client;  
    }  
    private void CloseSocket(Socket socket)  {  
        try
		{
			reader.close();
			writer.close();  
	        socket.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}  
        ;  
        System.out.println("client closed..");  
    }  

    //向客户端发送消息  
    private void SendMsg(Socket socket, ArrayList slist)  
    {  
		Collections.sort(slist);//排序
		try
		{
			writer = new BufferedWriter(new OutputStreamWriter(  
			        socket.getOutputStream(),"UTF-8"));
			for(int i = 0; i<slist.size(); i++)
			{
				System.out.println(slist.get(i));
				writer.write(slist.get(i) + "\n");
				writer.flush(); 
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
       
    }  

    //接收来自客户端的消息。服务器通过server.accept();接收来自客户端的套接字，采用I/O方式  
    //将套接字的消息取出来  
    private String ReceiveMsg(Socket socket)
    {  
        try
		{
			reader = new BufferedReader(new InputStreamReader(  
			        socket.getInputStream(),"UTF-8"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}  
        System.out.println("server get input from client socket.."); 
        String line = null;  
        try
		{
			while ((line = reader.readLine()) != null)
			{   
			    return line;  
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}  
        return line;  
    }  

    //后台处理，获取要发送的信息，并且返回
    private ArrayList exceCommand(String command) 
    {  
    	MsgParse msp = new MsgParse();
    	
    	if(!(resultList.isEmpty()))
    		 resultList.clear();
    	 
    	 if(msp.getMsgType(command) == 1)//登录
         {
         	String user= msp.parsezUser(command);
         	String pwd = msp.parsezPwd(command);
         	String loginResult =  new DBcheckUser().checklogin(user,pwd);//登录结果
         	if(loginResult.equals("noexit"))
         	{
         		System.out.println("用户不存在");
         	}
         	else if(loginResult.equals("wrong"))
         	{
         		System.out.println("密码错误");
         	}
         	else
         	{
         		System.out.println("用户:"+user+"登录成功");
         	}
         	resultList.add(loginResult);	
         }
    	 else if(msp.getMsgType(command) == 2)
         {
         	UserSignUp usignup = new UserSignUp(command);
         	String signupResult = usignup.getSignupResult();   
         	resultList.add(signupResult);
         }
    	 else if(msp.getMsgType(command) == 3)//3若首次登录初始化用户下的所有表
    	 {
    		 String initResult = "no";
    		 String database = msp.parsezUser(command);
    		 database = "u"+database;
    		 
    		 //false表示用户没有初始化
    		 boolean flags = new InDatabaseExit().isDatabaseExit(database);
    		//首次登录，创建表s
    		 if(!flags)
    		 {
    			 System.out.println("delimiter===========initUserTables================");
    			 new initUserTables().init(database);
    			 System.out.println("delimiter==========initUserTables=================");
    			 initResult = "yes";
    		 }
    		 //yes初始化成功，no已经存在
    		 resultList.add(initResult);	 
    	 }  
    	 else if(msp.getMsgType(command) == 4)//3请求所有联系人
    	 {
    		 //献给收到的id加上前叙‘u’，
    		 String id = msp.parsezUser(command);
    		 id = "u"+id;
    		 
    	     resultList = new GetContacts().getContacts(id);
	    	     for(int i = 0; i<resultList.size(); i++)
	        		 System.out.println("查询的联系人-》"+resultList.get(i));
    	 }
    	 else if(msp.getMsgType(command) == 5)//5请求用户信息
    	 {
    		
    		 String id = msp.parsezUser(command);
    		 resultList.add(new GetUserInfo().getUserInfo(id));
    	 }
    	 else if(msp.getMsgType(command) == 6)//6请求修改用户信息
    	 {
    	     resultList.add(new UpdateUserInfo().updateUserInfo(command));
     	 }
    	 else if(msp.getMsgType(command) == 7)//
    	 {
    		 System.out.println(command+"---sssssssssssss----"+"command = 7");
    	     resultList.add(new UpdateUserPassword().updateUserPassword(command));
    	 }
    	 else if(msp.getMsgType(command) == 8)//xinzeingleibie
    	 {
    	     resultList.add(new NewContactsType().newContactsType(command));
    	 }
    	 else if(msp.getMsgType(command) == 9)//9请求类别
    	 {
    	     System.out.println("999999999999999999999999999999"+command);

    		 String []ss = command.split(" ");
    	     resultList = new GetContactTypes().getContactTypes("u"+ss[1]);
    	     
    	 }
    	 else if(msp.getMsgType(command) == 10)//删除请求类别
    	 {
    		 resultList = new DeleteContactsType().deleteContactsType(command);
    	 }
    	 else if(msp.getMsgType(command) == 11)//新增来年喜人
    	 {
    		 System.out.println("main:command"+command);
    		 System.out.println("main:commandsize"+command.length());
    		 resultList.add(new AddContacts().addContacts(command));
    	 }
    	 else if(msp.getMsgType(command) == 12)//12修改类别名
    	 {
    		 resultList = new ModifyType().modifyType(command);
    	 }
    	 
    	 else if(msp.getMsgType(command) == 13)//1删除联系人
    	 {
    		 resultList = new DeleteContact().deleteContact(command);
    	 }
    	 else if(msp.getMsgType(command) == 14)// 14修改联系人
    	 {
    		 resultList.add(new UpdateContact().updateContact(command));
    	 }
    	 else if(msp.getMsgType(command) == 15)// 查询好友请求
    	 {
    		 resultList = new QueryFriendreaqust().queryFriendreaqust(command);
    	 }
    	 else if(msp.getMsgType(command) == 16)// 请求一键添加
    	 {
    		 resultList.add(new AddFriend().addFriend(command));
    	 }
    	 else if(msp.getMsgType(command) == 17)// 一键添加
    	 {
    		 resultList.add(new EasyAddFriend().easyAddFriend(command));
    	 }
    	 else if(msp.getMsgType(command) == 18)// 一键添加
    	 {
    		 String []ss = command.split(" ");
    		 resultList.add(new DropUser().dropUser(ss[1]));
    	 }
    	 else if(msp.getMsgType(command) == 19)// 一键添加
    	 {
    		
    		 resultList.add(new RejuectAddFriend().rejuectAddFriend(command));
    	 }
        return resultList;  
    }  
    public static void main(final String args[]) {  
    	maincontract commandServer = null;
			try
			{
				commandServer = new maincontract();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		 
        if (commandServer != null) {  
            commandServer.start();  
        }  
    }  
}