package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DeleteContactsType
{
	 ArrayList<String> resultList = new ArrayList<String>();

		Connection conn = null;
		public  DeleteContactsType()
		{	
		}
		
		public  ArrayList<String> deleteContactsType(String command)
		{
			String []ss = command.split(" ");
			//command = "10 "+uuuid+" "+ss[1](Tname);
			conn = new MysqlConnect().connect("u"+ss[1]);
			Statement stmt = null;
			try
			{
				stmt = conn.createStatement();
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sql = "delete from Type where Tname='"+ss[2]+"'"; 

	         try
			{
				if( stmt.executeUpdate(sql) == 1)
				 {
					 ArrayList<String> templist  = new GetContactTypes().getContactTypes("u"+ss[1]);
					 resultList.add("successed");
					 for(int i = 0;i<templist.size(); i++)
						 resultList.add(templist.get(i));
				 }
				 else
				 {
					 resultList.add("failed");
				 }
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	       //successed/Types    failed
	         for(int i = 0; i<resultList.size(); i++)
	        	 System.out.println("rrrrrrrrrrrrrrrrrrrrrr"+resultList.get(i));
	 		return resultList;

		}
}
