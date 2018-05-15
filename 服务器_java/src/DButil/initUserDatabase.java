package DButil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class initUserDatabase
{
	 final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost:3306/temp";

	// 数据库的用户名与密码，需要根据自己的设置
 	 static final String USER = "root";
	 static final String PASS = "carica";
	 final String userNotExit = "noexit";
	 final String userPwdWrong = "wrong";
	String loginresult = null;
	
	//返回结果1表示成功，0表示失败
	public int init(String userid) throws Exception
	{
		Connection conn = null;
	     
        Class.forName("com.mysql.jdbc.Driver");
        // 打开链接
        System.out.println("连接数据库...");
        System.out.println(" 实例化Statement对...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt = conn.createStatement();
        String databaseSql = "create database " + userid;
        int n = 0;
        n = stmt.executeUpdate(databaseSql);//返回结果1表示成功，0表示失败
        stmt.close();
        conn.close();
		return n;
	}
}
