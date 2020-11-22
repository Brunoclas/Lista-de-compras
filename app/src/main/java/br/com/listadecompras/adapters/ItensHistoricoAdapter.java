package br.com.listadecompras.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import br.com.listadecompras.R;
import br.com.listadecompras.holders.ItensHistoricoHolder;
import br.com.listadecompras.model.ProdutoRealm;
import io.realm.RealmResults;

public class ItensHistoricoAdapter extends RecyclerView.Adapter<ItensHistoricoHolder> {
    private RealmResults<ProdutoRealm> produtos;

    public ItensHistoricoAdapter(RealmResults<ProdutoRealm> produtos) {
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ItensHistoricoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detalhes_historico, viewGroup, false);
        return new ItensHistoricoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItensHistoricoHolder itensHistoricoHolder, int i) {
        itensHistoricoHolder.txtNomeProd.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
        itensHistoricoHolder.txtNomeProd.setText(produtos.get(i).getDescription());
        itensHistoricoHolder.txtVlUnit.setText("Vl unit.: R$ " + String.format("%.2f", produtos.get(i).getPreco()));
        double vlTotal = produtos.get(i).getPreco() * produtos.get(i).getQtde();
        itensHistoricoHolder.txtVlTotal.setText("Vl Total: R$ " + String.format("%.2f", vlTotal));
        if(produtos.get(i).getUnidMed().equals("Unidade")) {
            itensHistoricoHolder.txtQtdeProd.setText("Quantidade: " + String.format("%.0f", produtos.get(i).getQtde()));
        }else{
            itensHistoricoHolder.txtQtdeProd.setText("Quantidade: " + produtos.get(i).getQtde());
        }
        Picasso.get()
                .load(produtos.get(i).getThumbnail())
                .resize(350, 300)
                .centerInside()
                .into(itensHistoricoHolder.imgProd);

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
}
