package br.com.listadecompras;

import android.Manifest;
import android.app.Application;

import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.utils.Permissions;
import io.realm.Realm;

    public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

    }
}
