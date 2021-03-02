package br.com.listadecompras.ui.acitivities.main;

import android.Manifest;
import android.app.Activity;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.listadecompras.R;
import br.com.listadecompras.ui.acitivities.historico.HistoricoActivity;
import br.com.listadecompras.ui.acitivities.produto.ProdutoActivity;
import br.com.listadecompras.ui.fragments.listaProdutoFragment.ListaProdutoFragment;
import br.com.listadecompras.model.ProdutoList;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.utils.Permissions;
import br.com.listadecompras.utils.Utils;
import br.com.listadecompras.viewmodel.HistoricoViewModel;
import br.com.listadecompras.viewmodel.ProdutoViewModel;
import br.com.listadecompras.webservices.IService;
import br.com.listadecompras.webservices.UrlUtils;
import br.com.listadecompras.zxing.client.android.CaptureActivity;
import br.com.listadecompras.zxing.client.android.PreferencesActivity;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainC.View{

    private FloatingActionButton btnLerCodigo;
    private TextInputEditText txtInpCodigo;
    private String gtin;
    private Button btnBuscar, btnFinalizarCompras;
    private ProdutoRealm produtoRealm;
    private TextView txtVlTotalLista, txtQtdeItem;

    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.VIBRATE
    };
    private ProdutoList produtoList;
    private Toolbar hometbr;


    private MainP presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permissions.validatePermission(permissoes, this, 1);
        presenter = new MainP(this);
        inicializaComponentes();
        events();
        presenter.loadBar();

//        carregaFragment();
//        confRealm.deleteRealm();
    }

    private void events() {
        btnLerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.callBarcodeReader();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    gtin = txtInpCodigo.getText().toString();
                    presenter.searchGtin(gtin, MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnFinalizarCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    produtoList = new ProdutoList();
                    presenter.checkOutProducts(MainActivity.this);
                    carregaFragment();
                    presenter.calcData();

                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.callItensMenu(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    private void carregaFragment() {
        Fragment listaProdFrag = ListaProdutoFragment.newInstance(1, 0);
        openFragment(listaProdFrag, R.id.fragListProd);
    }


    public void openFragment(Fragment fragment, int frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frag, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void inicializaComponentes() {
        btnLerCodigo = findViewById(R.id.btnLerCodigo);
        txtInpCodigo = findViewById(R.id.txtInpCodigo);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnFinalizarCompras = findViewById(R.id.btnFinalizarCompras);
        txtQtdeItem = findViewById(R.id.txtQtdeItem);
        txtVlTotalLista = findViewById(R.id.txtVlTotalLista);
        hometbr = findViewById(R.id.hometbr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            gtin = data.getStringExtra("dado");
            txtInpCodigo.setText(gtin);
            Log.i("Response-gtin", gtin);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            try {
                produtoRealm = data.getParcelableExtra("produtoRealm");
                Log.i("Response", produtoRealm.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaFragment();
        presenter.calcData();
        txtInpCodigo.setText("");
        gtin = "";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void callBarcodeReader() {
        startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 1);
    }

    @Override
    public void loadBar() {
        hometbr.setTitle("Lista de Compras");
        setSupportActionBar(hometbr);
    }

    @Override
    public void toastAlert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void calcData(RealmResults<ProdutoRealm> produtos, double vlTotal ) {
        txtQtdeItem.setText("Itens: " + produtos.size());
        txtVlTotalLista.setText("Total: R$ " + String.format("%.2f", vlTotal));
    }

    @Override
    public void searchGtin(ProdutoRealm produto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("produto", produto);
        Intent i = new Intent(MainActivity.this, ProdutoActivity.class);
        i.putExtras(bundle);
        startActivityForResult(i, 2);
    }

    @Override
    public boolean callItensMenu(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.historico:
                startActivity(new Intent(MainActivity.this, HistoricoActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.menu_settings:
                Intent intent = new Intent();
                intent.setClassName(this, PreferencesActivity.class.getName());
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
        return true;
    }
}
