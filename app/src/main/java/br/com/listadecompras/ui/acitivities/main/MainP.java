package br.com.listadecompras.ui.acitivities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.ui.acitivities.produto.ProdutoActivity;
import br.com.listadecompras.utils.Utils;
import br.com.listadecompras.viewmodel.HistoricoViewModel;
import br.com.listadecompras.viewmodel.ProdutoViewModel;
import br.com.listadecompras.webservices.UrlUtils;
import br.com.listadecompras.zxing.client.android.CaptureActivity;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainP implements MainC.Presenter {

    private MainC.View view;
    private double vlTotal = 0;
    private ConfRealm confRealm;
    RealmResults<ProdutoRealm> produtos;
    private String gtin;

    public MainP(MainC.View view) {
        this.view = view;
        confRealm = new ConfRealm();
    }

    @Override
    public void callBarcodeReader() {
        view.callBarcodeReader();
    }

    @Override
    public void loadBar() {
        view.loadBar();
    }

    @Override
    public void toastAlert(String msg) {
        view.toastAlert(msg);
    }

    @Override
    public void calcData() {
        try {
            vlTotal = 0;
            if (!confRealm.ultimaListaProduto().getStatus().equals(Utils.FECHADO)) {

                produtos = confRealm.realm().where(ProdutoRealm.class).equalTo("status", Utils.EM_PROCESSAMENTO).findAll();
                if (produtos.size() > 0) {
                    for (int i = 0; i < produtos.size(); i++) {
                        vlTotal += produtos.get(i).getVl_total();
                    }
                    view.calcData(produtos, vlTotal);
                }
            } else {
                vlTotal = 0;
                view.calcData(produtos, vlTotal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchGtin(String gtin, Activity activity) {
        try{
            this.gtin = gtin;
            if (!gtin.equals("") && gtin.length() > 0 && !gtin.equals(null)) {

//                ProdutoViewModel model = ViewModelProviders.of((FragmentActivity) activity).get(ProdutoViewModel.class);
//                model.getProduto(gtin).observeForever(produto -> {
//                    view.searchGtin(produto);
//                });

                Call<ProdutoRealm> callProd = UrlUtils.getService().recuperaProd(gtin);
                callProd.enqueue(new Callback<ProdutoRealm>() {
                    @Override
                    public void onResponse(Call<ProdutoRealm> call, Response<ProdutoRealm> response) {
                        if (response.isSuccessful()) {
                            ProdutoRealm produto = response.body();
                            Log.i("Response", produto.toString());
                            view.searchGtin(produto);
                        }else{
                            view.toastAlert("Produto nāo encontrado");
                        }
                    }

                    @Override
                    public void onFailure(Call<ProdutoRealm> call, Throwable t) {
                        t.getStackTrace();
                        Log.i("ResponseError", t.getMessage());
                    }
                });
            }else {
                view.toastAlert("Campo de busca vazio !");
            }

        }catch (Exception e){
            e.getMessage();
            e.getStackTrace();
        }
    }

    @Override
    public void checkOutProducts(Activity activity) {
        saveList(activity);
    }

    @Override
    public boolean callItensMenu(MenuItem item) {
        return view.callItensMenu(item);
    }

    private void saveList(Activity activity){
        ProdutoViewModel model = ViewModelProviders.of((FragmentActivity) activity).get(ProdutoViewModel.class);
        model.getFinalizaProd().observeForever(vl_total -> {
            vlTotal = vl_total;
        });

        HistoricoViewModel model1 = ViewModelProviders.of((FragmentActivity) activity).get(HistoricoViewModel.class);
        model1.getSalvaLista(vlTotal);
    }

}
