package br.com.listadecompras.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.listadecompras.R;
import br.com.listadecompras.adapters.HistoricoAdapter;
import br.com.listadecompras.adapters.ProdutoAdapter;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.utils.Utils;
import io.realm.RealmResults;
import io.realm.Sort;
import okhttp3.internal.Util;


public class ListaProdutoFrgment extends Fragment implements ProdutoAdapter.OnClickProdListener {

    private RecyclerView prodRecycler;
    private ConfRealm confRealm;
    private static int activity, position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_lista_produto, container, false);

        confRealm = new ConfRealm();
        prodRecycler = v.findViewById(R.id.prodRecycler);

        return v;
    }

    private void preencheLista() {

        try {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            staggeredGridLayoutManager.setReverseLayout(false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            gridLayoutManager.setReverseLayout(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.setStackFromEnd(true);
            linearLayoutManager.setReverseLayout(true);
            prodRecycler.setLayoutManager(gridLayoutManager);
            ProdutoAdapter produtoAdapter = new ProdutoAdapter(carregaDados(), this);
            prodRecycler.setAdapter(produtoAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void preencheListaItemHistorico(){
        try {
            RealmResults<ProdutoRealm> produtoRealms = confRealm.realm().where(ProdutoRealm.class).equalTo("id_lista", position).sort("id", Sort.DESCENDING).findAll();
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            staggeredGridLayoutManager.setReverseLayout(false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            gridLayoutManager.setReverseLayout(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.setStackFromEnd(true);
            linearLayoutManager.setReverseLayout(true);
            prodRecycler.setLayoutManager(gridLayoutManager);
            ProdutoAdapter produtoAdapter = new ProdutoAdapter(produtoRealms, this);
            prodRecycler.setAdapter(produtoAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private RealmResults<ProdutoRealm> carregaDados() {
        RealmResults<ProdutoRealm> produtoRealms = null ;
        try {
            if(!confRealm.ultimaListaProduto().getStatus().equals(Utils.FECHADO)) {
                produtoRealms = confRealm.realm().where(ProdutoRealm.class).equalTo("status", Utils.EM_PROCESSAMENTO).findAll().sort("dataCad", Sort.DESCENDING);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  produtoRealms;
    }

    public static ListaProdutoFrgment newInstance(int _activity, int _position){
        activity = _activity;
        position = _position;
        return new ListaProdutoFrgment();
    }

    @Override
    public void onClickProdListener(int position) {

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(activity == 1) {
            preencheLista();
        }else if(activity == 2){
            preencheListaItemHistorico();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void alerta(String msg){
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }


}
