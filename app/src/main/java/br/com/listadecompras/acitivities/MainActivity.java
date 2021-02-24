package br.com.listadecompras.acitivities;

import android.Manifest;
import android.app.Activity;

import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ProcessLifecycleOwner;
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

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import br.com.listadecompras.R;
import br.com.listadecompras.fragments.ListaProdutoFrgment;
import br.com.listadecompras.model.Barcode_Request;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btnLerCodigo;
    private TextInputEditText txtInpCodigo;
    private String gtin;
    private IService iService;
    private Button btnBuscar, btnFinalizarCompras;
    private ProdutoRealm produtoRealm;
    private TextView txtVlTotalLista, txtQtdeItem;
    private ConfRealm confRealm;
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.VIBRATE
    };
    private ProdutoList produtoList;
    private Toolbar hometbr;
    private double vl_total = 0;
    private long qtde_total = 0;
    private double vlTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permissions.validatePermission(permissoes, this, 1);
        inicializaComponentes();
        carregaToobar();
//        carregaFragment();
        confRealm = new ConfRealm();
//        confRealm.deleteRealm();
    }

    private void carregaToobar() {
        hometbr.setTitle("Lista de Compras");
        setSupportActionBar(hometbr);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
//       hometbr.inflateMenu(R.menu.menu_home);

        return true;
    }

    private void carregaFragment() {
        Fragment listaProdFrag = ListaProdutoFrgment.newInstance(1, 0);
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
        btnLerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 1);
            }
        });

        txtInpCodigo = findViewById(R.id.txtInpCodigo);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    gtin = txtInpCodigo.getText().toString();

                    if(!gtin.equals("") && gtin.length() > 0 && !gtin.equals(null)) {

                        ProdutoViewModel model = ViewModelProviders.of(MainActivity.this).get(ProdutoViewModel.class);
                            model.getProduto(gtin).observeForever(produto -> {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("produto", produto);
                            Intent i = new Intent(MainActivity.this, ProdutoActivity.class);
                            i.putExtras(bundle);
                            startActivityForResult(i, 2);
                        });

                        Barcode_Request barR = new Barcode_Request();
                        barR.setPassport("400000000");
                        barR.setBarcode(gtin);

                        System.out.println(barR.toString());

//                        Call<ResponseBody> callProd = UrlUtils.getService().recuperaProd(barR);
//                        callProd.enqueue(new Callback<ResponseBody>() {
//                            @Override
//                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                if(response.isSuccessful()){
//                                    String res = null;
//                                    try {
//                                        res = response.body().string();
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                    System.out.println(res);
//                                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();
//                                }else{
//                                    Toast.makeText(MainActivity.this, "Produto nāo encontrado", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                            @Override
//                            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                t.getStackTrace();
//                                Log.i("ResponseError", t.getMessage());
//                            }
//                        });

//                    Call<Produto> callProd = UrlUtils.getService().recuperaProd(gtin);
//                    callProd.enqueue(new Callback<Produto>() {
//                        @Override
//                        public void onResponse(Call<Produto> call, Response<Produto> response) {
//                            if (response.isSuccessful()) {
//                                Produto produto = response.body();
//                                Log.i("Response", produto.toString());
//                                Bundle bundle = new Bundle();
//                                bundle.putParcelable("produto", produto);
//                                Intent i = new Intent(MainActivity.this, ProdutoActivity.class);
//                                i.putExtras(bundle);
//                                startActivityForResult(i, 2);
//                            }else{
//                                Toast.makeText(MainActivity.this, "Produto nāo encontrado", Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Produto> call, Throwable t) {
//                            t.getStackTrace();
//                            Log.i("ResponseError", t.getMessage());
//                        }
//                    });

                    }else{
                        alerta("Campo de busca vazio !");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("ResponseError", e.getMessage());
                }
            }
        });
        btnFinalizarCompras = findViewById(R.id.btnFinalizarCompras);
        btnFinalizarCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    produtoList = new ProdutoList();

                    ProdutoViewModel model = ViewModelProviders.of(MainActivity.this).get(ProdutoViewModel.class);
                    model.getFinalizaProd().observeForever( vl_total -> {
                        vlTotal = vl_total;
                    });

                    HistoricoViewModel model1 = ViewModelProviders.of(MainActivity.this).get(HistoricoViewModel.class);
                    model1.getSalvaLista(vlTotal);

//                final ConfRealm confRealm = new ConfRealm();

//                if(confRealm.ultimaListaProduto().getStatus().equals(Utils.EM_PROCESSAMENTO)){

//                    confRealm.realm().beginTransaction();
//                    vl_total = 0;
//                    qtde_total = 0;
//                    RealmResults<ProdutoRealm> produtoRealms = confRealm.realm()
//                            .where(ProdutoRealm.class)
//                            .equalTo("status", Utils.EM_PROCESSAMENTO)
//                            .findAll();
//                    for(int i = 0; i < produtoRealms.size(); i++){
//                        vl_total += produtoRealms.get(i).getVl_total();
//                    }
//                    qtde_total += produtoRealms.size();
//                    produtoRealms.setLong("id_lista", confRealm.ultimaListaProduto().getId());
//                    produtoRealms.setString("status", Utils.FECHADO);
//
////                   confRealm.realm().copyToRealm(produtoRealms);
//                    confRealm.realm().copyToRealmOrUpdate(produtoRealms);
//                    confRealm.realm().commitTransaction();
//                    confRealm.realm().close();

//                    confRealm.realm().beginTransaction();
//                    produtoList = new ProdutoList();
//                    String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
//
//                    RealmResults<ProdutoList> produtoListss = confRealm.realm()
//                            .where(ProdutoList.class)
//                            .equalTo("status", Utils.EM_PROCESSAMENTO)
//                            .findAll();
//                    produtoListss.setLong("qtde_itens", qtde_total);
//                    produtoListss.setDouble    ("vl_total", vl_total);
//                    produtoListss.setString("dt_finalizacao", date);
//                    produtoListss.setString("status", Utils.FECHADO);
//
////                    confRealm.realm().copyToRealm(produtoLists);
//                    confRealm.realm().copyToRealmOrUpdate(produtoListss);
//                    confRealm.realm().commitTransaction();
//                    confRealm.realm().close();

                    carregaFragment();
                    calculaDados();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });
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

            try {
                Barcode_Request barR = new Barcode_Request();
                barR.setPassport("400000000");
                barR.setBarcode(gtin);

                System.out.println(barR.toString());

//                Call<ResponseBody> callProd = UrlUtils.getService().recuperaProd(barR);
//                callProd.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if(response.isSuccessful()){
//                            Log.i("Response", "entrou na chamada");
//                            String res = null;
//                            try {
//                                res = response.body().string();
//                                Log.i("Response-request", res);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            System.out.println(res);
//                            Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();
//                        }else{
//                            Toast.makeText(MainActivity.this, "Produto nāo encontrado", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        t.getStackTrace();
//                        Log.i("ResponseError", t.getMessage());
//                    }
//                });
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("ResponseError", e.getMessage());
            }

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

    public void calculaDados() {
        try {
            vlTotal = 0;
            if (!confRealm.ultimaListaProduto().getStatus().equals(Utils.FECHADO)) {

                RealmResults<ProdutoRealm> produtos = confRealm.realm().where(ProdutoRealm.class).equalTo("status", Utils.EM_PROCESSAMENTO).findAll();
                if (produtos.size() > 0) {
                    txtQtdeItem.setText("Itens: " + produtos.size());
                    for (int i = 0; i < produtos.size(); i++) {
                        vlTotal += produtos.get(i).getVl_total();
                    }
                    txtVlTotalLista.setText("Total: R$ " + String.format("%.2f", vlTotal));
                }
            } else {
                txtVlTotalLista.setText("Total: R$ " + String.format("%.2f", 0.00));
                txtQtdeItem.setText("Itens: " + 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        alerta("MainActivity - onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
//        alerta("MainActivity - onResume");
        carregaFragment();
        calculaDados();
        txtInpCodigo.setText("");
        gtin = "";
    }

    @Override
    public void onPause() {
        super.onPause();
//        alerta("MainActivity - onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
//        alerta("MainActivity - onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        alerta("MainActivity - onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        carregaFragment();
        finish();
    }

    private void alerta(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
