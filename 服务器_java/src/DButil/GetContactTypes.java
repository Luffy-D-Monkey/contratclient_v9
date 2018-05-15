package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GetContactTypes
{
	 ArrayList<String> resultList = new ArrayList<String>();
	Connection conn = null;
	
	
	public   ArrayList<String> getContactTypes(String uuuserid)
	{
		 //String command = "8 " +uuuid+" " + " " + tname + " " + tinfo;
		try
		{
			conn = new MysqlConnect().connect(uuuserid);
			Statement stmt = conn.createStatement();
			 String sql = "select Tname ,Remark  from Type"; 
	         ResultSet rs = stmt.executeQuery(sql);
	         while(rs.next())
	         {
	        	 //System.out.println("getUserInfo>>>>>>>>ResultSet<<<<<<<");
	        	 String coloumm = "";
	        	 //tno type remark
	        	 
	        	 coloumm += rs.getString("Tname")+" ";
	        	 coloumm += rs.getString("Remark");
	        	 System.out.println(coloumm);
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
