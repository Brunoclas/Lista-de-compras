package br.com.listadecompras.realm;

import android.util.Log;

import br.com.listadecompras.model.ProdutoList;
import br.com.listadecompras.model.ProdutoRealm;
import io.realm.DynamicRealm;
import io.realm.MutableRealmInteger;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import io.realm.Sort;

public class ConfRealm {
    private RealmConfiguration realmConfiguration;
    public ConfRealm() {
        realmConfiguration = new RealmConfiguration.Builder()
                .name("bdProdutos.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public Realm realm(){
        Realm realm = Realm.getInstance(realmConfiguration);
        return realm;
    }

    public Long autoIncrementIdProduto(){
        Long key = 1L;
        try {
            key = realm().where(ProdutoRealm.class).max("id").longValue() + 1;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return key;
    }

    public Long autoIncrementIdProdutoLista(){
        Long key = 1L;
        try {
            key = realm().where(ProdutoList.class).max("id").longValue() + 1;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return key;
    }

    public void deleteRealm(){
        Realm.deleteRealm(realmConfiguration);
    }

    public RealmResults<ProdutoRealm> recebeProdutos(){
        RealmResults<ProdutoRealm> produtoRealms = realm().where(ProdutoRealm.class).findAll();
        Log.i("Response", produtoRealms.toString());
        return  produtoRealms;
    }

    public RealmResults<ProdutoList> recebeListaListaProdutos(){
        RealmResults<ProdutoList> produtoLists = realm().where(ProdutoList.class).findAll();
        Log.i("Response", produtoLists.toString());
        return  produtoLists;
    }

    public ProdutoRealm ultimoProduto(){
        ProdutoRealm produtoRealm = realm().where(ProdutoRealm.class).findFirst();
        Log.i("Response", produtoRealm.toString());
        return  produtoRealm;
    }

    public ProdutoList ultimaListaProduto(){
//        ProdutoList produtoList = realm().where(ProdutoList.class).findFirst();
//        Log.i("Response", produtoList.toString());
//        return  produtoList;

        RealmResults<ProdutoList> produtoLists = realm().where(ProdutoList.class).findAll().sort("id", Sort.ASCENDING);
        ProdutoList produtoList = new ProdutoList();
        for(int i = 0; i < produtoLists.size(); i++){
            produtoList = produtoLists.get(i);
        }
        Log.i("Response", produtoLists.toString());
        return  produtoList;

    }
}
