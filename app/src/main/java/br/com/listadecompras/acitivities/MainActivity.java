package br.com.listadecompras.acitivities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;

import br.com.listadecompras.R;
import br.com.listadecompras.fragments.ListaProduto;
import br.com.listadecompras.model.Produto;
import br.com.listadecompras.webservices.IService;
import br.com.listadecompras.webservices.UrlUtils;
import br.com.listadecompras.zxing.client.android.CaptureActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btnLerCodigo;
    private TextInputEditText txtInpCodigo;
    private String gtin;
    private IService iService;
    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializaComponentes();
        Fragment listaProdFrag = ListaProduto.newInstance();
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
                    Call<Produto> callProd = UrlUtils.getService().recuperaProd(gtin);
                    callProd.enqueue(new Callback<Produto>() {
                        @Override
                        public void onResponse(Call<Produto> call, Response<Produto> response) {
                            if (response.isSuccessful()) {
                                Produto produto = response.body();
                                Log.i("Response", produto.toString());
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("produto", produto);
                                Intent i = new Intent(MainActivity.this, ProdutoActivity.class);
                                i.putExtras(bundle);
                                startActivity(i);
                            }else{
                                Toast.makeText(MainActivity.this, "Produto nāo encontrado", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Produto> call, Throwable t) {
                            t.getStackTrace();
                            Log.i("ResponseError", t.getMessage());
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("ResponseError", e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            gtin = data.getStringExtra("dado");
            txtInpCodigo.setText(gtin);
            Log.i("Response", gtin);

            try {
                Call<Produto> callProd = UrlUtils.getService().recuperaProd(gtin);
                callProd.enqueue(new Callback<Produto>() {
                    @Override
                    public void onResponse(Call<Produto> call, Response<Produto> response) {
                        if (response.isSuccessful()) {
                            Produto produto = response.body();
                            Log.i("Response", produto.toString());
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("produto", produto);
                            Intent i = new Intent(MainActivity.this, ProdutoActivity.class);
                            i.putExtras(bundle);
                            startActivity(i);
                        }else{
                            Toast.makeText(MainActivity.this, "Produto nāo encontrado", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Produto> call, Throwable t) {
                        t.getStackTrace();
                        Log.i("ResponseError", t.getMessage());
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
                Log.i("ResponseError", e.getMessage());
            }

        }
    }
}
