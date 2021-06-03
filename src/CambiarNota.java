

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class CambiarNota
 */
@WebServlet("/CambiarNota")
public class CambiarNota extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CambiarNota() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String alumno = request.getParameter("dni");
		String acronimo = request.getParameter("acronimo");
		String nota = request.getParameter("nota");
		double nota1 = Double.parseDouble(nota);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if(nota1  > 10.0 || nota1 < 0.0) {
			JSONObject j = new JSONObject();
			j.put("error", "nota invalida");
			response.setStatus(400);
			response.getWriter().append(j.toString());
			
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String res = Interacciones.setNotaToAlumno(alumno, acronimo, nota,(String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
		JSONObject json = new JSONObject();
		json.put("msg", alumno+acronimo+nota);
		response.getWriter().append(json.toString());
	}

}
