import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;


@WebServlet("/Mypostwill")
public class Mypostwill extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public Mypostwill() {
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
		String userId = requestParam.getString("userId");
		Long l = System.currentTimeMillis();
		Date time = new Date(l);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sql = String.format("insert into postwill (userId,willname,locat,money,willdate)"
				+ "values ('%s','%s','%s','%s','%s')",requestParam.get("userId"),requestParam.get("resumewill"),
				requestParam.get("locatwill"),requestParam.get("moneywill"),time);
		CommonResponse res = new CommonResponse(); 
		try {
			int result = DBUtil.other(sql);
			res.setResult("0","发布成功");
		} catch (SQLException e) {
			e.printStackTrace();
			res.setResCode("100");
		}
		 String resStr = JSONObject.fromObject(res).toString();  
	        response.setCharacterEncoding("UTF-8");
	        System.out.println(resStr);  
	        response.getWriter().append(resStr).flush(); 
		
	}
	
	

}
