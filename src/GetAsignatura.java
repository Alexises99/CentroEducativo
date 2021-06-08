

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class getAsignatura
 */
@WebServlet("/getAsignatura")
public class GetAsignatura extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String acronimo = "mal";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAsignatura() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		String res = Interacciones.getAsignaturasPorAcronimo(acronimo, (String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
		response.getWriter().append(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**String acronimo = request.getParameter("acronimo");
		this.acronimo = acronimo;*/
		String acronimo = request.getParameter("acronimo");
		response.setContentType("application/json");
		String res = Interacciones.getAsignaturasPorAcronimo(acronimo, (String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
		response.getWriter().append(res);
	}

}
