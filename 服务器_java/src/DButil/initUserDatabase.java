package DButil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class initUserDatabase
{
	 final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost:3306/temp";

	// ���ݿ���û��������룬��Ҫ�����Լ�������
 	 static final String USER = "root";
	 static final String PASS = "carica";
	 final String userNotExit = "noexit";
	 final String userPwdWrong = "wrong";
	String loginresult = null;
	
	//���ؽ��1��ʾ�ɹ���0��ʾʧ��
	public int init(String userid) throws Exception
	{
		Connection conn = null;
	     
        Class.forName("com.mysql.jdbc.Driver");
        // ������
        System.out.println("�������ݿ�...");
        System.out.println(" ʵ����Statement��...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt = conn.createStatement();
        String databaseSql = "create database " + userid;
        int n = 0;
        n = stmt.executeUpdate(databaseSql);//���ؽ��1��ʾ�ɹ���0��ʾʧ��
        stmt.close();
        conn.close();
		return n;
	}
}
