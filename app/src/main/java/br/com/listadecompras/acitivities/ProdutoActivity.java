package br.com.listadecompras.acitivities;

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

import br.com.listadecompras.R;
import br.com.listadecompras.model.Produto;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;

public class ProdutoActivity extends AppCompatActivity {

    private TextView txtNomeProd;
    private ImageView imgProd, imgCodBarras;
    private RadioButton rdBtnKg, rdBtnLt, rdBtnUn;
    TextInputEditText edtInpQtde, edtInpValor;
    private RadioGroup rdGroup;
    private Button btnOk, btnCancelar;

    private Produto produto;
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
                if (!edtInpQtde.getText().equals("") || !edtInpQtde.getText().equals("")) {
                    if (rdBtnKg.isChecked() || rdBtnLt.isChecked() || rdBtnUn.isChecked()){
//                        BigDecimal qtde = new BigDecimal(edtInpQtde.getText().toString());
                        produto.setQtde(Double.parseDouble((edtInpQtde.getText().toString())));
//                        BigDecimal preco = new BigDecimal(edtInpValor.getText().toString());
                        produto.setPreco(Double.parseDouble(edtInpValor.getText().toString()));
                        salvaDadosRealm(produto);
                        Log.i("Response", String.valueOf(produto.getQtde()));
                        Log.i("Response", String.valueOf(produto.getPreco()));
                        finish();
                    } else {
                        Toast.makeText(ProdutoActivity.this, "Selecionar a unidade de medida !", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    private void salvaDadosRealm(Produto produto) {
        try {
            ProdutoRealm produtoRealm = new ProdutoRealm();
            produtoRealm.setDescription(produto.getDescription());
            produtoRealm.setGtin(produto.getGtin());
            produtoRealm.setBarcode_image(produto.getBarcode_image());
            produtoRealm.setThumbnail(produto.getThumbnail());

            BigDecimal preco = new BigDecimal(String.valueOf(produto.getPreco()));
            produtoRealm.setPreco(preco.doubleValue());

            BigDecimal qtde = new BigDecimal(String.valueOf(produto.getQtde()));
            produtoRealm.setQtde(qtde.doubleValue());

            produtoRealm.setUnidMed(produto.getUnidMed());

            confRealm.realm().beginTransaction();
            confRealm.realm().copyToRealm(produtoRealm);
            confRealm.realm().commitTransaction();
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
        imgProd = findViewById(R.id.imgProd);
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
}
