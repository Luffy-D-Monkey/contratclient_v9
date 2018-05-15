package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GetUserInfo
{
	Connection conn = null;
	public  GetUserInfo()
	{
		
	}
	
	public  String getUserInfo(String uuuserid)
	{
		String result = "failed ";
		
		System.out.println("getUserInfo>>>>>>>>>>>>"+uuuserid);
	
		try
		{
			conn = new MysqlConnect().connect("adminroot");
			Statement stmt = conn.createStatement();
			//System.out.println("getcontacttttt:");
			 String sql = "SELECT Uname,\r\n" + 
			 		"Usex,\r\n" + 
			 		"Ubirthday,\r\n" + 
			 		"Uaddress ,\r\n" + 
			 		"Utel_no ,\r\n" + 
			 		"Upost  ,\r\n" + 
			 		"UQQ_no,\r\n" + 
			 		"Uemall \r\n" + 
			 		"from user where id = '"+uuuserid+"'"; 
	
	         ResultSet rs = stmt.executeQuery(sql);
	         if(rs.next())
	         {
	        	 //System.out.println("getUserInfo>>>>>>>>ResultSet<<<<<<<");
	         
	        	 result = "successed ";
	        	 result += uuuserid+" ";
	        	 result += rs.getString("Uname")+" ";
	        
	        	 result += rs.getString("Usex")+" ";
	        	 result += rs.getString("Ubirthday")+" ";
	        	 result += rs.getString("Uaddress")+" ";
	        	 result += rs.getString("Utel_no")+" ";
	        	 result += rs.getString("Upost")+" ";
	        	 
	        	 result += rs.getInt("UQQ_no")+" ";
	        	 result += rs.getString("Uemall");
	        	 /*if(rs.getString("Usex") == null)
        		 System.out.println("getUserInfoggggggggggggggggggggggggggggggggggggg"+rs.getString("Usex"));
*/
	         }
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
