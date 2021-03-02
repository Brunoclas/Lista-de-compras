package br.com.listadecompras.holders;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.listadecompras.R;
import br.com.listadecompras.adapters.ProdutoAdapter;
import br.com.listadecompras.model.ProdutoRealm;
import br.com.listadecompras.realm.ConfRealm;
import br.com.listadecompras.ui.acitivities.main.MainActivity;
import br.com.listadecompras.utils.Utils;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ProdutoHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView txtNomeProd, txtVlUnit, txtQtdeProd, txtVlTotal;
    public ImageView imgProdList;
    ProdutoAdapter.OnClickProdListener onClickProdListener;
    ProdutoAdapter.OnClickLongProdListener onClickLongProdListener;
    private ConfRealm confRealm;
    private View v;

    public ProdutoHolder(@NonNull View itemView, ProdutoAdapter.OnClickProdListener onClickProdListener, ProdutoAdapter.OnClickLongProdListener onClickLongProdListener) {
        super(itemView);
        this.onClickProdListener = onClickProdListener;
        this.onClickLongProdListener = onClickLongProdListener;
        txtNomeProd = itemView.findViewById(R.id.txtNomeProd);
        txtVlUnit = itemView.findViewById(R.id.txtVlUnit);
        txtQtdeProd = itemView.findViewById(R.id.txtQtdeProd);
        txtVlTotal = itemView.findViewById(R.id.txtVlTotal);
        imgProdList = itemView.findViewById(R.id.imgProdList);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        this.v = itemView;
    }

    @Override
    public void onClick(View v) {
        onClickProdListener.onClickProdListener(getAdapterPosition());
        Toast.makeText(v.getContext(), "Click - " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        onClickLongProdListener.onClickLongProdListener(getAdapterPosition());
        Toast.makeText(v.getContext(), "Click - long" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        deleteProd();
        return true;
    }

    private void deleteProd(){
        try{
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
                                        ProdutoRealm produtoRealm =  produtoRealms.get(getAdapterPosition());
                                        produtoRealm.deleteFromRealm();
                                    }
                                });
                                produtoRealms.removeAllChangeListeners();
//                                notifyItemRemoved(i);
//                                    notifyItemRangeChanged(i, produtoRealms.size());
//                                v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class));
//                                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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
        }catch (Exception e){

        }
    }
}
