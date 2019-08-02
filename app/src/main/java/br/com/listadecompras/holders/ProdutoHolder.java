package br.com.listadecompras.holders;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.listadecompras.R;
import br.com.listadecompras.adapters.ProdutoAdapter;

public class ProdutoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtNomeProd, txtPrecoProd, txtQtdeProd;
    public ImageView imgProd;
    ProdutoAdapter.OnClickProdListener onClickProdListener;

    public ProdutoHolder(@NonNull View itemView, ProdutoAdapter.OnClickProdListener onClickProdListener) {
        super(itemView);
        this.onClickProdListener = onClickProdListener;
        txtNomeProd = itemView.findViewById(R.id.txtNomeProd);
        txtPrecoProd = itemView.findViewById(R.id.txtPrecoProd);
        txtQtdeProd = itemView.findViewById(R.id.txtQtdeProd);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onClickProdListener.onClickProdListener(getAdapterPosition());
    }

}
