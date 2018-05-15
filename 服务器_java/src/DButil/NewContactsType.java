package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NewContactsType
{

	
	 //String command = "8 " +uuuid+" "+ tno + " " + tname + " " + tinfo;
	
	public String newContactsType(String s)
	{
		
		String result = "failed";
		//  String command = "8 " +uuuid+" "+ tno + " " + tname + " " + tinfo;
		//                    0     1          2           3              4
		s +=" =";
		String []ss = s.split(" ");
		Connection conn = null;
        Statement stmt = null;
        conn =  new MysqlConnect().connect("u"+ss[1]);
        System.out.println("1");
        try
		{
			stmt = conn.createStatement();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("2");
        String sql;
        //Tno char(6) not null PRIMARY KEY,
        //Type char(8) not null ,
        //Remark varchar(20)
        
        // String command = "8 " +uuuid+" " + " " + tname + " " + tinfo;
        sql = "SELECT Tname FROM Type WHERE Tname='"+ss[2]+"'"; 
        ResultSet rs = null;
       
        try
		{
        	rs = stmt.executeQuery(sql);
        	if(rs.next())
			{	
					result = "typenameHaveExit";
					rs.close();
					conn.close();
					 return result;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    //  String command = "8 " +uuuid+" "+ tname + " " + tinfo;
    //                    0     1            2          3              
       // String insertSql = "INSERT INTO Type SET Tno = 'ndf',Type = 'notde'" ;
        System.out.println("nnnnnnnnnnnnewewewewewewnewnewnewnenw"+s);
        int size = 4;
        for(int i = 2; i<size; i++)
        	
        {
        	System.out.println("tyty>"+ss[i]);
        	if(ss[i].equals("null") || ss[i].equals(""))
				ss[i] = "null";
        	else
        	{
        		ss[i] = "'"+ss[i]+"'";
        	}
        	
        }
        String updatesql ="";
        
        updatesql = "INSERT INTO Type SET Tname = "+ss[2]+","
        		+ "Remark = "+ss[3]+"";
        System.out.println("uuuuuuuuuuuuussssssssss"+updatesql);
        int n = 0;
        try
		{
			if(stmt.executeUpdate(updatesql) == 1)
				result =  "successed";
			
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
        try
		{
			if(!(rs.isClosed()))
					rs.close();
		
			conn.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      //failed∏Ò Ω¥ÌŒÛ  typenoHaveExit    typenameHaveExit  successed
       
        return result;
	}
}
