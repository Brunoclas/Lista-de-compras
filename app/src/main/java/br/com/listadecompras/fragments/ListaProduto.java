package br.com.listadecompras.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.listadecompras.R;
import br.com.listadecompras.adapters.ProdutoAdapter;
import br.com.listadecompras.model.Produto;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import io.realm.RealmResults;


public class ListaProduto extends Fragment implements ProdutoAdapter.OnClickProdListener {

    private RecyclerView prodRecycler;
    private ConfRealm confRealm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_lista_produto, container, false);

        confRealm = new ConfRealm();
        //carregaDados();

        prodRecycler = v.findViewById(R.id.prodRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        prodRecycler.setLayoutManager(linearLayoutManager);

        ProdutoAdapter produtoAdapter = new ProdutoAdapter(carregaDados(), this);
        prodRecycler.setAdapter(produtoAdapter);

        return v;
    }

    private RealmResults<ProdutoRealm> carregaDados() {
        RealmResults<ProdutoRealm> produtoRealms = null ;
        try {
            produtoRealms = confRealm.realm().where(ProdutoRealm.class).findAll();
            
            for(int i = 0; i <= produtoRealms.size(); i++) {
                Log.i("Response", produtoRealms.get(i).toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  produtoRealms;
    }

    public static ListaProduto newInstance(){
        return new ListaProduto();
    }

    @Override
    public void onClickProdListener(int position) {

    }
}
