

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
 * Servlet implementation class DetallesAlumno
 */
@WebServlet("/DetallesAlumno")
public class DetallesAlumno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final OkHttpClient client = new OkHttpClient();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetallesAlumno() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		String token = (String) session.getAttribute("token");
		String cookie = (String) session.getAttribute("cookie");
		String dni = (String) session.getAttribute("dni");
		
		String alumno = getDescription(token,cookie,dni);
		String asignaturas= " "+getAsignaturas(token,cookie,dni);
		JSONObject Alumno = new JSONObject(alumno);
		
		
		String html = "<html>"
				+ "<head>"
				+ "<meta charset='UTF-8'>" + 
				"  <meta http-equiv='X-UA-Compatible' content='IE=edge'>" + 
				"  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" + 
				"  <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x' crossorigin='anonymous'>" + 
				"  <script src='https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js' integrity='sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4' crossorigin='anonymous'></script>" + 
				"  <title>Certificado de Notas</title>"
				+ "</head>"
				+ "<body>"
				+ "<div class=\"mx-5 my-4\">" + 
				"            <div class=\"text-center mx-5\">" + 
				"                <h2 class=\"text-info\">Certificado sin validez academica</h2>" + 
				"                <h3>Curso 2020/2021</h3>" + 
				"                <div class=\"row my-5\">" + 
				"                    <div class=\"col-md-6\">" + 
				"                        <div class=\"row my-3\">" + 
				"                            <div class=\"col-md-6 bg-primary text-white\">" + 
				"                                Nombre:" + 
				"                            </div>" + 
				"                            <div class=\" col-md-6\">" + Alumno.getString("nombre")+
				"                            </div>" + 
				"                        </div>" + 
				"                        <div class=\"row my-3\">" + 
				"                            <div class=\"col-md-6 bg-primary text-white\">" + 
				"                                Apellidos: "+ 
				"                            </div>" + 
				"                            <div class=\"col-md-6\">" + Alumno.getString("apellidos")+
				"                            </div>" + 
				"                        </div>" + 
				"                        <div class=\"row my-3\">" + 
				"                            <div class=\"col-md-6 bg-primary text-white\">" + 
				"                                Dni: "+
				"                            </div>" + 
				"                            <div class=\"col-md-6 \">" + Alumno.getString("dni")+
				"                            </div>" + 
				"                        </div>" + 
				"                    </div>" + 
				"                    <div class=\"col-md-6 inline-block\">" + 
				"                       <img src=\"default.png\" class=\"\" width=\"250\" height=\"250\"/>" + 
				"                    </div>   " + 
				"                </div>" + 
				"                <div>" + 
				"                    <p>Ha logrado las siguientes calificaciones</p>" + 
				"                </div>" + 
				"                <table class=\"table\">" + 
				"                    <thead class=\"thead-light\">" + 
				"                        <tr>" + 
				"                            <th scope=\"col\">Acronimo</th>" + 
				"                            <th scope=\"col\">Asignturas</th>" + 
				"                            <th scope=\"col\">Calificacion</th>" + 
				"                        </tr>" + 
				"                    </thead>" + 
				"                    <tbody>";
		JSONArray jsonArray = new JSONArray(asignaturas);
		
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject asignatura = jsonArray.getJSONObject(i);
		    
		    String s = getName(token,cookie,asignatura.getString("asignatura"));
		    JSONObject asignatura1 = new JSONObject(s);
		    html += " <tr>" + 
		    		" <th scope=\"row\">"+asignatura1.getString("acronimo")+"</th>" + 
		    		" <td>"+asignatura1.getString("nombre")+"</td>" + 
		    		" <td>"+asignatura.getString("nota")+"</td>" + 
		    		" </tr>";
		}
		html+= " </tbody>" + 
				"</table>" +
				"</div>" + 
				"<div class=\"text-center\">" + 
				"  Valencia" + 
				"</div>" +
				"</div>" + 
				"</body>" + 
				"</html>";
		response.getWriter().append(html);
				
	}
	
	public String getDescription(String token,String cookie,String user) {
		String url = "http://localhost:9090/CentroEducativo/alumnos/"+user;
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
	public String getName(String token,String cookie,String name) {
		String url = "http://localhost:9090/CentroEducativo/asignaturas/"+name;
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
	
	public String getAsignaturas(String token,String cookie,String user) {
		String url = "http://localhost:9090/CentroEducativo/alumnos/"+user+"/asignaturas";
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
