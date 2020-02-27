package br.com.listadecompras.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Historico extends RealmObject implements Parcelable {

    @PrimaryKey
    private Long id;
    private Long qtde_itens;
    private Double vl_total;
    private String dt_finalizacao;
    private String status;

    public Historico() {

    }

    protected Historico(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            qtde_itens = null;
        } else {
            qtde_itens = in.readLong();
        }
        if (in.readByte() == 0) {
            vl_total = null;
        } else {
            vl_total = in.readDouble();
        }
        dt_finalizacao = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (qtde_itens == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(qtde_itens);
        }
        if (vl_total == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(vl_total);
        }
        dest.writeString(dt_finalizacao);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Historico> CREATOR = new Creator<Historico>() {
        @Override
        public Historico createFromParcel(Parcel in) {
            return new Historico(in);
        }

        @Override
        public Historico[] newArray(int size) {
            return new Historico[size];
        }
    };

    @Override
    public String toString() {
        return "Historico{" +
                "id=" + id +
                ", qtde_itens=" + qtde_itens +
                ", vl_total=" + vl_total +
                ", dt_finalizacao='" + dt_finalizacao + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
