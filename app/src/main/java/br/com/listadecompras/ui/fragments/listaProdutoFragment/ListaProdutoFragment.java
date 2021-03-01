package br.com.listadecompras.ui.fragments.listaProdutoFragment;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.listadecompras.R;
import br.com.listadecompras.adapters.ProdutoAdapter;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.utils.Utils;
import br.com.listadecompras.viewmodel.ProdutoViewModel;
import io.realm.RealmResults;


public class ListaProdutoFragment extends Fragment implements ProdutoAdapter.OnClickProdListener {

    private RecyclerView prodRecycler;
    private ConfRealm confRealm;
    private static int activity, position;
    private RealmResults<ProdutoRealm> produtoRealms;

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
            ProdutoAdapter produtoAdapter = new ProdutoAdapter(carregaDados(), this, getActivity());
            prodRecycler.setAdapter(produtoAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private RealmResults<ProdutoRealm> carregaDados() {
        ProdutoViewModel produtoViewModel;
        try {
            if(!confRealm.ultimaListaProduto().getStatus().equals(Utils.FECHADO)) {
                ProdutoViewModel model = ViewModelProviders.of(this).get(ProdutoViewModel.class);

                 model.getListaProduto().observe(this, produtos -> {
                     produtoRealms = produtos;
                 });
//                produtoRealms = confRealm.realm().where(ProdutoRealm.class).equalTo("status", Utils.EM_PROCESSAMENTO).findAll().sort("dataCad", Sort.DESCENDING);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  produtoRealms;
    }

    public static ListaProdutoFragment newInstance(int _activity, int _position){
        activity = _activity;
        position = _position;
        return new ListaProdutoFragment();
    }

    @Override
    public void onClickProdListener(int position) {

    }

    @Override
    public void onStart() {
        super.onStart();
//        alerta("ListaProdutoFrgment - onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
//        alerta("ListaProdutoFrgment - onResume");
            preencheLista();
    }

    @Override
    public void onPause() {
        super.onPause();
//        alerta("ListaProdutoFrgment - onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
//        alerta("ListaProdutoFrgment - onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        alerta("ListaProdutoFrgment - onDestroy");
    }

    private void alerta(String msg){
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
