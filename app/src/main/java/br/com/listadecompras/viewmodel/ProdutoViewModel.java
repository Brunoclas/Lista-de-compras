package br.com.listadecompras.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import br.com.listadecompras.model.Produto;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.utils.Utils;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ProdutoViewModel extends ViewModel {
    private MutableLiveData<RealmResults<ProdutoRealm>> produtos;
    private ConfRealm confRealm;
    public LiveData<RealmResults<ProdutoRealm>> getProduto(){
        if(produtos == null){
            produtos = new MutableLiveData<RealmResults<ProdutoRealm>>();
            loadProduto();
        }
        return produtos;
    }

    private void loadProduto() {
        confRealm = new ConfRealm();
        RealmResults<ProdutoRealm> prod = confRealm.realm().where(ProdutoRealm.class).equalTo("status", Utils.EM_PROCESSAMENTO).findAll().sort("dataCad", Sort.DESCENDING);
        produtos.setValue(prod);

    }
}
