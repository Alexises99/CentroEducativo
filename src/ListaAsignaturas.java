

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Servlet implementation class ListaAsignaturas
 */
@WebServlet("/ListaAsignaturas")
public class ListaAsignaturas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OkHttpClient client = new OkHttpClient();
	public static final MediaType MEDIA_TYPE_JSON
    = MediaType.parse("application/json; charset=utf-8");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaAsignaturas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Login login = new Login();
		User user = login.map.get(request.getRemoteUser());
		HttpSession session = request.getSession(false);
		String token = (String)session.getAttribute("token");
		String cookie = (String)session.getAttribute("cookie");
		if (token.equals(null)) {
			session.putValue("dni", user.getDni());
			session.putValue("password", user.getPassword());
			try {
				if(token.equals(login.login(user.getDni(), user.getPassword()))) {
					session.putValue("token", token);
				}
				else {
					response.setStatus(402);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//response.getWriter().append(token);
		String res = get(token,cookie,request.getRemoteUser());
		String head = "<head><title>Asignaturas</title></head><body>";
		String html = "";
		JSONArray jsonArray = new JSONArray(res);
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject asignatura = jsonArray.getJSONObject(i);
		    html += "<form action='VerAsignatura'><div><a href='VerAsignatura'>"+asignatura.getString("asignatura")+"</a><input type='hidden' name='index' value='"+asignatura.getString("")+"'/><button>Seleccionar</button></div></form>";
		    
		}
		String full = head+html+"</body>";
		response.getWriter().append(full);

		//response.getWriter().append(res);
		
		
		
	}
	
	public String get(String token,String cookie,String user) {
		String url = "http://localhost:9090/CentroEducativo/alumnos/"+user+"/asignaturas";
		HttpUrl.Builder urlBuilder 
	      = HttpUrl.parse(url).newBuilder();
	    urlBuilder.addQueryParameter("key", token); //?id=0
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
