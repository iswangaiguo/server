import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;




@WebServlet("/Jobhunter")
public class Jobhunter extends HttpServlet {

	private static final long serialVersionUID = 1L;
	 public Jobhunter() {
		super();
	 }
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		super.doGet(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader read =  request.getReader();
		StringBuilder sb = new StringBuilder();
		String line = null;
		while((line = read.readLine()) != null) {
			sb.append(line);
		}
		String req = sb.toString();
		System.out.println(req);
		JSONObject object = JSONObject.fromObject(req);
		JSONObject requestParam = object.getJSONObject("requestParam");
		String sql = "select DISTINCT * FROM personalresume,postwill WHERE personalresume.userId = postwill.userId";
		CommonResponse res = new CommonResponse();
		try {
			ResultSet result = DBUtil.query(sql);
			HashMap<String,String> map;	
			while(result.next()) {
				map = new HashMap<>();
				map.put("userId", result.getString("userId"));
				map.put("userName", result.getString("userName"));
				map.put("willname", result.getString("willname"));
				map.put("locat", result.getString("locat"));
				map.put("money", result.getString("money"));
				map.put("willdata", result.getString("willdate"));
				map.put("phone", result.getString("phone"));
				map.put("school", result.getString("school"));
				map.put("professional", result.getString("Professional"));
				map.put("workexperience", result.getString("workexperience"));
				res.addListItem(map);
			}
			res.setResCode("0");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		 String resStr = JSONObject.fromObject(res).toString();  
	        response.setCharacterEncoding("UTF-8");
	        System.out.println(resStr);  
	        response.getWriter().append(resStr).flush(); 
	}
	 
	 
	 
	 
	 

}
