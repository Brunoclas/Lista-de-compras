package br.com.listadecompras.webservices;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofit {
    public static Retrofit retrofit;

    public static Retrofit getClient(String url) {

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
            httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);

            OkHttpClient client = httpClientBuilder.build();

            retrofit = new Retrofit
                    .Builder()
                    .client(client)
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }

}
