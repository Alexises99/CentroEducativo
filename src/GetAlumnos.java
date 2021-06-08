

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class GetAlumnos
 */
@WebServlet("/GetAlumnos")
public class GetAlumnos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String acronimo = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAlumnos() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String alumnos = Interacciones.getAlumnosDeAsignatura(acronimo,(String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
		JSONArray alumns = new JSONArray(alumnos);
		JSONArray json = new JSONArray();
		for(int i = 0; i < alumns.length(); i++) {
			JSONObject al = alumns.getJSONObject(i);
			String res = Interacciones.getAlumnoDni(al.getString("alumno"),(String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
			JSONObject hola = new JSONObject(res);
			hola.put("nota", al.getString("nota"));
			json.put(hola);
		}
		response.setContentType("application/json");
		response.getWriter().append(json.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acronimo = request.getParameter("acronimo");
		this.acronimo = acronimo;
		response.getWriter().append("{msg:okey}");
	}

}
