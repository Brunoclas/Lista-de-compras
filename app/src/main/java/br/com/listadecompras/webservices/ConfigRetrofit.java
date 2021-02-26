package br.com.listadecompras.webservices;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofit {
    public static Retrofit retrofit;
    private static SSLContext sslContext;
    public static Retrofit getClient(String url) {

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };


            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
            httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
            httpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);


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
