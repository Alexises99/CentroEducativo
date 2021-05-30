package interacciones;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Servlet implementation class GetAlumnoDni
 */
@WebServlet("/GetAlumnoDni")
public class GetAlumnoDni extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static OkHttpClient client = new OkHttpClient();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAlumnoDni() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user = request.getParameter("user");
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		String cookie = (String) session.getAttribute("cookie");
		String url = "http://localhost:9090/CentroEducativo/alumnos/"+user;
		HttpUrl.Builder urlBuilder 
	      = HttpUrl.parse(url).newBuilder();
	    urlBuilder.addQueryParameter("key", token); 
	    String url1 = urlBuilder.build().toString();
	   
	    
		Request req = new Request.Builder()
				.url(url1)
				.header("Content-Type", "application/json")
				.header("Cookie", cookie)
				.build();
		Call call = client.newCall(req);
		
			Response res;
			
			try {
				res = call.execute();
				if(res.code() == 200) {
					String msg = res.body().string();
					response.setContentType("UTF-8");
					response.setStatus(200);
					response.addHeader("Content-Type", "application/json");
					response.getWriter().append(msg);
					response.getWriter().flush();
					response.getWriter().close();
				}
				else {
					String msg = res.body().string();
					response.setContentType("UTF-8");
					response.setStatus(res.code());
					response.addHeader("Content-Type", "application/json");
					response.getWriter().append(msg);
					response.getWriter().flush();
					response.getWriter().close();
				}
				
			} catch (IOException e) {
				response.sendError(500, "error interno");
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
