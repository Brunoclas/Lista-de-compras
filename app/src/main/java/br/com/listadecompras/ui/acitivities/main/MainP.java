    package br.com.listadecompras.ui.acitivities.main;

    import br.com.listadecompras.model.ProdutoRealm;
    import br.com.listadecompras.realm.ConfRealm;
    import br.com.listadecompras.utils.Utils;
    import io.realm.Realm;
    import io.realm.RealmResults;

    public class MainP implements MainC.Presenter {
    private Realm realm;
    private ConfRealm confRealm;
    private MainC.View view;

    public MainP(MainC.View view) {
        this.view = view;
        confRealm = new ConfRealm();
        realm = confRealm.realm();
    }

    @Override
    public void loadProducts() {
        RealmResults<ProdutoRealm> produtos = null;
        if (!confRealm.ultimaListaProduto().getStatus().equals(Utils.FECHADO)) {
            produtos = confRealm.realm().where(ProdutoRealm.class).equalTo("status", Utils.EM_PROCESSAMENTO).findAll();
        }
        view.loadProducts(produtos);
    }
}
