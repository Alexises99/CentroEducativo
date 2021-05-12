

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OkHttpClient client = new OkHttpClient();
	public static final MediaType MEDIA_TYPE_JSON
    = MediaType.parse("application/json; charset=utf-8");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		
		try {
			String res = post(user,password);
			response.getWriter().append(res);
			HttpSession session = request.getSession(true);
			session.setAttribute("token", res);
			response.sendRedirect("listarAsignaturas.html");
		} catch(Exception e) {
			System.err.print(e.toString());
		}
	}
	
	public String post(String user, String password) throws Exception{
		
		JSONObject json = new JSONObject();
		json.put("dni", user);
		json.put("password",password);
		RequestBody body = RequestBody.create(
				MediaType.parse("application/json"),json.toString());
		
				
		Request request = new Request.Builder()
			.url("http://localhost:9090/CentroEducativo/login")
			.post(body)
			.build();
		
		Call call = client.newCall(request);
		try {
			Response response = call.execute();
			String token = response.body().string();
			return token;
			
		} catch(Exception e) {
			return(e.toString());
		}
	}

}
