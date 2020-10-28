package Week_02;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class HttpService {



    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .get()
                    .url("http://127.0.0.1:8081/test")
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response.body().string());
            }
        } finally {
            client.clone();
        }

    }

}
