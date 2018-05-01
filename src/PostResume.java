import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

@WebServlet("/PostResume")
public class PostResume extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public PostResume() {
		super();
	 }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		super.doGet(request,response);
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
		Long l = System.currentTimeMillis();
		Date time = new Date(l);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sql = String.format("insert into postresume (userId,postName,workLocation,record,resumeNumber,jobDescriptions,posttime)"
				+ "values ('%s','%s','%s','%s','%s','%s','%s')",
				requestParam.getString("userId"),
				requestParam.getString("postname"),
				requestParam.getString("worklocation"),
				requestParam.getString("education"),
				requestParam.getString("postnum"),
				requestParam.getString("workintroduction"),
				sdf.format(time));	
		System.out.println(sql);
		CommonResponse res = new CommonResponse(); 
		try {
			DBUtil.other(sql);
			res.setResult("0","发布成功");
		} catch (SQLException e) {
			e.printStackTrace();
			res.setResult("400", "发布失败");
		}
		 String resStr = JSONObject.fromObject(res).toString();  
	        response.setCharacterEncoding("UTF-8");
	        System.out.println(resStr);  
	        response.getWriter().append(resStr).flush(); 
		
	}
	
	
	
}
