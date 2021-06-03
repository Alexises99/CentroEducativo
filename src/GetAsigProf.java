

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class GetAsigProf
 */
@WebServlet("/GetAsigProf")
public class GetAsigProf extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAsigProf() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String res = Interacciones.getAsignaturasDeProfesor((String) request.getSession().getAttribute("dni"), (String)request.getSession().getAttribute("token"),(String)request.getSession().getAttribute("cookie"));
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		JSONArray j = new JSONArray(res);
		JSONObject json = new JSONObject();
		JSONArray def = new JSONArray();
		for(int i = 0; i < j.length(); i++) {
			JSONObject asig = j.getJSONObject(i);
			json.put("acronimo", asig.getString("acronimo"));
			
		}
		def.put(json);
		
		response.getWriter().append(def.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
