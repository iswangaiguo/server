

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
		System.out.println("��֧��GET����;");
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
          
        // ��һ������ȡ �ͻ��� ���������󣬻ָ���Json��ʽ����>��Ҫ�ͻ��˷�����ʱҲ��װ��Json��ʽ  
        JSONObject object = JSONObject.fromObject(req);  
        // requestCode��ʱ�ò���  
        // ע���±��õ���2���ֶ�����requestCode��requestParamҪ�Ϳͻ���CommonRequest��װʱ�������һ��  
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
		//�Զ���Ľ����Ϣ��  
		CommonResponse res = new CommonResponse(); 
		try {
			ResultSet result = DBUtil.query(sql);
			if(result.next()) {
				res.setResult("101","�û����Ѵ���");	
			} else {
				DBUtil.other(addsql);
				res.setResult("0","ע��ɹ�");
			}		
		} catch (SQLException e) {
			res.setResult("300", "���ݿ��ѯ����");  
            e.printStackTrace();  
			
		}
		 String resStr = JSONObject.fromObject(res).toString();  
	        response.setCharacterEncoding("UTF-8");
	        System.out.println(resStr);  
	        response.getWriter().append(resStr).flush(); 
	}

}
