import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class Users {
	
	public User[] list() throws Exception {
		OkHttpClient client = new OkHttpClient();
		Login login = new Login();
		String token = login.login("111111111", "654321");

		
		Request request = new Request.Builder()
		      .url("http://localhost:9090/CentroEducativo/asignaturas?key="+token)
		      .build();
		
		Response response = client.newCall(request);
		response.body()
		return null;

	}
}
