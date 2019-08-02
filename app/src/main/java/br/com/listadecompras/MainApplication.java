package br.com.listadecompras;

import android.app.Application;

import br.com.listadecompras.realm.ConfRealm;
import io.realm.Realm;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
//        new ConfRealm();
    }
}
