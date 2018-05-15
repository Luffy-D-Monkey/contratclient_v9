
package DButil;
import java.sql.*;

/*//密码表保存在admincontract数据库的users表中
//调用checklogin(string user，string password)检查用户名和用户密码
//存在并密码正确则调用返回函数返回用户名user
//不存在则返回‘noexit’
//存在且密码错误则返回‘wrong’
//JDBC 驱动名及数据库 URL
*/
public class DBcheckUser
{
	public void DBcheckUser()
	{
		
	}


	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "carica";
	static final String userNotExit = "noexit";
	static final String userPwdWrong = "wrong";
	String loginresult = null;
	
	public String checklogin(String user, String password)
	{
		 Connection conn = null;
	        Statement stmt = null;
	        try{
	            // 注册 JDBC 驱动
	            //Class.forName("com.mysql.jdbc.Driver");
	            // 打开链接
	            System.out.println("连接数据库...");
	            conn =  new MysqlConnect().connect("adminroot");
	        
	            // 执行查询
	            System.out.println(" 实例化Statement对...");
	            stmt = conn.createStatement();
	            String sql,sql2;
	            sql = "SELECT ID, Upassword  FROM user WHERE ID='";
	            sql2 =sql+user+"'";
	            ResultSet rs = stmt.executeQuery(sql2);
        
	            
	            if(rs.next())
	            {
	            	if((rs.getString("Upassword").equals(password)))//密码正确)
	            	{
	            		loginresult = user;
	            	}
	            	else 
	            	{
	            		loginresult = userPwdWrong;//密码错误
	            	}
	            }
	            else//账户不存在
	            {
            		loginresult = userNotExit;
	            }

	            
	            
	            rs.close();
	            //stmt.executeUpdate(sq3);
	            //test
	           /* sql = "create database woshishui";
	            stmt.executeUpdate(sql);
*/	            stmt.close();
	            
	            
	           
	            
	            
	            conn.close();
	            
	        }catch(SQLException se){
	            // 处理 JDBC 错误
	            se.printStackTrace();
	        }catch(Exception e){
	            // 处理 Class.forName 错误
	            e.printStackTrace();
	        }finally{
	            // 关闭资源
	            try{
	                if(stmt!=null) stmt.close();
	            }catch(SQLException se2){
	            }// 什么都不做
	            try{
	                if(conn!=null) conn.close();
	            }catch(SQLException se){
	                se.printStackTrace();
	            }
	        }
	        System.out.println("Goodbye!");
		return loginresult;
		
	}
}
