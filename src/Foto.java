

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.squareup.okhttp.OkHttpClient;

/**
 * Servlet implementation class Foto
 */
@WebServlet("/Foto")
public class Foto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static OkHttpClient client = new OkHttpClient();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Foto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String dni = request.getParameter("dni");
		String file = getServletConfig().getInitParameter("carpeta");
		file += "/"+dni+".pngb64";
		BufferedReader br = new BufferedReader(new FileReader(file));
		String everything ="mal";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();
		} finally {
		    br.close();
		}
		response.setContentType("text/plain");
		JSONObject json = new JSONObject();
		json.put("dni", dni);
		json.put("img",everything);
		response.getWriter().append(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
