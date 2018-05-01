

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBUtil {
	    public static final String TABLE_USERINFO = "userinfo"; 
	    public static final String TABLE_RESUME = "personalResume";
	    public static final String TABLE_COMPANY = "companyinfo";
	    public static final String TABLE_POSTRESUME = "postresume";
	  
	    // 连接数据库  
	    public static Connection getConnect() {  
	        String url = "jdbc:mysql://localhost:3306/jobsapp"; // 数据库的Url  
	        Connection connecter = null;  
	        try {  
	            Class.forName("com.mysql.jdbc.Driver"); // java反射，固定写法  
	            connecter = (Connection) DriverManager.getConnection(url, "root", "123456");  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (SQLException e) {  
	            System.out.println("SQLException: " + e.getMessage());  
	            System.out.println("SQLState: " + e.getSQLState());  
	            System.out.println("VendorError: " + e.getErrorCode());  
	        }  
	        return connecter;  
	    }  
	    
	    //数据库查询 增加 删除
	    public static ResultSet query(String querySql) throws SQLException{
	    	Statement statement;
	    	statement = (Statement) getConnect().createStatement();
	    	return statement.executeQuery(querySql);
		}
	    public static int other(String querySql) throws SQLException{
	    	Statement statement;
	    	statement = (Statement) getConnect().createStatement();
	    	return statement.executeUpdate(querySql);
		}

	
	
}

		