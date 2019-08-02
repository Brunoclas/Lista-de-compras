package br.com.listadecompras.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        produtoHolder.txtNomeProd.setText(produtos.get(i).getDescription());
        produtoHolder.txtPrecoProd.setText(String.valueOf(produtos.get(i).getPreco()));
        produtoHolder.txtQtdeProd.setText(String.valueOf(produtos.get(i).getQtde()));
    }

    @Override
    public int getItemCount() {
        if(produtos.equals(null) || produtos.equals("")){
            return produtos.size();
        }
        return 0;
    }

    public interface OnClickProdListener{
        void onClickProdListener(int position);
    }
}
