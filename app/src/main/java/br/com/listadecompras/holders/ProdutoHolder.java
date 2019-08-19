package br.com.listadecompras.holders;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.listadecompras.R;
import br.com.listadecompras.adapters.ProdutoAdapter;

public class ProdutoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtNomeProd, txtVlUnit, txtQtdeProd, txtVlTotal;
    public ImageView imgProdList;
    public ImageButton btnDeleteProd;
    ProdutoAdapter.OnClickProdListener onClickProdListener;

    public ProdutoHolder(@NonNull View itemView, ProdutoAdapter.OnClickProdListener onClickProdListener) {
        super(itemView);
        this.onClickProdListener = onClickProdListener;
        txtNomeProd = itemView.findViewById(R.id.txtNomeProd);
        txtVlUnit = itemView.findViewById(R.id.txtVlUnit);
        txtQtdeProd = itemView.findViewById(R.id.txtQtdeProd);
        txtVlTotal = itemView.findViewById(R.id.txtVlTotal);
        imgProdList = itemView.findViewById(R.id.imgProdList);
        btnDeleteProd = itemView.findViewById(R.id.btnDeleteProd);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onClickProdListener.onClickProdListener(getAdapterPosition());
        Toast.makeText(v.getContext(), "Click - " + getAdapterPosition(), Toast.LENGTH_LONG).show();
    }

}
