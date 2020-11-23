package br.com.listadecompras.ui.acitivities.historico;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;

import br.com.listadecompras.R;
import br.com.listadecompras.adapters.HistoricoAdapter;
import br.com.listadecompras.model.ProdutoList;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.ui.acitivities.itensHistorico.ItensHistoricoActivity;
import br.com.listadecompras.utils.Utils;
import io.realm.RealmResults;
import io.realm.Sort;

public class HistoricoActivity extends AppCompatActivity implements HistoricoAdapter.OnClickHistoListener {
    private RecyclerView recyclerView;
    private ConfRealm confRealm;
    private Toolbar historicoTbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        inicializaComponentes();
        carregaToolbar();
        confRealm = new ConfRealm();
        preencheLista();
    }

    private void carregaToolbar() {
        historicoTbr.setTitle("Historico");
        setSupportActionBar(historicoTbr);
    }

    private void preencheLista() {
        try {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            staggeredGridLayoutManager.setReverseLayout(false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            gridLayoutManager.setReverseLayout(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.setStackFromEnd(true);
            linearLayoutManager.setReverseLayout(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            HistoricoAdapter historicoAdapter = new HistoricoAdapter(carregaDados(), this);
            recyclerView.setAdapter(historicoAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void inicializaComponentes() {
        recyclerView = findViewById(R.id.historicoRecycler);
        historicoTbr = findViewById(R.id.historicoTbr);
    }


    private RealmResults<ProdutoList> carregaDados() {
        RealmResults<ProdutoList> produtoLists = null ;
        try {
//            if(confRealm.ultimaListaProduto().getStatus().equals(Utils.FECHADO)) {
                produtoLists = confRealm.realm().where(ProdutoList.class).equalTo("status", Utils.FECHADO).findAll().sort("id", Sort.ASCENDING);

//            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  produtoLists;
    }

    @Override
    public void onClickHistoListener(int position) {
        Bundle b = new Bundle();
        Intent i = new Intent(this, ItensHistoricoActivity.class);
        b.putInt("position", position);
        i.putExtras(b);
        startActivity(i);
    }
}
