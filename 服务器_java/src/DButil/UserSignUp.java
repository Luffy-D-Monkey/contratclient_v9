package DButil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserSignUp
{
	//exist用户已存在,wrong信息格式错误，successed成功
	String result = "wrong";
	public UserSignUp(String msg)
	{
		
		  /*exsql = "2" + " " + sid + " " + spassword + " " + sname + " " + ssex + " " + sbir + " " + saddress + " " + stelno + " " + spost
                  + " " + sqqno + " " + semail;*/
		msg +=" =";
		String [] ss = msg.split(" ");
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/adminroot";
		final String USER = "root";
		final String PASS = "carica";
		Connection conn = null;
		 Statement stmt = null;
		 // 注册 JDBC 驱动
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("连接数据库...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		    
		    stmt = conn.createStatement();
            String sql,sql2;
            sql = "SELECT ID FROM User WHERE ID='";
            sql2 =sql+ss[1]+"'";
            ResultSet rs = stmt.executeQuery(sql2);
            if(rs.next())
            {
            	result = "exist";
            }
            else
            {
            	String presql ="insert into user(ID,Upassword ,Uname,Usex,Ubirthday,Uaddress,Utel_no,Upost,UQQ_no,Uemall) values(?,?,?,?,?,?,?,?,?,?)";  
            	 PreparedStatement preStmt =conn.prepareStatement(presql);
       		  
     			Class.forName("com.mysql.jdbc.Driver");
     			//"2"+" "+sid+" "+spassword+" "+sname+" "+ssex+" "+sbir+" "+saddress+" "+stelno+" "+spost
               // +" "+sqqno+" "+semail;
     			//不能为空应在客户端限制
     			
     			
     			preStmt.setString(1,ss[1].toString());  
     			
		        preStmt.setString(2,ss[2].toString());//或者：preStmt.setInt(1,值);  
     			
     			preStmt.setString(3,ss[3].toString());
		     
     			int i = 4,k=11;
		        if(++i<=k)
		        {
			       if(ss[4].toString().equals(""))
			        {
			        	preStmt.setString(4,null);
			        }
			        else
			        {
			        	preStmt.setString(4,ss[4].toString());
			        	System.out.println("性别<"+ss[4].toString()+">");
			        }
		        }
		        else
		        {
		        	preStmt.setString(4,null);
		        }
		      
		        if(++i<=k)
		       if(ss[5].toString().equals(""))
		        {
		        	preStmt.setString(5,null);
		        }
		        else
		        {
		        	preStmt.setString(5,ss[5].toString());
		        }
		        else
		        {
		        	preStmt.setString(5,null);
		        }

		        if(++i<=k)
		       if(ss[6].toString().equals(""))
		        {
		        	preStmt.setString(6,null);
		        }
		        else
		        {
		        	preStmt.setString(6,ss[6].toString());
		        }
		        else
		        {
		        	preStmt.setString(6,null);
		        }
		        
		        if(++i<=k)
		        	preStmt.setString(7,ss[7].toString());
		        else
		        {
		        	preStmt.setString(7,null);
		        }
		        
   
		        if(++i<=k)
		       if(ss[8].toString().equals(""))
		        {
		        	preStmt.setString(8,null);
		        }
		        else
		        {
		        	preStmt.setString(8,ss[8].toString());
		        }
		        else
		        {
		        	preStmt.setString(8,null);
		        }
		        
		       if(++i<=k)
		      if(ss[9].toString().equals(""))
		      {
		    	  preStmt.setString(9,null);
		      }
		      else
		      {
		    	  //int kk = Integer.parseInt(ss[9].toString());
		    	  preStmt.setString(9,ss[9].toString());
		      }
		        else
		        {
		        	preStmt.setString(9,null);
		        }
		        
		        
		        if(++i<=k)
		       if(ss[10].toString().equals(""))
		        {
		        	preStmt.setString(10,null);
		        }
		        else
		        {
		        	preStmt.setString(10,ss[10].toString());
		        }
		        else
		        {
		        	preStmt.setString(10,null);
		        }
		        

     		        int n=preStmt.executeUpdate();
     		        
     		        if(n == 1)
     		        {
     		        	result = "successed";
     		        }
     		        else
     		        {
     		        	result = "wrong";
     		        }
            }
            
		    conn.close();
		} catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;	
		}
        // 打开链接
  	}
	public String getSignupResult()
	{
		return result;
	}
}
