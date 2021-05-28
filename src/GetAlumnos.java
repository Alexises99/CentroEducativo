

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String alumnos = Interacciones.getAlumnosDeAsignatura(acronimo);
		response.setContentType("application/json");
		response.getWriter().append(alumnos);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acronimo = request.getParameter("acronimo");
		this.acronimo = acronimo;
	}

}
