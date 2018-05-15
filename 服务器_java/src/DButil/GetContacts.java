package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetContacts
{
    ArrayList<String> resultList = new ArrayList<String>();

	Connection conn = null;
	public  GetContacts()
	{
		
	}
	
	public  ArrayList<String> getContacts(String uuuserid)
	{
		try
		{
			conn = new MysqlConnect().connect(uuuserid);
			Statement stmt = conn.createStatement();
			//System.out.println("getcontacttttt:");
			 String sql = "select * from CONTACTS"; 
	
	         ResultSet rs = stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 String coloumm = "";
	        	 coloumm += rs.getString("Cname")+" ";
	        	 coloumm += rs.getString("Csex")+" ";
	        	 coloumm += rs.getString("Cbirthday")+" ";
	        	 coloumm += rs.getString("Caddress")+" ";
	        	 coloumm += rs.getString("Ctel_no")+" ";
	        	 coloumm += rs.getString("Cpost")+" ";
	        	 coloumm += rs.getString("Cmobile_no")+" ";
	        	 coloumm += rs.getInt("CQQ_no")+" ";
	        	 coloumm += rs.getString("Cemall")+" ";	    
	        	 coloumm += rs.getString("Type")+" ";	      
	        	 resultList.add(coloumm);
	         }
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
		
	}
	
	
}
