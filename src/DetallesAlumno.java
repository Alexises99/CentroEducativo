

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class DetallesAlumno
 */
@WebServlet("/DetallesAlumno")
public class DetallesAlumno extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
		String dni = (String) session.getAttribute("dni");
		
		String alumno = Interacciones.getAlumnoDni(dni,(String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
		String asignaturas= " "+Interacciones.getAsignaturasDeAlumno(dni,(String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
		JSONObject Alumno = new JSONObject(alumno);
		
		
		String html = "<html>"
				+ "<head>"
				+ "<meta charset='UTF-8'>" + 
				"  <meta http-equiv='X-UA-Compatible' content='IE=edge'>" + 
				"  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" + 
				"  <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x' crossorigin='anonymous'>" + 
				"  <script src='https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js' integrity='sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4' crossorigin='anonymous'></script>" + 
				"  <script src=\"jquery-3.6.0.js\"></script>"
				+ "<title>Certificado de Notas</title>"
				+ "</head>"
				+ "<body>"
				+ "<div>" + 
				"        <img src=\"banneredu.png\"  class=\"img-fluid\"/>" + 
				"    </div>"+
			    "<div class=\"mx-5 my-4\">" + 
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
				"                            <div class=\"col-md-6 \">" + "<p id='dni'></p>"+
				"                            </div>" + 
				"                        </div>" + 
				"                    </div>" + 
				"                    <div class=\"col-md-6 inline-block\">" + 
				"                       <img width=\"250\" id='img' height=\"250\"/>" + 
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
		    
		    String s = Interacciones.getAsignaturasPorAcronimo(asignatura.getString("asignatura"),(String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
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
				"<script>"+
				"$.getJSON(\"Foto?dni=12345678W\")\n" + 
				".done(function(response){\n" + 
				"$(\"#dni\").text(response.dni);\n" + 
				"$(\"#img\").attr(\"src\", \"data:image/png;base64,\"+response.img);\n" + 
				"})\n" + 
				".fail(function(jqxhr, textStatus, error ) {\n" + 
				" var err = jqxhr.response.replace(\",\", \"\\n\"); // Pequeños ajustes\n" + 
				" alert(\"Algo mal: \"+error);\n" + 
				"});\n" + 
				"</script>"+
				"</body>" + 
				"</html>";
		response.getWriter().append(html);
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
