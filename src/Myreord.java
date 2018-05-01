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


@WebServlet("/Myreord")
public class Myreord extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public Myreord() {
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
		String sql = String.format("SELECT DISTINCT postresume.*,companyinfo.companyName,companyinfo.companyType FROM postrecord,postresume,companyinfo WHERE postrecord.userId = '%s' AND postresume.postResumeId = postrecord.postResumeId AND postresume.userId = companyinfo.userId", userId);
		CommonResponse res = new CommonResponse();
		HashMap<String,String> map;
		try {
			ResultSet result = DBUtil.query(sql);
			while(result.next()) {
				map = new HashMap();
				map.put("title", result.getString("postName"));
				map.put("worklocation", result.getString("workLocation"));
				map.put("companyType", result.getString("companyType"));
				map.put("number", result.getString("resumeNumber"));
				map.put("companyName",result.getString("companyName"));
				res.addListItem(map);
			}
			res.setResCode("0");
		} catch(SQLException e) {
			e.printStackTrace();
			res.setResult("100", "≤È—Ø ß∞‹");
		}
		String resStr = JSONObject.fromObject(res).toString();  
        response.setCharacterEncoding("UTF-8");
        System.out.println(resStr);  
        response.getWriter().append(resStr).flush(); 
		
	}
    
    
}
	
	


