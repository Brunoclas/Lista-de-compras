package br.com.listadecompras.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.utils.Utils;
import br.com.listadecompras.webservices.UrlUtils;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoViewModel extends ViewModel {
    private MutableLiveData<RealmResults<ProdutoRealm>> produtos;
    private MutableLiveData<ProdutoRealm> produto;
    private ConfRealm confRealm;

    private MutableLiveData<Double> vl_total;
    private long qtde_total = 0;
    private double vlTotal = 0;

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

    public LiveData<Double> getFinalizaProd(){
        if (vl_total == null) {
            vl_total = new MutableLiveData<Double>();
            finalizaListaCompras();
        }
        return vl_total;
    }

    private void finalizaListaCompras() {
        confRealm = new ConfRealm();
        if (confRealm.ultimaListaProduto().getStatus().equals(Utils.EM_PROCESSAMENTO)) {

            confRealm.realm().beginTransaction();
            qtde_total = 0;
            RealmResults<ProdutoRealm> produtoRealms = confRealm.realm()
                    .where(ProdutoRealm.class)
                    .equalTo("status", Utils.EM_PROCESSAMENTO)
                    .findAll();
            for (int i = 0; i < produtoRealms.size(); i++) {
                vl_total.setValue(vlTotal += produtoRealms.get(i).getVl_total());
            }
            qtde_total += produtoRealms.size();
            produtoRealms.setLong("id_lista", confRealm.ultimaListaProduto().getId());
            produtoRealms.setString("status", Utils.FECHADO);

//          confRealm.realm().copyToRealm(produtoRealms);
            confRealm.realm().copyToRealmOrUpdate(produtoRealms);
            confRealm.realm().commitTransaction();
            confRealm.realm().close();
        }

    }
}
