package br.com.listadecompras.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.listadecompras.R;
import br.com.listadecompras.holders.HistoricoHolder;
import br.com.listadecompras.model.ProdutoList;
import io.realm.RealmResults;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoHolder> {

    private OnClickHistoListener onClickHistoListener;
    private RealmResults<ProdutoList> produtoLists;

    public HistoricoAdapter(RealmResults<ProdutoList> produtoLists, OnClickHistoListener onClickHistoListener) {
        this.onClickHistoListener = onClickHistoListener;
        this.produtoLists = produtoLists;
    }

    @NonNull
    @Override
    public HistoricoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_historico, viewGroup, false);
        return new HistoricoHolder(v, onClickHistoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoHolder historicoHolder, int i) {
        try {
            historicoHolder.txtNroLista.setText("Numero: " + produtoLists.get(i).getId().toString());
            historicoHolder.txtDt.setText(produtoLists.get(i).getDt_criacao());
            historicoHolder.txtVlTotal.setText("R$: " + String.format("%.2f",produtoLists.get(i).getVl_total()));
            historicoHolder.txtQtde.setText("Itens: " + (produtoLists.get(i).getQtde_itens()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return produtoLists.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public interface OnClickHistoListener{
        void onClickHistoListener(int position);
    }
}
