package br.com.listadecompras.acitivities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import br.com.listadecompras.R;
import br.com.listadecompras.adapters.ItensHistoricoAdapter;
import br.com.listadecompras.adapters.ProdutoAdapter;
import br.com.listadecompras.fragments.ListaProdutoFrgment;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ItensHistoricoActivity extends AppCompatActivity {
    private int position;
    private RecyclerView itensDetalhesHistorico;
    private ConfRealm confRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_historico);
        inicializaCampos();

    }

    private void inicializaCampos() {
        itensDetalhesHistorico = findViewById(R.id.listaDetalhesHist);
    }

    private void preencheListaItemHistorico(){
        try {
            confRealm = new ConfRealm();
            RealmResults<ProdutoRealm> produtoRealms = confRealm.realm().where(ProdutoRealm.class).equalTo("id_lista", position).sort("id", Sort.DESCENDING).findAll();
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            staggeredGridLayoutManager.setReverseLayout(false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            gridLayoutManager.setReverseLayout(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.setStackFromEnd(true);
            linearLayoutManager.setReverseLayout(true);
            itensDetalhesHistorico.setLayoutManager(gridLayoutManager);
            ItensHistoricoAdapter itensHistoricoAdapter = new ItensHistoricoAdapter(produtoRealms);
            itensDetalhesHistorico.setAdapter(itensHistoricoAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        carregaFragment();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle b = getIntent().getExtras();
        position = b.getInt("position");
        preencheListaItemHistorico();
    }
}
