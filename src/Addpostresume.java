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


@WebServlet("/Addpostresume")
public class Addpostresume extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public Addpostresume() {
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
		String resumeId = requestParam.getString("resumeId");
		String userId = requestParam.getString("userId");
		String sql = String.format("insert into postrecord (postResumeId, userId) values ('%s','%s')",resumeId,userId);
		CommonResponse res = new CommonResponse();
		try {
			int result = DBUtil.other(sql);
			res.setResCode("0");
		} catch (SQLException e) {
			e.printStackTrace();
			res.setResCode("100");
		}
	}
	
	

}
