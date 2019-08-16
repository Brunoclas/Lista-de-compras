package br.com.listadecompras.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.listadecompras.R;
import br.com.listadecompras.adapters.HistoricoAdapter;

public class HistoricoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    HistoricoAdapter.OnClickHistoListener onClickHistoListener;
    public TextView txtNroLista, txtDt, txtQtde, txtVlTotal;

    public HistoricoHolder(@NonNull View itemView, HistoricoAdapter.OnClickHistoListener onClickHistoListener) {
        super(itemView);
        this.onClickHistoListener = onClickHistoListener;
        txtNroLista = itemView.findViewById(R.id.txtNroLista);
        txtDt = itemView.findViewById(R.id.txtDt);
        txtQtde = itemView.findViewById(R.id.txtQtde);
        txtVlTotal = itemView.findViewById(R.id.txtVlTotal);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onClickHistoListener.onClickHistoListener(getAdapterPosition() + 1);
//        Toast.makeText(v.getContext(), "Click - " + (getAdapterPosition() + 1), Toast.LENGTH_LONG).show();
    }

}
