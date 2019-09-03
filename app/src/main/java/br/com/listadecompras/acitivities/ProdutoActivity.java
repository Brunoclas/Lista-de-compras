package br.com.listadecompras.acitivities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.listadecompras.R;
import br.com.listadecompras.model.Produto;
import br.com.listadecompras.model.ProdutoList;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.utils.Utils;
import br.com.listadecompras.zxing.Result;
import io.realm.MutableRealmInteger;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import okhttp3.internal.Util;

public class ProdutoActivity extends AppCompatActivity {

    private TextView txtNomeProd;
    private ImageView imgProd, imgCodBarras;
    private RadioButton rdBtnKg, rdBtnLt, rdBtnUn;
    TextInputEditText edtInpQtde, edtInpValor;
    private RadioGroup rdGroup;
    private Button btnOk, btnCancelar;

    private ProdutoRealm produto;
    private ConfRealm confRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        inicializaComponentes();
        confRealm = new ConfRealm();
        recebeBundle();
        carregaDados();
        eventos();
    }

    private void eventos() {
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionaRadioButton();
                try {
                    if (!edtInpQtde.getText().equals("") || !edtInpQtde.getText().equals("")) {
                        if (rdBtnKg.isChecked() || rdBtnLt.isChecked() || rdBtnUn.isChecked()) {
//                        BigDecimal qtde = new BigDecimal(edtInpQtde.getText().toString());
                            produto.setQtde(Double.parseDouble((edtInpQtde.getText().toString())));
//                        BigDecimal preco = new BigDecimal(edtInpValor.getText().toString());
                            produto.setPreco(Double.parseDouble(edtInpValor.getText().toString()));
                            salvaDadosRealm(produto);
                            Log.i("Response", String.valueOf(produto.getQtde()));
                            Log.i("Response", String.valueOf(produto.getPreco()));
                        } else {
                            Toast.makeText(ProdutoActivity.this, "Selecionar a unidade de medida !", Toast.LENGTH_LONG).show();
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void salvaDadosRealm(final ProdutoRealm produto) {
        try {
            RealmResults<ProdutoRealm> produtoRealms = confRealm.realm().where(ProdutoRealm.class).findAll();

            if(produtoRealms.size() >= 0) {

//                produtoRealm1 = confRealm.realm().where(ProdutoRealm.class).equalTo("gtin", produto.getGtin()).findFirst();
//
//                if (produtoRealm1 == null) {

                ProdutoRealm produtoRealm = new ProdutoRealm();
                confRealm.realm().beginTransaction();
                if(produtoRealms.size() <= 0) {
                    produtoRealm.setId(1L);
                }else {
                    produtoRealm.setId(confRealm.autoIncrementIdProduto());
                }
                produtoRealm.setDescription(produto.getDescription());
                produtoRealm.setGtin(produto.getGtin());
                produtoRealm.setBarcode_image(produto.getBarcode_image());
                produtoRealm.setThumbnail(produto.getThumbnail());

                BigDecimal preco = new BigDecimal(String.valueOf(produto.getPreco()));
                produtoRealm.setPreco(preco.doubleValue());

                BigDecimal qtde = new BigDecimal(String.valueOf(produto.getQtde()));
                produtoRealm.setQtde(qtde.doubleValue());

                BigDecimal vlTotal = new BigDecimal(String.valueOf(produto.getPreco() * produto.getQtde()));
                produtoRealm.setVl_total(vlTotal.doubleValue());
                produto.setVl_total(vlTotal.doubleValue());
                String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                produtoRealm.setDataCad(date);

                produtoRealm.setUnidMed(produto.getUnidMed());
                produtoRealm.setStatus(Utils.EM_PROCESSAMENTO);

//                RealmResults<ProdutoList> produtoLists = confRealm.realm().where(ProdutoList.class).findAll();
//                Long id_lista = confRealm.realm().where(ProdutoList.class).max("id").longValue();
//                if(produtoLists.size() <= 0) {
//                    produtoRealm.setId_lista(1L);
//                }else if(produtoLists.size() > 0 && produtoLists.){
//
//                }

                confRealm.realm().copyToRealm(produtoRealm);
                confRealm.realm().commitTransaction();
                confRealm.realm().close();

                if(confRealm.recebeListaListaProdutos().size() <= 0){
                    salvaLista();
                }else if(!confRealm.ultimaListaProduto().getStatus().equals(Utils.EM_PROCESSAMENTO)){
                    salvaLista();
                }

                Bundle b = new Bundle();
                b.putParcelable("produtoRealm", produtoRealm);
                Intent i = new Intent();
                i.putExtras(b);
                setResult(RESULT_OK, i);
                finish();

//                } else {
//                    Toast.makeText(this, "Produto ja esta cadastrado.", Toast.LENGTH_LONG).show();
//                }
           }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void selectionaRadioButton() {
        switch (rdGroup.getCheckedRadioButtonId()) {
            case R.id.rdBtnUn:
                try {
                    produto.setUnidMed("Unidade");
                    Log.i("Response", produto.getUnidMed());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rdBtnLt:
                try {
                    produto.setUnidMed("Litro");
                    Log.i("Response", produto.getUnidMed());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rdBtnKg:
                try {
                    produto.setUnidMed("Kilos");
                    Log.i("Response", produto.getUnidMed());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:

        }
    }

    private void carregaDados() {
        txtNomeProd.setText(produto.getDescription());

        Picasso.get()
                .load(produto.getThumbnail())
                .resize(250, 350)
                .centerInside()
                .into(imgProd);

        Picasso.get()
                .load(produto.getBarcode_image())
                .resize(300, 150)
                .centerInside()
                .into(imgCodBarras);
    }

    private void recebeBundle() {
        Bundle getBundle = getIntent().getExtras();
        produto = getBundle.getParcelable("produto");
    }

    private void inicializaComponentes() {
        txtNomeProd = findViewById(R.id.txtNomeProd);
        imgProd = findViewById(R.id.imgProdList);
        imgCodBarras = findViewById(R.id.imgCodBarras);
        rdBtnKg = findViewById(R.id.rdBtnKg);
        rdBtnLt = findViewById(R.id.rdBtnLt);
        rdBtnUn = findViewById(R.id.rdBtnUn);
        rdGroup = findViewById(R.id.rdGroup);
        btnOk = findViewById(R.id.btnOk);
        btnCancelar = findViewById(R.id.btnCancelar);
        edtInpQtde = findViewById(R.id.edtInpQtde);
        edtInpValor = findViewById(R.id.edtInpValor);
    }

    private void salvaLista(){
        ProdutoList produtoList = new ProdutoList();
        RealmResults<ProdutoList> produtoLists = confRealm.recebeListaListaProdutos();
        confRealm.realm().beginTransaction();
        if(produtoLists.size() <= 0){
            produtoList.setId(1L);
        }else{
            produtoList.setId(confRealm.autoIncrementIdProdutoLista());
        }
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        produtoList.setDt_criacao(date);
        produtoList.setStatus(Utils.EM_PROCESSAMENTO);
        confRealm.realm().copyToRealm(produtoList);
        confRealm.realm().commitTransaction();
        confRealm.realm().close();
    }

}
