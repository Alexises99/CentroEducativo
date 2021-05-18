

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
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
		//Login login = new Login();
		//User user = login.map.get(request.getRemoteUser());
		
		HttpSession session = request.getSession(false);
		String token = (String) session.getAttribute("token");
		String cookie = (String) session.getAttribute("cookie");
		
		
		
		
		if(request.isUserInRole("rolalu")) {
			String res = " "+getAsignaturas(token,cookie,(String)session.getAttribute("dni"),"alumnos");
			JSONArray jsonArray = new JSONArray(res);
			String head = "<head><title>Asignaturas</title></head><body>";
			String html = "";
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject asignatura = jsonArray.getJSONObject(i);
			    html += "<div><a href='VerAsignatura?nombre="+asignatura.getString("asignatura")+"&nota="+asignatura.getString("nota")+"'>"+asignatura.getString("asignatura")+"</a>";
			}
			String full = head+html+"</body>";
			response.getWriter().append(full);
		
		}
		else if(request.isUserInRole("rolpro")) {
			String res = " "+getAsignaturas(token,cookie,(String)session.getAttribute("dni"),"profesores");
			JSONArray jsonArray = new JSONArray(res);
			String head = "<head><title>Asignaturas</title></head><body>";
			String html = "";
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject asignatura = jsonArray.getJSONObject(i);
			    html += "<div>"
			    		+ "<div>"
			    		+ "<p>Nombre: "+asignatura.getString("nombre")+"</p>"
			    		+ "</div>"
			    		+ "<div>"
			    		+ "<p>Acronimo: "+asignatura.getString("acronimo")+"</p>"
			    		+ "</div>"
			    		+ "<div>"
			    		+ "<p>Curso "+asignatura.getInt("curso")+"</p>"
			    		+ "</div>"
			    		+ "<div>"
			    		+ "<p>Cuatrimestre "+asignatura.getString("cuatrimestre") +"</p>"
			    		+ "</div>"
			    		+ "<div>"
			    		+ "<p>Creditos "+asignatura.getDouble("creditos")+"</p>"
			    		+ "</div>"
			    		+ "<div>"
			    		+ "<form action='ListarAlumnosAsignatura'>"
			    		+ "<input type='hidden' name='acronimo' value='"+asignatura.getString("acronimo")+"'/>"
			    		+ "<button>Ver alumnos</button>"
			    		+ "</div>"
			    		+ "</div>";	
			}
			String full = head+html+"</body>";
			response.getWriter().append(full);
			
		}
		
		
		

		//response.getWriter().append(res);
		
	}
	
	public String getAsignaturas(String token,String cookie,String user,String rol) {
		String url = "http://localhost:9090/CentroEducativo/"+rol+"/"+user+"/asignaturas";
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
