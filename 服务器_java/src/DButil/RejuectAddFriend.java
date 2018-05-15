package DButil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RejuectAddFriend
{

	public String rejuectAddFriend(String command)
	{
		//String request = "19 "+uuuid+" "+res;
        
		String []ss = command.split(" ");
		
		String Receiver= ss[1],Requester = ss[2];
		
		 Connection conn = new MysqlConnect().connect("adminroot");
		 //插入联系人
		 Statement stmt = null;
		 try
		{
			stmt = conn.createStatement();
			
			String sql ="delete from friend where Requester = '"+Requester+"' and Receiver = '"+Receiver+"'";
			//String desq2 ="delete from friend where Requester = '"+id2+"' and Receiver = '"+id1+"'";
			if(stmt.executeUpdate(sql) == 1)
				return "successed";
			
			//stmt1.executeUpdate(desq2);
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return "failed";
		 
		 
	}
	
}
