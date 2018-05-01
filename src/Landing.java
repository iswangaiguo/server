

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class Landing
 */
@WebServlet("/Landing")
public class Landing extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Landing() {
    	super();
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
          
  
        // �ڶ�������Jsonת��Ϊ������ݽṹ����ʹ�û���ֱ��ʹ�ã��˴�ֱ��ʹ�ã�������ҵ�������ɽ��  
        // ƴ��SQL��ѯ���  
        String sql = String.format("SELECT * FROM %s WHERE userId ='%s'",   
                DBUtil.TABLE_USERINFO,   
                requestParam.getString("userId"));  
System.out.println(sql);  
  
        // �Զ���Ľ����Ϣ��  
        CommonResponse res = new CommonResponse();  
        try {  
            ResultSet result = DBUtil.query(sql); // ���ݿ��ѯ����  
//	          result.getRow();  
              
            if (result.next()) {  
                if (result.getString("userPassword").equals(requestParam.getString("password"))
                		&& result.getString("userType").equals(requestParam.getString("type"))) {  
                    res.setResult("0", "��½�ɹ�");  
                   
                } else {  
                    res.setResult("100", "��¼ʧ�ܣ��û������������");  
                }  
            } else {  
                res.setResult("200", "�õ�½�˺�δע��");  
            }  
        } catch (SQLException e) {  
            res.setResult("300", "���ݿ��ѯ����");  
            e.printStackTrace();  
        }  
  
        // ���������������װ��Json��ʽ׼�����ظ��ͻ��ˣ���ʵ�����紫��ʱ���Ǵ���json���ַ���  
        // ������֮ǰ��String����һ����ֻ��Json�ṩ���ض����ַ���ƴ�Ӹ�ʽ  
        // ��Ϊ�����JSon���õ�����ĵ�����JSon��������ǿ�󣬲�����Android�������Լ��ֶ�ת��ֱ�ӿ��Դ�Beanת��JSon��ʽ  
        String resStr = JSONObject.fromObject(res).toString();  
        response.setCharacterEncoding("UTF-8");
        System.out.println(resStr);  
        response.getWriter().append(resStr).flush();  
		
	}
		

}
