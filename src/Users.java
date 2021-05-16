import com.squareup.okhttp.OkHttpClient;

public class Users {
	
	public User[] list() {
		OkHttpClient client = new OkHttpClient();
		Login login = new Login();
		login.login("111111111", "654321");

		String run() throws IOException {
		  Request request = new Request.Builder()
		      .url(url)
		      .build();

		  try (Response response = client.newCall(request).execute()) {
		    return response.body().string();
		  }
		}
	}
}
