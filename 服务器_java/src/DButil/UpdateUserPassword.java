package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateUserPassword
{
	public String updateUserPassword(String s)
	{
		String result = "";
		//s�ĸ�ʽ��  "7 " + uuuuid + " " + op + " " + np;���ϸ�ĸ�ʽ�����ٲ�©��
		String []ss = s.split(" ");
		Connection conn = null;
        Statement stmt = null;
        conn =  new MysqlConnect().connect("adminroot");
        //System.out.println("1");
        try
		{
			stmt = conn.createStatement();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("2");
        String sql,sql2;
        sql = "SELECT ID, Upassword  FROM user WHERE ID='";
        sql2 =sql+ss[1]+"'";
        ResultSet rs = null;
        System.out.println("3");
        try
		{
        	rs = stmt.executeQuery(sql2);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try
		{
			if(rs.next())
			{
				if((rs.getString("Upassword").equals(ss[2])))//������ȷ)
				{
					result = "failed";
					rs.close();
					String updatesql = "UPDATE user SET Upassword  ='"
							+ss[3]+ "'"
									+ "WHERE id = '"+ss[1]+"'";
					if(stmt.executeUpdate(updatesql) == 1)
						result = "successed";				
				}
				else 
				{
					result = "wrong";//�������
				}
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
      
        //wrong������� failed��ʽ����  successed�ɹ�
        return result;
	}

}
