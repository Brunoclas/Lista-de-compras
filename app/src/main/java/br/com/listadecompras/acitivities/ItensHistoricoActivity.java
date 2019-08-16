package br.com.listadecompras.acitivities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.listadecompras.R;
import br.com.listadecompras.fragments.ListaProdutoFrgment;

public class ItensHistoricoActivity extends AppCompatActivity {
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_historico);
        inicializaCampos();
//        Bundle b = getIntent().getExtras();
//        position = b.getInt("position");
//        carregaFragment();


    }

    private void inicializaCampos() {

    }

    private void carregaFragment() {
        Fragment listaProdFrag = ListaProdutoFrgment.newInstance(2, position);
        openFragment(listaProdFrag, R.id.fragItenHisto);
    }
    public void openFragment(Fragment fragment, int frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frag, fragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        carregaFragment();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle b = getIntent().getExtras();
        position = b.getInt("position");
        carregaFragment();
    }
}
