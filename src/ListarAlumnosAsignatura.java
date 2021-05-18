

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
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Servlet implementation class ListarAlumnosAsignatura
 */
@WebServlet("/ListarAlumnosAsignatura")
public class ListarAlumnosAsignatura extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarAlumnosAsignatura() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acronimo = request.getParameter("acronimo");
		HttpSession session = request.getSession(true);
		String token = (String) session.getAttribute("token");
		String cookie = (String) session.getAttribute("cookie");
		String res = " "+getAlumnos(acronimo,token,cookie);
		JSONArray jsonArray = new JSONArray(res);
		String head = "<head><title>Asignaturas</title></head><body>";
		String html = "";
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject asignatura = jsonArray.getJSONObject(i);
		    html += "<div>"
		    		+ "<div>"
		    		+ "<p> Dni Alumno: "+asignatura.getString("alumno")+"</p>"
		    		+ "</div>"
		    		+ "<div>"
		    		+ "<p> Nota: " + asignatura.getString("nota") + "</p>"
		    		+ "</div>"
		    		+ "<div>"
		    		+ "<button>Modificar Nota</button>"
		    		+ "</div>";
		}
		String full = head+html+"</body>";
		response.getWriter().append(full);
		
		
	}

	public String getAlumnos(String acronimo,String token,String cookie) {
		OkHttpClient client = new OkHttpClient();
		String url = "http://localhost:9090/CentroEducativo/asignaturas/"+acronimo+"/alumnos";
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
