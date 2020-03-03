package br.com.listadecompras.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.listadecompras.model.Historico;
import br.com.listadecompras.model.ProdutoList;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.utils.Utils;
import io.realm.RealmResults;

public class HistoricoViewModel extends ViewModel {

    private MutableLiveData<RealmResults<Historico>> hitoricos;
    private MutableLiveData<Historico> historico;
    private ConfRealm confRealm;
    private double vl_total;
    private long qtde_total;

    public void getSalvaLista(double vlTotal){
        salvaLista(vlTotal);
    }

    private void salvaLista(double vlTotal) {
        vl_total = 0;
        qtde_total = 0;
        confRealm = new ConfRealm();
        if (confRealm.ultimaListaProduto().getStatus().equals(Utils.EM_PROCESSAMENTO)) {

            confRealm.realm().beginTransaction();
//            produtoList = new ProdutoList();
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

            RealmResults<ProdutoList> produtoListss = confRealm.realm()
                    .where(ProdutoList.class)
                    .equalTo("status", Utils.EM_PROCESSAMENTO)
                    .findAll();
            qtde_total = produtoListss.size() + 1;
            produtoListss.setLong("qtde_itens", qtde_total);
            produtoListss.setDouble    ("vl_total", vlTotal);
            produtoListss.setString("dt_finalizacao", date);
            produtoListss.setString("status", Utils.FECHADO);

//                    confRealm.realm().copyToRealm(produtoLists);
            confRealm.realm().copyToRealmOrUpdate(produtoListss);
            confRealm.realm().commitTransaction();
            confRealm.realm().close();
        }

    }

}
