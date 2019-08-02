package br.com.listadecompras.webservices;

import br.com.listadecompras.model.Produto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface IService {

    @Headers("X-Cosmos-Token: UgqjdZ4FJ9_0W20LzisDYA")
    @GET("/gtins/{cod}")
    Call<Produto> recuperaProd(@Path("cod") String cod);

}
