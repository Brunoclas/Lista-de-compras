package br.com.listadecompras.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import br.com.listadecompras.acitivities.MainActivity;
import br.com.listadecompras.acitivities.ProdutoActivity;
import br.com.listadecompras.model.Produto;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.utils.Utils;
import br.com.listadecompras.webservices.UrlUtils;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoViewModel extends ViewModel {
    private MutableLiveData<RealmResults<ProdutoRealm>> produtos;
    private MutableLiveData<ProdutoRealm> produto;
    private ConfRealm confRealm;

    public LiveData<RealmResults<ProdutoRealm>> getListaProduto(){
        if(produtos == null){
            produtos = new MutableLiveData<RealmResults<ProdutoRealm>>();
            loadListaProduto();
        }
        return produtos;
    }

    private void loadListaProduto() {
        confRealm = new ConfRealm();
        RealmResults<ProdutoRealm> prods = confRealm.realm().where(ProdutoRealm.class).equalTo("status", Utils.EM_PROCESSAMENTO).findAll().sort("dataCad", Sort.DESCENDING);
        produtos.setValue(prods);
    }

    public LiveData<ProdutoRealm> getProduto(String gtin){
        if(produto == null){
            produto = new MutableLiveData<ProdutoRealm>();
            loadProduto(gtin);
        }
        return produto;
    }

    private void loadProduto(String gtin){
        try {
            Call<ProdutoRealm> callProd = UrlUtils.getService().recuperaProd(gtin);
            callProd.enqueue(new Callback<ProdutoRealm>() {
                @Override
                public void onResponse(Call<ProdutoRealm> call, Response<ProdutoRealm> response) {
                    if (response.isSuccessful()) {
                        ProdutoRealm prod = response.body();
                        Log.i("Response", produto.toString());
                        produto.setValue(prod);

                    }else{
//                        Toast.makeText(MainActivity.this, "Produto nāo encontrado", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ProdutoRealm> call, Throwable t) {
                    t.getStackTrace();
                    Log.i("ResponseError", t.getMessage());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.i("ResponseError", e.getMessage());
        }
    }


}
