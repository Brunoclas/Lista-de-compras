package br.com.listadecompras.ui.acitivities.main;

import android.app.Activity;
import android.view.MenuItem;

import br.com.listadecompras.model.ProdutoRealm;
import io.realm.RealmResults;

public interface MainC {
    interface View{
        void callBarcodeReader();
        void loadBar();
        void toastAlert(String msg);
        void calcData(RealmResults<ProdutoRealm> produtos, double vlTotal );
        void searchGtin(ProdutoRealm produto);
        boolean callItensMenu(MenuItem item);
    }
    interface Presenter{
        void callBarcodeReader();
        void loadBar();
        void toastAlert(String msg);
        void calcData();
        void searchGtin(String gtin, Activity activity);
        void checkOutProducts(Activity activity);
        boolean callItensMenu(MenuItem item);
    }

}
