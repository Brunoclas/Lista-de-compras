package br.com.listadecompras.realm;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ConfRealm {
    private RealmConfiguration realmConfiguration;
    public ConfRealm() {
        realmConfiguration = new RealmConfiguration.Builder().build();
    }

    public Realm realm(){
        Realm realm = Realm.getInstance(realmConfiguration);
        return realm;
    }
}
