package DButil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnect
{
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	final String preDB_URL = "jdbc:mysql://localhost:3306/";
	final String USER = "root";
	final String PASS = "carica";
	private Connection conn = null;
	
	 //连接s数据库
	 public Connection connect(String s) 
	 {
		 try
		{
			conn = DriverManager.getConnection(preDB_URL+s,USER,PASS);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return conn;
	 }
	 
	 public void close()
	 {
		 try
		{
			conn.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
