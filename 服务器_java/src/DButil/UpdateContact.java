package DButil;

import java.sql.Connection;
import java.sql.Statement;

public class UpdateContact
{

	Connection conn = null;
	
	public  String updateContact(String command)
	{
		//System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		 /* String ucommand = 
		    ucommand = "14 "+uuuid+" "+smoblie+" "+sname+" "+ssex+" "+sbir+" "+saddress+" "+stelno+" "+spost+" "+sqqno+" "+semail+" "+styyyy;
		 				0 		1		2			3			4		5			6			7			8		9		10			11		
		 */
		String updatecommand = command +" =";
		String result = "failed ";
		
		String []ss = updatecommand.split(" ");
		System.out.println("updatecommand"+updatecommand);
		for(int i = 2; i<=11; i++)
		{
			System.out.print(i+"="+ss[i]+"-->");

			if(ss[i].equals("null") || ss[i].equals(""))
				ss[i] = "null";
			else
			{
				ss[i] = "'"+ss[i]+"'";
			}
	
		}
		//	0 		1		2			3			4		5		6			7			8		9		10			11		

/*		String ucommand = "14 ";
    ucommand += uuuid+" "+smoblie+" "+sname+" "+ssex+" "+sbir+" "+saddress+" "+stelno+" "+spost+" "+sqqno+" "+semail+" "+styyyy;
   		1			2		3			4		5			6			7		8			9			10		11			12
   */
		String updatesql = "UPDATE contacts SET Cname = "+ss[3]+",\r\n" + 
				"Csex = "+ ss[4]+",\r\n"+
				"Cmobile_no = "+ ss[2]+",\r\n"+
				"Cbirthday = "+ ss[5]+",\r\n" + 
				"Caddress = "+ss[6]+",\r\n" + 
				"Ctel_no= "+ss[7]+",\r\n" + 
				"Cpost = "+ss[8]+",\r\n" + 
				"CQQ_no = "+ss[9]+",\r\n" +
				"Cemall = "+ss[10]+",\r\n"  + 
				"Type = "+ss[11]+"\r\n"+
				"WHERE Cmobile_no = "+ss[2];
		System.out.println("sql-->"+updatesql);
		try
		{
			conn = new MysqlConnect().connect("u"+ss[1]);
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
