import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
	private final static String site = "localhost";
	
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
	
	
	public static String getAlumnos(String user,String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/alumnos";
		return get(token,cookie,url);
	}
	public static String getAsignaturas(String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/alumnos";
		return get(token,cookie,url);
	}
	
	public static String getAlumnoDni(String user,String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/alumnos/"+user;
		return get(token,cookie,url);
	}
	
	public static String getProfesores(String user,String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/alumnos";
		return get(token,cookie,url);
	}
	
	public static String getAsignaturasConNota(String user,String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/"+user+"/asignaturas";
		return get(token,cookie,url);
	}

	public static String getAlumnosDeAsignatura(String acronimo,String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/asignaturas/"+acronimo+"/alumnos";
		return get(token,cookie,url);
	}
	
	public static String getAsignaturasDeAlumno(String user,String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/alumnos/"+user+"/asignaturas";
		return get(token,cookie,url);
	}
	
	public static String getAsignaturasPorAcronimo(String acronimo,String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/asignaturas/"+acronimo;
		return get(token,cookie,url);
	}
	
	public static String getProfesoresDeAsignaturas(String acronimo,String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/"+acronimo+"/asignaturas";
		return get(token,cookie,url);
	}
	
	public static String getAsignaturasDeProfesor(String user,String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/profesores/"+user+"/asignaturas";
		return get(token,cookie,url);
	}
	
	public static String getProfesorDni(String user,String token,String cookie) {
		String url = "http://"+site+":9090/CentroEducativo/profesores/"+user;
		return get(token,cookie,url);
	}
	public static String setNotaToAlumno(String user,String acronimo,String nota,String token,String cookie) throws IOException {
		String url = "http://"+site+":9090/CentroEducativo/alumnos/"+user+"/asignaturas/"+acronimo;
		HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
	    urlBuilder.addQueryParameter("key", token); 
	    String url1 = urlBuilder.build().toString();
		
	    RequestBody body = RequestBody.create(MediaType.parse("application/json"), nota);
		
		Request request = new Request.Builder()
			.url(url1)
			.header("Cookie", cookie)
			.put(body)
			.build();
		
		Call call = client.newCall(request);
		
		Response response = call.execute();
		return response.body().string();
	    
	}
	
	public static String[] login(String user,String password) throws IOException {
		JSONObject json = new JSONObject();
		json.put("dni", user);
		json.put("password",password);
		
		RequestBody body = RequestBody.create(
				MediaType.parse("application/json"),json.toString());
		
				
		Request request = new Request.Builder()
			.url("http://"+site+":9090/CentroEducativo/login")
			.post(body)
			.build();
		
		Call call = client.newCall(request);
		
			Response response = call.execute();
			String cookie = response.header("Set-Cookie");
			
			String token = response.body().string();
			
			String[]v= {token,cookie};
			return v;
	}

}
