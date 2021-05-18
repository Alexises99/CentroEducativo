

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
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
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
				String dni1 = login;
				session.setAttribute("dni", map.get(login).getDni());
				session.setAttribute("password",user.getPassword());
					String v[] = login(map.get(login).getDni(),user.getPassword());
					token = v[0];
					session.setAttribute("token", token);
					cookie =  v[1];
					session.setAttribute("cookie", cookie);
				 
					//response.sendError(422, "Usuario o contraseña incorrecto");
				
				if(token != null) session.setAttribute("token", token);	
			} 
			else {
				response.sendError(401, "Usuario o contraseña no esta en base de datos");
			}
			
		}
		chain.doFilter(request, response);
	}
	
	public HashMap<String,UsuarioFull> populate() throws IOException {
		HashMap<String,UsuarioFull> map = new HashMap<String,UsuarioFull>();
		String v[] = login("111111111","654321");
		String token = v[0];
		String cookie = v[1];
		String url = "http://localhost:9090/CentroEducativo/alumnos";
		OkHttpClient client = new OkHttpClient();
		HttpUrl.Builder urlBuilder 
	      = HttpUrl.parse(url).newBuilder();
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
		
		
			JSONArray json = new JSONArray(res);
			for (int i = 0; i < json.length(); i++) {
			    JSONObject alumno = json.getJSONObject(i);
			    UsuarioFull user = new UsuarioFull(alumno.getString("dni"),alumno.getString("nombre"));
			    map.put(user.getNombre(),user);
			}
			String res1 = getProfs();
			JSONArray json1 = new JSONArray(res1);
			for (int i = 0; i < json1.length(); i++) {
			    JSONObject profesor = json1.getJSONObject(i);
			    UsuarioFull user = new UsuarioFull(profesor.getString("dni"),profesor.getString("nombre"));
			    map.put(user.getNombre(),user);
			}
			return map;
	}
	
	public String getProfs() throws IOException {
		OkHttpClient client = new OkHttpClient();
		
		String v[] = login("111111111","654321");
		String token1 = v[0];
		String cookie1 = v[1];
		String url = "http://localhost:9090/CentroEducativo/profesores";
		HttpUrl.Builder urlBuilder
	      = HttpUrl.parse(url).newBuilder();
	    urlBuilder.addQueryParameter("key", token1); 
	    String urlq = urlBuilder.build().toString();
	   
		Request request1 = new Request.Builder()
				.url(urlq)
				.header("Content-Type", "application/json")
				.header("Cookie", cookie1)
				.build();
		Call call1 = client.newCall(request1);
		Response response1;
	    String res1 ="";
		try {
			response1 = call1.execute();
			res1 = " "+response1.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			res1 = "";
		}
		return res1;
	}

	
	public String[] login(String user,String password) throws IOException {
		OkHttpClient client = new OkHttpClient();
		JSONObject json = new JSONObject();
		json.put("dni", user);
		json.put("password",password);
		
		RequestBody body = RequestBody.create(
				MediaType.parse("application/json"),json.toString());
		
				
		Request request = new Request.Builder()
			.url("http://localhost:9090/CentroEducativo/login")
			.post(body)
			.build();
		
		Call call = client.newCall(request);
		
			Response response = call.execute();
			String cookie = response.header("Set-Cookie");
			
			String token = response.body().string();
			String[]v= {token,cookie};
			return v;
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
