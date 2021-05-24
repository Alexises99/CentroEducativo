

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import com.squareup.okhttp.Response;

/**
 * Servlet Filter implementation class Autenticacion
 */
@WebFilter("/Autenticacion")
public class Autenticacion implements Filter {

    /**
     * Default constructor. 
     */
	
    public Autenticacion() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// place your code here
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HashMap<String,UsuarioFull> map = populate();
		HttpSession session = request.getSession(true);
		String token = "";
		try {
			token = (String) session.getAttribute("token");
		} catch(Exception e) {
			token = null;
		}
		String cookie = "";
		
		if(token == null) {
			User user = credentialsWithBasicAuthentication(request);
			String login = user.getDni();
			
			if(!(login == null)) {
				if(map.get(login).getDni() == null|| user.getPassword() == null) {
					response.sendError(422, "Unathorized");
				}
				session.setAttribute("dni", map.get(login).getDni());
				session.setAttribute("password",user.getPassword());
					String v[] = Interacciones.login(map.get(login).getDni(),user.getPassword());
					token = v[0];
					session.setAttribute("token", token);
					cookie =  v[1];
					session.setAttribute("cookie", cookie);
				 
					//response.sendError(422, "Usuario o contraseña incorrecto");
				
				if(token != null) session.setAttribute("token", token);	
			} 
			else {
				response.sendError(401, "Usuario/contraseña no esta en base de datos");
			}
			
		}
		chain.doFilter(request, response);
	}
	
	public String getJsons(String param) throws IOException {
		String v[] = Interacciones.login("111111111","654321");
		String token = v[0];
		String cookie = v[1];
		String url = "http://localhost:9090/CentroEducativo/"+param;
		OkHttpClient client = new OkHttpClient();
		HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
	    urlBuilder.addQueryParameter("key", token); 
	    String url1 = urlBuilder.build().toString();
	   
		Request request = new Request.Builder()
				.url(url1)
				.header("Content-Type", "application/json")
				.header("Cookie", cookie)
				.build();
		Call call = client.newCall(request);
		
		Response response;
		String res="";
		try {
			response = call.execute();
			res = " "+response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			res = "";
		}
		return res;
	}
	
	public HashMap<String,UsuarioFull> populate() throws IOException {
		HashMap<String,UsuarioFull> map = new HashMap<String,UsuarioFull>();
		String alumnos = getJsons("alumnos");
		String profesores = getJsons("profesores");
		
		
			JSONArray json = new JSONArray(alumnos);
			for (int i = 0; i < json.length(); i++) {
			    JSONObject alumno = json.getJSONObject(i);
			    String s = alumno.getString("nombre").substring(0,2);
			    String s1 = alumno.getString("apellidos").substring(0,3);
			    String nom = s+s1;
			    UsuarioFull user = new UsuarioFull(alumno.getString("dni"),alumno.getString("nombre"));
			    map.put(nom.toLowerCase(),user);
			}
	
			JSONArray json1 = new JSONArray(profesores);
			for (int i = 0; i < json1.length(); i++) {
			    JSONObject profesor = json1.getJSONObject(i);
			    String s = profesor.getString("nombre").substring(0,2);
			    String s1 = profesor.getString("apellidos").substring(0,3);
			    String nom = s+s1;
			    UsuarioFull user = new UsuarioFull(profesor.getString("dni"),profesor.getString("nombre"));
			    map.put(nom.toLowerCase(),user);
			}
			return map;
	}
	

	public User credentialsWithBasicAuthentication(HttpServletRequest req) {
	    String authHeader = req.getHeader("Authorization");
	    if (authHeader != null) {
	        StringTokenizer st = new StringTokenizer(authHeader);
	        if (st.hasMoreTokens()) {
	            String basic = st.nextToken();

	            if (basic.equalsIgnoreCase("Basic")) {
	                try {
	                    String credentials = new String(Base64.decodeBase64(st.nextToken()), "UTF-8");
	                    
	                    int p = credentials.indexOf(":");
	                    if (p != -1) {
	                        String login = credentials.substring(0, p).trim();
	                        String password = credentials.substring(p + 1).trim();
	                        User user = new User(login,password);
	                        return user;
	                    } else {
	                        //LOG.error("Invalid authentication token");
	                    }
	                } catch (UnsupportedEncodingException e) {
	                    //LOG.warn("Couldn't retrieve authentication", e);
	                }
	            }
	        }
	    }

	    return null;
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
