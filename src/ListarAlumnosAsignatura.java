

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
		String res = " "+Interacciones.getAlumnosDeAsignatura(acronimo,(String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
		JSONArray jsonArray = new JSONArray(res);
		String head = "<head><title>Asignaturas</title><script src='jquery-3.6.0.js'></script></head><body><div>";
		String html = "";
		String token = (String) (request.getSession().getAttribute("token"));
		String cookie = (String) (request.getSession().getAttribute("cookie"));
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject asignatura = jsonArray.getJSONObject(i);
		    html += 
		    		 "<div>"
		    		+ "<p> Dni Alumno: "+asignatura.getString("alumno")+"</p>"
		    		+ "</div>"
		    		+ "<div>"
		    		+ "<p> Nota: " + "</p>"
		    		+ "</div>"
		    		+ "<div>"
		    		+ "<button onclick='cambiarNota()'>Modificar Nota</button>"
		    		+ "</div>";
		    		
		}
		html+= "</div>"
				+ "<script>"
	    		+ "function cambiarNota(){"
	    		+ "$.getJSON('GetAlumnos?acronimo="+acronimo+"')"
	    		+ ".done(function(data){"
	    		+ ""
	    		+ "})"
	    		+ "}"
	    		+ "</script>";
		String full = head+html+"</body>";
		response.getWriter().append(full);
		
		
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
