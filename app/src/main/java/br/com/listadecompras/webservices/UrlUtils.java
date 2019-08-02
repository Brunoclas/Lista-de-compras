package br.com.listadecompras.webservices;

import android.content.Context;

public class UrlUtils {

    public static final String BASE_URL = "https://api.cosmos.bluesoft.com.br";

    public static IService getService(){
        return ConfigRetrofit.getClient(BASE_URL).create(IService.class);
    }

}
