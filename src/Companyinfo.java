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




@WebServlet("/Companyinfo")

public class Companyinfo extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public Companyinfo() {
       
    	super();
 
    }



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("不支持GET方法;");
	}

	@Override
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
	        case "10":
	    		initdata(requestParam,res);
	    		break;
        	case "11":
        		updatedata(requestParam ,res);
        		break;
        	
        }
        String resStr = JSONObject.fromObject(res).toString();  
        response.setCharacterEncoding("UTF-8");
        System.out.println(resStr);  
        response.getWriter().append(resStr).flush(); 
        
        
	}
	private void initdata(JSONObject requestParam,CommonResponse res) {
		String sql = String.format("SELECT * FROM %s WHERE userId ='%s'",   
                DBUtil.TABLE_COMPANY,              
                requestParam.getString("userId"));
		 System.out.println(sql);
		try {
			ResultSet result = DBUtil.query(sql);
			if(result.next()) {
				res.getProperty().put("companyName", result.getString(2));
           		res.getProperty().put("Location", result.getString(3));
           		res.getProperty().put("phone", result.getString(4));
           		res.getProperty().put("companyType", result.getString(5));       		
           		res.getProperty().put("companyintroduce", result.getString(6));
           		res.setResult("0", "读取数据成功");      			
	
			} else {
				res.setResult("11", "信息未创建");
			}
		
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	private void updatedata(JSONObject requestParam,CommonResponse res) {
		String update = String.format("REPLACE INTO companyinfo (userId,companyName,Location,phone,companyType,companyintroduce) VALUES"
				+ " ('%s','%s','%s','%s','%s','%s')", requestParam.getString("userId"),requestParam.getString("companyName"),
				requestParam.getString("companyLocate"),requestParam.getString("phone"),
				requestParam.getString("companyType"),requestParam.getString("companyIntroduction"));
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
	
	
	

