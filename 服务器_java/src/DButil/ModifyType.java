package DButil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ModifyType
{int returnn = 0;
Connection conn = null; 
	                             
	public ArrayList<String> modifyType(String command)
	{
		 ArrayList<String> resultList = new ArrayList<String>();

		command +=" =";
		String []ss = command.split(" ");
		// String command = "12 "+uuuid+" "+typename.getText()+" "+typeinfo.getText();

	    //                    0      1              2                 3         
		
		
		conn = new MysqlConnect().connect("u"+ss[1]);
		Statement stmt = null;
		
		try
		{
			stmt = conn.createStatement();
			
			// String command = "12 "+uuuid+" "+ pretypename+" " +typename.getText()+" "+typeinfo.getText();

		    //                    0      1              2                 3         
			
			
			String sql = "UPDATE Type SET Tname = '"+ss[3]+"', Remark = '"+ss[4]+"' WHERE Tname = '"+ss[2]+"'"; 
			if(stmt.executeUpdate(sql) == 0)
			{
				resultList.add("failed");
			}
			else
			{
				resultList.add("successed");
				 ArrayList<String> templist  = new GetContactTypes().getContactTypes("u"+ss[1]);
				 for(int i = 0;i<templist.size(); i++)
					 resultList.add(templist.get(i));
			}
				
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//failed  successed/adafadf
		return resultList;
	
	}
}
