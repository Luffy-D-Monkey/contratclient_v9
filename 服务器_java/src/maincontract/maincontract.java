package maincontract;
import DButil.DBcheckUser;
import DButil.UserSignUp;
import MsgUtil.MsgParse;
//�ҵ�github:https://github.com/ygj0930
//�ҵĲ��ͣ�http://www.cnblogs.com/ygj0930/
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
        // ����Socket������  
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
            // �߳�����ѭ����ʵʱ����socket�˿�  
            while (true)
            {  
                client = ResponseSocket();  //�õ�һ��socket
                // ��Ӧ�ͻ����������󡣡�  
                while (true) 
                {  
                    txt = ReceiveMsg(client);    
                    // ���ӻ�ÿͻ��˷���������  
                    System.out.println(txt); 
                    if (txt != null) 
                    {  
                        // ִ������  
                        resultList = exceCommand(txt);  //�õ�Ҫ���͵���Ϣ
                    }  
                    // ��ͻ��˷�����Ϣ  
                    SendMsg(client, resultList);  
                    // �жϣ��رմ˴�����  
                    break;  
                }  
                CloseSocket(client);  
            }  
        } catch (  Exception e) {  
            System.out.println(e);  
        }  

    }  
    //����һ��ServerSocketʵ�����󶨵�һ���ض��Ķ˿ڡ�һ���������˿�������֧��50�����ӣ���������ܾ����ӡ�  
    //���ԣ����ֻ����50���ͻ���ͬʱʹ�����ָ���Ķ˿��������������Ϣ  
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

    //��ͻ��˷�����Ϣ  
    private void SendMsg(Socket socket, ArrayList slist)  
    {  
		Collections.sort(slist);//����
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

    //�������Կͻ��˵���Ϣ��������ͨ��server.accept();�������Կͻ��˵��׽��֣�����I/O��ʽ  
    //���׽��ֵ���Ϣȡ����  
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

    //��̨������ȡҪ���͵���Ϣ�����ҷ���
    private ArrayList exceCommand(String command) 
    {  
    	MsgParse msp = new MsgParse();
    	
    	if(!(resultList.isEmpty()))
    		 resultList.clear();
    	 
    	 if(msp.getMsgType(command) == 1)//��¼
         {
         	String user= msp.parsezUser(command);
         	String pwd = msp.parsezPwd(command);
         	String loginResult =  new DBcheckUser().checklogin(user,pwd);//��¼���
         	if(loginResult.equals("noexit"))
         	{
         		System.out.println("�û�������");
         	}
         	else if(loginResult.equals("wrong"))
         	{
         		System.out.println("�������");
         	}
         	else
         	{
         		System.out.println("�û�:"+user+"��¼�ɹ�");
         	}
         	resultList.add(loginResult);	
         }
    	 else if(msp.getMsgType(command) == 2)
         {
         	UserSignUp usignup = new UserSignUp(command);
         	String signupResult = usignup.getSignupResult();   
         	resultList.add(signupResult);
         }
    	 else if(msp.getMsgType(command) == 3)//3���״ε�¼��ʼ���û��µ����б�
    	 {
    		 String initResult = "no";
    		 String database = msp.parsezUser(command);
    		 database = "u"+database;
    		 
    		 //false��ʾ�û�û�г�ʼ��
    		 boolean flags = new InDatabaseExit().isDatabaseExit(database);
    		//�״ε�¼��������s
    		 if(!flags)
    		 {
    			 System.out.println("delimiter===========initUserTables================");
    			 new initUserTables().init(database);
    			 System.out.println("delimiter==========initUserTables=================");
    			 initResult = "yes";
    		 }
    		 //yes��ʼ���ɹ���no�Ѿ�����
    		 resultList.add(initResult);	 
    	 }  
    	 else if(msp.getMsgType(command) == 4)//3����������ϵ��
    	 {
    		 //�׸��յ���id����ǰ��u����
    		 String id = msp.parsezUser(command);
    		 id = "u"+id;
    		 
    	     resultList = new GetContacts().getContacts(id);
	    	     for(int i = 0; i<resultList.size(); i++)
	        		 System.out.println("��ѯ����ϵ��-��"+resultList.get(i));
    	 }
    	 else if(msp.getMsgType(command) == 5)//5�����û���Ϣ
    	 {
    		
    		 String id = msp.parsezUser(command);
    		 resultList.add(new GetUserInfo().getUserInfo(id));
    	 }
    	 else if(msp.getMsgType(command) == 6)//6�����޸��û���Ϣ
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
    	 else if(msp.getMsgType(command) == 9)//9�������
    	 {
    	     System.out.println("999999999999999999999999999999"+command);

    		 String []ss = command.split(" ");
    	     resultList = new GetContactTypes().getContactTypes("u"+ss[1]);
    	     
    	 }
    	 else if(msp.getMsgType(command) == 10)//ɾ���������
    	 {
    		 resultList = new DeleteContactsType().deleteContactsType(command);
    	 }
    	 else if(msp.getMsgType(command) == 11)//��������ϲ��
    	 {
    		 System.out.println("main:command"+command);
    		 System.out.println("main:commandsize"+command.length());
    		 resultList.add(new AddContacts().addContacts(command));
    	 }
    	 else if(msp.getMsgType(command) == 12)//12�޸������
    	 {
    		 resultList = new ModifyType().modifyType(command);
    	 }
    	 
    	 else if(msp.getMsgType(command) == 13)//1ɾ����ϵ��
    	 {
    		 resultList = new DeleteContact().deleteContact(command);
    	 }
    	 else if(msp.getMsgType(command) == 14)// 14�޸���ϵ��
    	 {
    		 resultList.add(new UpdateContact().updateContact(command));
    	 }
    	 else if(msp.getMsgType(command) == 15)// ��ѯ��������
    	 {
    		 resultList = new QueryFriendreaqust().queryFriendreaqust(command);
    	 }
    	 else if(msp.getMsgType(command) == 16)// ����һ�����
    	 {
    		 resultList.add(new AddFriend().addFriend(command));
    	 }
    	 else if(msp.getMsgType(command) == 17)// һ�����
    	 {
    		 resultList.add(new EasyAddFriend().easyAddFriend(command));
    	 }
    	 else if(msp.getMsgType(command) == 18)// һ�����
    	 {
    		 String []ss = command.split(" ");
    		 resultList.add(new DropUser().dropUser(ss[1]));
    	 }
    	 else if(msp.getMsgType(command) == 19)// һ�����
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