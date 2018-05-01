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

@WebServlet("/PostOccupation")

public class PostOccupation extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public PostOccupation() {
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
		String sql = "SELECT * from companyinfo,postresume where companyinfo.userId = postresume.userId";
		CommonResponse res = new CommonResponse();  
		try {
			ResultSet result = DBUtil.query(sql);
			while(result.next()) {
				HashMap<String,String> map = new HashMap<>();
				map.put("postName", result.getString("postName"));
				map.put("workLocation", result.getString("workLocation"));
				map.put("record", result.getString("record"));
				map.put("resumeNumber", result.getString("resumeNumber"));
				map.put("jobDescriptions", result.getString("jobDescriptions"));
				map.put("posttime", result.getString("posttime"));
				map.put("companyName", result.getString("companyName"));
				map.put("companyType", result.getString("companyType"));
				map.put("resumeId", result.getString("postResumeId"));
				map.put("userId", result.getString("userId"));
				res.addListItem(map);
				
			}
			res.setResCode("0");
		} catch (SQLException e) {
			res.setResult("300", "Êý¾Ý¿â²éÑ¯´íÎó");  
			e.printStackTrace();
		}
		 String resStr = JSONObject.fromObject(res).toString();  
	        response.setCharacterEncoding("UTF-8");
	        System.out.println(resStr);  
	        response.getWriter().append(resStr).flush(); 
	}
	
	

}
