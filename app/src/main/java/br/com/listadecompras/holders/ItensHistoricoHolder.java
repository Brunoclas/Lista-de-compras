package br.com.listadecompras.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.listadecompras.R;

public class ItensHistoricoHolder extends RecyclerView.ViewHolder  {
    public TextView txtNomeProd, txtVlUnit, txtQtdeProd, txtVlTotal;
    public ImageView imgProd;


    public ItensHistoricoHolder(@NonNull View itemView) {
        super(itemView);
        txtNomeProd = itemView.findViewById(R.id.txtNomeProd);
        txtVlUnit = itemView.findViewById(R.id.txtVlUnit);
        txtQtdeProd = itemView.findViewById(R.id.txtQtdeProd);
        txtVlTotal = itemView.findViewById(R.id.txtVlTotal);
        imgProd = itemView.findViewById(R.id.imgProdList);
    }
}
