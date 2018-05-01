

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
 * Servlet implementation class PersonalResume
 */
@WebServlet("/PersonalResume")
public class PersonalResume extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public PersonalResume() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader read = request.getReader();  
        StringBuilder sb = new StringBuilder();  
        String line = null;  
        while ((line = read.readLine()) != null) {  
            sb.append(line);  
        }  
        String req = sb.toString();  
        System.out.println(req);  
        
        JSONObject object = JSONObject.fromObject(req); 
        String requestCode = object.getString("requestCode");
        JSONObject requestParam = object.getJSONObject("requestParam");
        CommonResponse res = new CommonResponse();
        
        switch (requestCode) {
        	case "8":
        		updatedata(requestParam ,res);
        		break;
        	case "9":
        		initdata(requestParam,res);
        		break;
        }
        String resStr = JSONObject.fromObject(res).toString();  
        response.setCharacterEncoding("UTF-8");
        System.out.println(resStr);  
        response.getWriter().append(resStr).flush();  

	}
	
	 private void initdata(JSONObject requestParam,CommonResponse res) {
		 String sql = String.format("SELECT * FROM %s WHERE userId ='%s'",   
                 DBUtil.TABLE_RESUME,   
                 requestParam.getString("userId")); 
       	try {
				ResultSet result = DBUtil.query(sql);
				if(result.next()) {
					res.getProperty().put("name", result.getString(2));
	           		res.getProperty().put("school", result.getString(3));
	           		res.getProperty().put("professional", result.getString(4));
	           		res.getProperty().put("grade", result.getString(5));       		
	           		res.getProperty().put("time", result.getString(6));
	           		res.getProperty().put("phone", result.getString(7));
	           		res.getProperty().put("domicile", result.getString(8));
	           		res.getProperty().put("email", result.getString(9));
	           		res.getProperty().put("workexperience", result.getString(10));
	           		res.setResult("0", "读取数据成功");      			
				} else {
					res.setResult("11", "简历未创建");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
     	
     }
	 
	 private void updatedata(JSONObject requestParam, CommonResponse res) {
		String update = String.format("REPLACE INTO personalresume (userId,userName,school,Professional,record,gradetime,phone,"
				+ "domicile,email,workexperience) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
				requestParam.getString("userId"),requestParam.getString("name"),requestParam.getString("school"),requestParam.getString("professional"),
				requestParam.getString("name"),requestParam.getString("time"),requestParam.getString("phone"),
				requestParam.getString("domicile"),requestParam.getString("email"),requestParam.getString("workexperience")
				); 
		System.out.println(update);
	 	try {
			ResultSet result = DBUtil.query(update);
			res.setResult("0", "保存成功");
	 	} catch(SQLException e) {
	 		e.printStackTrace();
	 		res.setResult("12", "保存失败");
	 	}
	 }
}
