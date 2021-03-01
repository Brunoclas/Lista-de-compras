package br.com.listadecompras.webservices;

import br.com.listadecompras.model.Produto;
import br.com.listadecompras.model.ProdutoRealm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.Path;

public interface IService {

    @Headers("X-Cosmos-Token: 2dHdD6sYFC_ULHb-Ibrm4w")
    @GET("/gtins/{cod}")
    Call<ProdutoRealm> recuperaProd(@Path("cod") String cod);

}
