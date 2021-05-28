

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
		String dni = (String) session.getAttribute("dni");
		
		
		if(request.isUserInRole("rolalu")) {
			String res = " "+Interacciones.getAsignaturasDeAlumno(dni);
			JSONArray jsonArray = new JSONArray(res);
			String html = "<!DOCTYPE html>" + 
					"<html lang=\"en\">" + 
					"<head>" + 
					"    <meta charset=\"UTF-8\">" + 
					"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" + 
					"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + 
					"    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x\" crossorigin=\"anonymous\">" + 
					"    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4\" crossorigin=\"anonymous\"></script>" + 
					"    <script src='jquery-3.6.0.js'></script>" +
					"    <title>Ver Asignaturas</title>" + 
					"</head>" + 
					"<body>" + 
					"  <div>" + 
					"        <img src=\"def.png\"  class=\"img-fluid\"/>" + 
					"    </div>"+
					"    <div class=\"container mt-3\">" + 
					"        <div class=\"row\">" + 
					"          <div class=\"col-md-12\">" + 
					"            <div class=\"card\">" + 
					"              <div class=\"card-header\">" + 
					"                <ul class=\"nav nav-tabs card-header-tabs\" id=\"asignatura-list\" role=\"tablist\">";
			for(int i = 0;i <jsonArray.length();i++) {
				JSONObject asignatura = jsonArray.getJSONObject(i);
				if(i == 0) {
					html += "<li class=\"nav-item\">" + 
							"<a class=\"nav-link active\" href=\"#description"+i+"\" role=\"tab\" aria-controls=\"description"+i+"\" aria-selected=\"true\">"+asignatura.getString("asignatura")+"</a>" + 
							"</li>";
				}
				else{
					html += "<li class=\"nav-item\">" + 
						"<a class=\"nav-link\" href=\"#description"+i+"\" role=\"tab\" aria-controls=\"description"+i+"\" aria-selected=\"false\">"+asignatura.getString("asignatura")+"</a>" + 
						"</li>";
				}
			}
					html += 
					"                </ul>" + 
					"              </div>" + 
					"              <div class=\"card-body\">" + 
					"                <h4 class=\"card-title text-center\">"+dni+"</h4>" + 
					"                <h6 class=\"card-subtitle mb-2 text-center\">Nombre Alumno</h6>" +  
					"                <div class=\"tab-content mt-3\">";
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject asignatura = jsonArray.getJSONObject(i);
			    if(i == 0) {
			    	html +=
							"                  <div class=\"tab-pane active\" id=\"description"+i+"\" role=\"tabpanel\" aria-labelledby=\"description"+i+"-tab\">" + 
							"                    <div class=\"row\">" + 
							"                        <div class=\"col-md-6\">" + 
							"                            <div class=\"h5\">Acronimo: "+asignatura.getString("asignatura")+"</div>" + 
							"                            <div class=\"h5\">Asignatura: "+asignatura.getString("asignatura")+"</div>" + 
							"                            <div class=\"h5\">Nota: "+asignatura.getString("nota")+"</div>" +
							"                        </div>" + 
							"                        <div class=\"col-md-6\">" + 
							"                            <div class=\"h4\">Alumnos</div>" + 
							"                        <ul class=\"list-group\">";
			    }
			    else {
			    	html +=
			    
					"                  <div class=\"tab-pane\" id=\"description"+i+"\" role=\"tabpanel\" aria-labelledby=\"description"+i+"-tab\">" + 
					"                    <div class=\"row\">" + 
					"                        <div class=\"col-md-6\">" + 
					"                            <div class=\"h5\">Acronimo: "+asignatura.getString("asignatura")+"</div>" + 
					"                            <div class=\"h5\">Asignatura: "+asignatura.getString("asignatura")+"</div>" + 
					"                            <div class=\"h5\">Nota: "+asignatura.getString("nota")+"</div>" + 
					"                        </div>" + 
					"                        <div class=\"col-md-6\">" + 
					"                            <div class=\"h4\">Alumnos</div>" + 
					"                        <ul class=\"list-group\">";
			    }
					String r = " "+Interacciones.getAlumnosDeAsignatura(asignatura.getString("asignatura"));
					JSONArray jsonArray2 = new JSONArray(r);
					
					for(int j = 0; j < jsonArray2.length();j++) {
						JSONObject alum = jsonArray2.getJSONObject(j);
						html += "<li class=\"list-group-item\">"+alum.getString("alumno")+"</li>";
					}
					html +=                          
							"                          </ul>" + 
							"                        </div>" + 
							"                    </div>" + 
							"                  </div>";
				}
					html += 
					"                  </div>" + 
					"                </div>" + 
					"              </div>" + 
					"            </div>" + 
					"          </div>" + 
					"        </div>" + 
					"        <div class=\"text-center mt-5\">" + 
					"	<form action='DetallesAlumno'>"+
					"            <button id='button' class=\"btn btn-primary\">" + 
					"                Ver Certificado" + 
					"            </button>" + "</form>" +
					"        </div>" + 
					"      </div>" +
					"<script>" + 
					"$('#asignatura-list a').on('click', function (e) {" + 
					"  e.preventDefault();" + 
					"  console.log(\"hola\");" + 
					"  $(this).tab('show');" + 
					"})"+
					"</script>"+
					"</body>"+
					"</html>"
					;
			
			response.getWriter().append(html);
		
		}
		else if(request.isUserInRole("rolpro")) {
			String res = " "+ Interacciones.getAsignaturasDeProfesor(dni);
			JSONArray jsonArray = new JSONArray(res);
			String head = "<head><script src='jquery-3.6.0.js'></script><title>Asignaturas</title></head><body>";
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
			    		+ "<div>"
			    		+ "<p>La nota media es: "+calcularNota(asignatura.getString("acronimo"))+"</p>"
			    		+ "</div>"
			    		+ "<form action='alumnos.html'>"
			    		+ "<input type='hidden' name='acronimo' value='"+asignatura.getString("acronimo")+"'/>"
			    		+ "<button>Ver alumnos</button>"
			    		+ "</form>"
			    		+ "</div>"
			    		+ "</div>"
			    		+ "<script>"
			    		+ "$.ajax({url : 'GetAlumnos',"
			            +"data : {acronimo: '" +asignatura.getString("acronimo")+"'},"
			            +"method : 'post'," 
			            +"dataType : 'json',"
			            +"success : function(response){"
			            +"alert('funciona bien');"
			            +"}"
			            +"});"
			            +"</script>";	
			}
			String full = head+html+"</body>";
			response.getWriter().append(full);
			
		}
	}
	
	public double calcularNota(String acronimo) {
		
		String res = Interacciones.getAlumnosDeAsignatura(acronimo);
		JSONArray jsonArray = new JSONArray(res);
		double media = 0;
		for(int i = 0; i < jsonArray.length();i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			String nota = obj.getString("nota");
			if (nota.equals("") || nota.isEmpty()) media += 0;
			else media+=1;
		}
		return media / jsonArray.length();
		
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
