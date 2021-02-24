package br.com.listadecompras.webservices;

import org.json.JSONObject;

import java.util.HashMap;

import br.com.listadecompras.model.Barcode_Request;
import br.com.listadecompras.model.Produto;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.model.Produto_k;
import br.com.listadecompras.model.Produto_k_root;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IService {

//    @Headers("X-Cosmos-Token: UgqjdZ4FJ9_0W20LzisDYA")
//    @GET("/gtins/{cod}")
//    Call<ProdutoRealm> recuperaProd(@Path("cod") String cod);

    @POST("/br-cap/cap-be/v2.1/index.php/openprice/pricecheck")
    Call<Produto_k_root> recuperaProd(@Body Barcode_Request br);

}
