package br.com.listadecompras.ui.acitivities.main;

import br.com.listadecompras.model.ProdutoRealm;
import io.realm.RealmResults;

public interface MainC {

    interface View{
        public void loadProducts(RealmResults<ProdutoRealm> produtos);
    }

    interface Presenter{
        public void loadProducts();
    }

}
