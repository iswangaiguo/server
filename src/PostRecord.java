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


@WebServlet("/PostRecord")
public class PostRecord extends HttpServlet {

	private static final long serialVersionUID = 1L;
	 public PostRecord() {
		super();
	 }
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		String userId = requestParam.getString("userId");
		String sql1 = String.format("SELECT distinct postrecord.userId FROM postresume, postrecord WHERE postresume.userId = "
				+ "%s AND postrecord.postResumeId = postresume.postResumeId",userId);
		CommonResponse res = new CommonResponse();
		try {
			ResultSet result = DBUtil.query(sql1);
			while(result.next()) {
				String id = result.getString(1);
				ResultSet result1 = DBUtil.query("select * from personalresume where userId = " + id);
				HashMap<String,String> map;
				while(result1.next()) {
					map = new HashMap<>();
					map.put("name", result1.getString(2));
					map.put("school", result1.getString(3));
					map.put("professional", result1.getString(4));
					map.put("grade", result1.getString(5));       		
					map.put("time", result1.getString(6));
					map.put("phone", result1.getString(7));
					map.put("domicile", result1.getString(8));
					map.put("email", result1.getString(9));
					map.put("workexperience", result1.getString(10));
					res.addListItem(map);
					}
			}	
			res.setResCode("0");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		 String resStr = JSONObject.fromObject(res).toString();  
	        response.setCharacterEncoding("UTF-8");
	        System.out.println(resStr);  
	        response.getWriter().append(resStr).flush(); 
	}
	 
	 

}
