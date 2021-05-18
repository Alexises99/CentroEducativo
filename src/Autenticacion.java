

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
import org.json.JSONObject;

import com.squareup.okhttp.Call;
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
		HashMap<String,User> map = new HashMap<String,User>();
		HttpSession session = request.getSession(true);
		String token = "";
		String dni = request.getParameter("dni");
		if(dni == null) return;
		try {
			token = (String) session.getAttribute("token");
		} catch(Exception e) {
			token = null;
		}
		String cookie = "";
		if(token == null) {
			String login = request.getRemoteUser();
			if(!(login == null)) {
				
				User user = credentialsWithBasicAuthentication(request);
				User user1 = new User(dni,user.getPassword());
				map.put(user.getDni(), user1);
				session.setAttribute("dni", map.get(user.getDni()).getDni());
				session.setAttribute("password", map.get(user.getDni()).getPassword());
					String v[] = login(map.get(user.getDni()).getDni(),map.get(user.getDni()).getPassword());
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
