package com.example.harrypotter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class ApiService {

    // Metoda pentru preluarea datelor de la o adresă URL
    public static String fetchData(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
           return response.body().string();
        }
    }
}
