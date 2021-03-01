package br.com.listadecompras.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import br.com.listadecompras.R;
import br.com.listadecompras.ui.acitivities.main.MainActivity;
import br.com.listadecompras.holders.ProdutoHolder;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.utils.Utils;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoHolder> {

    private RealmResults<ProdutoRealm> produtos;
    private OnClickProdListener onClickProdListener;
    private ConfRealm confRealm;
    private Activity activity;
    private View v;

    public ProdutoAdapter(RealmResults<ProdutoRealm> produtos, OnClickProdListener onClickProdListener, Activity activity) {
        this.produtos = produtos;
        this.onClickProdListener = onClickProdListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProdutoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_produto, viewGroup, false);
        this.v = v;
        return new ProdutoHolder(v, onClickProdListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProdutoHolder produtoHolder, final int i) {
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
                .resize(350, 300)
                .centerInside()
                .into(produtoHolder.imgProdList);

        produtoHolder.btnDeleteProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Atençāo !")
                        .setMessage("Tem certeza que deseja apagar esse produto?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try{
                                    confRealm = new ConfRealm();
                                    final RealmResults<ProdutoRealm> produtoRealms = confRealm.realm()
                                            .where(ProdutoRealm.class)
                                            .equalTo("status", Utils.EM_PROCESSAMENTO)
                                            .findAll()
                                            .sort("id", Sort.DESCENDING);

                                    confRealm.realm().executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            ProdutoRealm produtoRealm =  produtoRealms.get(i);
                                            produtoRealm.deleteFromRealm();
                                        }
                                    });
                                    produtoRealms.removeAllChangeListeners();
                                    notifyItemRemoved(i);
//                                    notifyItemRangeChanged(i, produtoRealms.size());
                                    v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class));
                                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    this.finalize();
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
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
