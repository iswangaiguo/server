

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("不支持GET方法;");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader read = request.getReader();  
        StringBuilder sb = new StringBuilder();  
        String line = null;  
        while ((line = read.readLine()) != null) {  
            sb.append(line);  
        }  
        String req = sb.toString();  
System.out.println(req);  
          
        // 第一步：获取 客户端 发来的请求，恢复其Json格式——>需要客户端发请求时也封装成Json格式  
        JSONObject object = JSONObject.fromObject(req);  
        // requestCode暂时用不上  
        // 注意下边用到的2个字段名称requestCode、requestParam要和客户端CommonRequest封装时候的名字一致  
        String requestCode = object.getString("requestCode");  
        JSONObject requestParam = object.getJSONObject("requestParam");
  
        String sql = String.format("SELECT * FROM %s WHERE userId ='%s'",   
                DBUtil.TABLE_USERINFO,   
                requestParam.getString("userId")); 
        String addsql = String.format("INSERT INTO %s (userId, userPassword,userType) values ('%s', '%s', '%s')",
        		DBUtil.TABLE_USERINFO,
        		requestParam.getString("userId"),
        		requestParam.getString("password"),
        		requestParam.getString("type"));
System.out.println(addsql);  
		//自定义的结果信息类  
		CommonResponse res = new CommonResponse(); 
		try {
			ResultSet result = DBUtil.query(sql);
			if(result.next()) {
				res.setResult("101","用户名已存在");	
			} else {
				DBUtil.other(addsql);
				res.setResult("0","注册成功");
			}		
		} catch (SQLException e) {
			res.setResult("300", "数据库查询错误");  
            e.printStackTrace();  
			
		}
		 String resStr = JSONObject.fromObject(res).toString();  
	        response.setCharacterEncoding("UTF-8");
	        System.out.println(resStr);  
	        response.getWriter().append(resStr).flush(); 
	}

}
