package DButil;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class DropUser
{
	Connection conn = null;
	public String dropUser(String id)
	{
		 
		
		conn = new MysqlConnect().connect("adminroot");
		Statement stmt = null;
		int change = 0;
		try
		{
			stmt = conn.createStatement();
			String desql ="delete from user where id = '"+id+"'";
			System.out.println("drop result->"+stmt.executeUpdate(desql));
			
			
			desql = "drop database "+"u"+id;
			System.out.println("drop result->"+stmt.executeUpdate(desql));
			
			
				return "successed";
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "failed";
	}
	
    //String request = "18 "+uuuid+" ";

}
