

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Servlet implementation class VerAsignatura
 */
@WebServlet("/VerAsignatura")
public class VerAsignatura extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OkHttpClient client = new OkHttpClient();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerAsignatura() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nombre = request.getParameter("nombre");
		String nota = request.getParameter("nota");
		HttpSession session = request.getSession(false);
		String token = (String)session.getAttribute("token");
		String cookie = (String)session.getAttribute("cookie");
		//String res = get(token,cookie,index);
		
		//JSONObject json = new JSONObject(res);
		String html ="<head>"
				+ "<title>"+nombre+"</title>"
				+ "</head>"
				+ "<body>"
				+ "<div>"
				+ "<p>Cuatrimestre " + nombre + "</p>"
				+ "<p>Nota " + nota + "</p>"
				+ "</div>"
				+ "</body>";
		response.getWriter().append(html);
		request.logout();
		
	}
	
	public String get(String token, String cookie, String acronimo) {
		String url = "http://localhost:9090/CentroEducativo/asignaturas/"+acronimo;
		HttpUrl.Builder urlBuilder 
	      = HttpUrl.parse(url).newBuilder();
	    urlBuilder.addQueryParameter("key", token);
	    String url1 = urlBuilder.build().toString();
	   
	    
		Request request = new Request.Builder()
				.url(url1)
				.header("Content-Type", "application/json")
				.header("Cookie", cookie)
				.build();
		Call call = client.newCall(request);
		
			Response response;
			
			try {
				response = call.execute();
				return response.body().string();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return "holamal";
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
