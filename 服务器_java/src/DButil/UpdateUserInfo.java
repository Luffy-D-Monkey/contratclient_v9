package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UpdateUserInfo
{
	

	Connection conn = null;
	
	public  String updateUserInfo(String updatecommand)
	{
		//System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		String result = "failed ";
		updatecommand += " =";
		String []ss = updatecommand.split(" ");
		System.out.println("updatecommand"+updatecommand);
		for(int i = 3; i<=9; i++)
		{
			System.out.print(i+"="+ss[i]+"-->");
			
			if(ss[i].equals("null") || ss[i].equals(""))
				ss[i] = "null";
			else
			{
							
				ss[i] = "'"+ss[i]+"'";
			}
			System.out.println(ss[i]);
		}
		//    String command = "6 "+uuuid+" "+upname+" "+upsex+" "+upbir+" "+upaddress+" "+uptelno+" "+uppost+" "+upqqno+" "+upemail;
//								0	  1			2			3		4			5			6				7		8			9			
		String updatesql = "UPDATE user SET Uname = '"+ss[2]+"',\r\n" + 
				"Usex = "+ ss[3]+",\r\n"+
				"Ubirthday = "+ ss[4]+",\r\n" + 
				"Uaddress = "+ss[5]+",\r\n" + 
				"Utel_no= "+ss[6]+",\r\n" + 
				"Upost = "+ss[7]+",\r\n" + 
				"UQQ_no ="+ss[8]+",\r\n"+
				"Uemall = "+ss[9]+"\r\n" + 
				"WHERE id = '"+ss[1]+"'";
		
		System.out.println("sql-->"+updatesql);
		//System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		//updatecommand = "6 "+uuuid+" "+upname+" "+upsex+" "+upbir+" 
		//-----------------0-----1----------2----------3---------4-----
		//"+upaddress+" "+uptelno+" "+uppost+" "+upqqno+" "+upemail;
        //------5------------6-----------7---------8----------9-------
		try
		{
			conn = new MysqlConnect().connect("adminroot");
			Statement stmt = conn.createStatement();
			int n=stmt.executeUpdate(updatesql);
			//System.out.println("getcontacttttt:");
			
			
			if(n == 1)
		        {
		        	result = "successed ";
		        }
			
		       
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//·µ»Ø½á¹û£ºresult+uuuid+" "+upname+" "+upsex+" "+upbir+" 
		//----    --0-----1----------2----------3---------4-----
		//"+upaddress+" "+uptelno+" "+uppost+" "+upqqno+" "+upemail;
		return result;
	}
	
	
}
