package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AddFriend
{
	Connection conn = null;
	public String addFriend(String turecommand)
	{
		 String result = "failed";
		
		String []ss = turecommand.split(" ");
		conn = new MysqlConnect().connect("adminroot");
		Statement stmt = null;

	    try
		{
			stmt = conn.createStatement();
			String sql = "insert into friend set Requester ='"+ss[1]+"',Receiver ='"+ss[2]+"'"; 
			if(stmt.executeUpdate(sql) == 1)
				result = "successed";
			
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    //failed successed
	    return result;
		
	}
}
