
package DButil;
import java.sql.*;
import DButil.MysqlConnect;

/*
 * 初始化用户的下的所有表
*/
public class initUserTables
{
	public void initUserTables()
	{
		
	}

	String loginresult = null;
	
	public int init(String userid)  
	{
		int returnn = 0;
		Connection conn = null; 		
        try
		{

        // 打开链接
        System.out.println("连接数据库...");
        System.out.println(" 实例化Statement对...");
        conn = new MysqlConnect().connect(userid);
        Statement stmt = conn.createStatement();

        //创建类型表
       String sql = "create table TYPE(Tno int not null auto_increment, Tname char(20) not null,Remark varchar(20),primary key(Tno))";
       
       returnn += stmt.executeUpdate(sql);
       
       sql =  "insert into type set tname = 'undefined',remark='not define'";
       returnn += stmt.executeUpdate(sql);

       //修改类型表
       sql = "alter table TYPE engine=innodb";
       returnn += stmt.executeUpdate(sql);

       sql  = "alter table TYPE add unique(Tname)";
       returnn += stmt.executeUpdate(sql);
   
        //联系人表
        String createSql = "create table CONTACTS(\r\n" + 
        		"Cno int not null auto_increment,\r\n" + 
        		"Type char(20),\r\n" + 
        		"FOREIGN KEY (Type) REFERENCES TYPE (Tname) ON DELETE SET NULL ON UPDATE CASCADE,\r\n" + 
        		"Cname varchar(20) not null,\r\n" + 
        		"Csex enum('男','女'),\r\n" + 
        		"Cbirthday date,Caddress varchar(40),\r\n" + 
        		"Ctel_no varchar(15),\r\n" + 
        		"Cpost char(6),\r\n" + 
        		"CQQ_no varchar(15),\r\n" + 
        		"Cmobile_no char(11) not null,\r\n" + 
        		"Cemall varchar(20),\r\n" + 
        		"primary KEY(Cno),\r\n" + 
        		"unique index contacts_cno(Cno ASC))ENGINE=InnoDB DEFAULT CHARSET=utf8;\r\n" + 
        		"";
        returnn += stmt.executeUpdate(createSql);
        System.out.println("createbable");
        //设置默认值
        String usql = "alter table contacts alter column type set default 'undefined';";
        returnn += stmt.executeUpdate(usql);
        

        String tigsql = "create trigger type_update after update on type\r\n" + 
        		"for each row begin\r\n" + 
        		"update contacts set type=new.tname where type=old.tname;\r\n" + 
        		"end\r\n" + 
        		"";
        returnn += stmt.executeUpdate(tigsql);
        
        tigsql = "create trigger type_delete after delete on type\r\n" + 
        		"for each row begin\r\n" + 
        		"update contacts set contacts.type='Undefined' where contacts.type=old.tname;\r\n" + 
        		"end ;\r\n" + 
        		"\r\n" + 
        		"";
        returnn += stmt.executeUpdate(tigsql);
        
        stmt.close();
        
        conn.close();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnn;
	}
}
