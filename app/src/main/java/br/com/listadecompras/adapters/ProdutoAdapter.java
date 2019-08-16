package br.com.listadecompras.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.listadecompras.R;
import br.com.listadecompras.holders.ProdutoHolder;
import br.com.listadecompras.model.Produto;
import br.com.listadecompras.model.ProdutoRealm;
import io.realm.RealmResults;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoHolder> {

    private RealmResults<ProdutoRealm> produtos;
    private OnClickProdListener onClickProdListener;

    public ProdutoAdapter(RealmResults<ProdutoRealm> produtos, OnClickProdListener onClickProdListener) {
        this.produtos = produtos;
        this.onClickProdListener = onClickProdListener;
    }

    @NonNull
    @Override
    public ProdutoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_produto, viewGroup, false);
        return new ProdutoHolder(v, onClickProdListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoHolder produtoHolder, int i) {
        produtoHolder.txtNomeProd.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
        produtoHolder.txtNomeProd.setText(produtos.get(i).getDescription());
        produtoHolder.txtVlUnit.setText("Vl unit.: R$ " + String.format("%.2f", produtos.get(i).getPreco()));
        double vlTotal = produtos.get(i).getPreco() * produtos.get(i).getQtde();
        produtoHolder.txtVlTotal.setText("Vl Total: R$ " + String.format("%.2f", vlTotal));

        if(produtos.get(i).getUnidMed().equals("Unidade")) {
            produtoHolder.txtQtdeProd.setText("Quantidade: " + String.format("%.0f", produtos.get(i).getQtde()));
        }else{
            produtoHolder.txtQtdeProd.setText("Quantidade: " + produtos.get(i).getQtde());
        }

        Picasso.get()
                .load(produtos.get(i).getThumbnail())
                .resize(250, 280)
                .centerInside()
                .into(produtoHolder.imgProdList);
    }

    @Override
    public int getItemCount() {
        try {
                return produtos.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public interface OnClickProdListener{
        void onClickProdListener(int position);
    }
}
