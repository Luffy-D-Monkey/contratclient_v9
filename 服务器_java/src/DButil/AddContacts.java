package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddContacts
{
	int returnn = 0;
	Connection conn = null; 
	
	
	public String addContacts(String command)
	{
		command +=" =";
		String []ss = command.split(" ");
		System.out.println("aaaaaaaaaaaaaaaaaaaaaddaddaddcontacts--"+command+"--"+command.length());
		
		String result = "wrong";
		
		System.out.print("sssssss[][][][][][]siz->"+ss.length);
		conn = new MysqlConnect().connect("u"+ss[1]);
		Statement stmt = null;
        try
		{
        	stmt = conn.createStatement();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
       /*   String command = "11 "+uuuid+" "+smoblie+" "+sname+" "+ssex
        +" "+sbir+" "+saddress+" "+stelno+" "+spost+" "
        +sqqno+" "+semail+" "+typename;*/
		/* String sql = "SELECT * from CONTACTS where Cmobile_no = '"+ss[2]+"'"; 
	
	         ResultSet rs = null;
			try
			{
				rs = stmt.executeQuery(sql);
			} catch (SQLException e2)
			{
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	         try
			{
				if(rs.next())
				 {
					rs.close();
					stmt.close();
					conn.close();
					result = "failed";
					 return "failed";
				 }
				else
				{
					
					rs.close();*/
        
    	
        /*   String command = "11 "+uuuid+" "+smoblie+" "+sname+" "+ssex
         +" "+sbir+" "+saddress+" "+stelno+" "+spost+" "
         +sqqno+" "+semail+" "+typename;*/
					for(int i = 2; i<=11; i++)
					{
						System.out.print(i+"="+ss[i]+"-->");
						if(ss[i].equals("null") || ss[i].equals(""))
						{
							
							ss[i] = "null";
						}
						else
						{

							ss[i] = "'"+ss[i]+"'";
						}
						System.out.println(ss[i]);
					}
					   /*   String command = "11 "+uuuid+" "+smoblie+" "+sname+" "+ssex
			         +" "+sbir+" "+saddress+" "+stelno+" "+spost+" "
			         +sqqno+" "+semail+" "+typename;*/
					String updatesql = "";
					updatesql = "INSERT INTO CONTACTS SET Cmobile_no = "+ss[2]
							+",Cname  = "+ss[3]
							+",Csex  = "+ss[4]
							+",Cbirthday  = "+ss[5]
							+",Caddress  = "+ss[6]
							+",Ctel_no = "+ss[7]
							+",Cpost = "+ss[8]
							+",CQQ_no = "+ss[9]
							+",Cemall = "+ss[10]
							+",Type = "+ss[11];
					
					int n = 0;
					try
					{
						if( 1 == stmt.executeUpdate(updatesql))
							result = "successed";
							
					} catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			
	         return result;
									
				/*	if(ss[9].equals("null") || ss[9].equals(""))
					{
							ss[9] = "null";
							updatesql += ",LQQ_no = "+ss[9];
					}
					else
					{
						updatesql += ",LQQ_no = "+Integer.parseInt(ss[9]);
					}
					updatesql +=",Lemall = "+ss[10];
					
					
					if(!(ss[11].equals(null))&&!(ss[11].equals("null")))//类别不为空，加多类别字段
					{
						updatesql +=",Type = "+ss[11];
						 String command = "11 "+uuuid+" "+smoblie+" "+sname+" "+ssex
						 * 					0		1			2			3		4
					             +" "+sbir+" "+saddress+" "+stelno+" "+spost+" "
					             		5		6				7			8		
					             +sqqno+" "+semail+" "+stype;
					             	9			10		11
					             	
					             	Lno int auto_increment primary key,
			Lname char(8) not null,
			Lsex char(1) ,
			Lbirthday DATE,
			Laddress varchar(40),
			Ltel_no char(12),
			Lpost char(9),
			Lmobile_no char(11) not null,
			LQQ_no int ,
			Lemall varchar(20) ,
			Tno char(6) not null DEFAULT 'ndf',
			Type char(8) not null DEFAULT 'notde',
					      
							
					}
					else
					{
						updatesql +=",Type = "+"'notde'";
					}*/
				
	
	}
}
