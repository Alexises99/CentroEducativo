

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class NotaMedia
 */
@WebServlet("/NotaMedia")
public class NotaMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotaMedia() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acronimo = request.getParameter("acronimo");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String res = Interacciones.getAlumnosDeAsignatura(acronimo,(String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
		JSONArray jsonArray = new JSONArray(res);
		double media = 0;
		for(int i = 0; i < jsonArray.length();i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			String nota = obj.getString("nota");
			if (nota.equals("") || nota.isEmpty()) media += 0;
			else media+=Double.parseDouble(nota);
		}
		JSONObject json = new JSONObject();
		json.put("media", media/ jsonArray.length());
		response.getWriter().append(json.toString());
	}

}
