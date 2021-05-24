import java.io.IOException;

import org.json.JSONObject;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Interacciones {
	
	private final static OkHttpClient client = new OkHttpClient();
	private static String token = "";
	private static String cookie = "";
	
	private static String get(String token,String cookie,String url) {
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
			
			try {
				response = call.execute();
				return response.body().string();
			} catch (IOException e) {
				return "holamal";
			}
	}
	
	
	public static String getAlumnos(String user) {
		String url = "http://localhost:9090/CentroEducativo/alumnos";
		return get(token,cookie,url);
	}
	public static String getAsignaturas() {
		String url = "http://localhost:9090/CentroEducativo/asignaturas";
		return get(token,cookie,url);
	}
	
	public static String getAlumnoDni(String user) {
		String url = "http://localhost:9090/CentroEducativo/alumnos/"+user;
		return get(token,cookie,url);
	}
	
	public static String getProfesores(String user) {
		String url = "http://localhost:9090/CentroEducativo/profesores";
		return get(token,cookie,url);
	}
	
	public static String getAsignaturasConNota(String user) {
		String url = "http://localhost:9090/CentroEducativo/"+user+"/asignaturas";
		return get(token,cookie,url);
	}

	public static String getAlumnosDeAsignatura(String acronimo) {
		String url = "http://localhost:9090/CentroEducativo/asignaturas/"+acronimo+"/alumnos";
		return get(token,cookie,url);
	}
	
	public static String getAsignaturasDeAlumno(String user) {
		String url = "http://localhost:9090/CentroEducativo/alumnos/"+user+"/asignaturas";
		return get(token,cookie,url);
	}
	
	public static String getAsignaturasPorAcronimo(String acronimo) {
		String url = "http://localhost:9090/CentroEducativo/asignaturas/"+acronimo;
		return get(token,cookie,url);
	}
	
	public static String getProfesoresDeAsignaturas(String acronimo) {
		String url = "http://localhost:9090/CentroEducativo/"+acronimo+"/asignaturas";
		return get(token,cookie,url);
	}
	
	public static String getAsignaturasDeProfesor(String user) {
		String url = "http://localhost:9090/CentroEducativo/profesores/"+user+"/asignaturas";
		return get(token,cookie,url);
	}
	
	public static String getProfesorDni(String user) {
		String url = "http://localhost:9090/CentroEducativo/profesores/"+user;
		return get(token,cookie,url);
	}
	
	public static String[] login(String user,String password) throws IOException {
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
			String cookie1 = response.header("Set-Cookie");
			
			String token1 = response.body().string();
			token = token1;
			cookie = cookie1;
			
			String[]v= {token1,cookie1};
			return v;
	}

}
