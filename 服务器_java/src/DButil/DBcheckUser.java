
package DButil;
import java.sql.*;

/*//���������admincontract���ݿ��users����
//����checklogin(string user��string password)����û������û�����
//���ڲ�������ȷ����÷��غ��������û���user
//�������򷵻ء�noexit��
//��������������򷵻ء�wrong��
//JDBC �����������ݿ� URL
*/
public class DBcheckUser
{
	public void DBcheckUser()
	{
		
	}


	// ���ݿ���û��������룬��Ҫ�����Լ�������
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
	            // ע�� JDBC ����
	            //Class.forName("com.mysql.jdbc.Driver");
	            // ������
	            System.out.println("�������ݿ�...");
	            conn =  new MysqlConnect().connect("adminroot");
	        
	            // ִ�в�ѯ
	            System.out.println(" ʵ����Statement��...");
	            stmt = conn.createStatement();
	            String sql,sql2;
	            sql = "SELECT ID, Upassword  FROM user WHERE ID='";
	            sql2 =sql+user+"'";
	            ResultSet rs = stmt.executeQuery(sql2);
        
	            
	            if(rs.next())
	            {
	            	if((rs.getString("Upassword").equals(password)))//������ȷ)
	            	{
	            		loginresult = user;
	            	}
	            	else 
	            	{
	            		loginresult = userPwdWrong;//�������
	            	}
	            }
	            else//�˻�������
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
	            // ���� JDBC ����
	            se.printStackTrace();
	        }catch(Exception e){
	            // ���� Class.forName ����
	            e.printStackTrace();
	        }finally{
	            // �ر���Դ
	            try{
	                if(stmt!=null) stmt.close();
	            }catch(SQLException se2){
	            }// ʲô������
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
