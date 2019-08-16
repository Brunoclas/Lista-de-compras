package br.com.listadecompras.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

import io.realm.MutableRealmInteger;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Produto implements Parcelable {

    @PrimaryKey
    private Long id;

    @Required
    private String description;

    @Required
    private String gtin;

    private String thumbnail;
    private double preco;
    private double vl_total;
    private double new_preco;
    private String barcode_image;
    private double qtde;
    private String unidMed;
    private String dataCad;
    private String status;
    private long id_lista;

    public Produto() {

    }

    protected Produto(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        description = in.readString();
        gtin = in.readString();
        thumbnail = in.readString();
        preco = in.readDouble();
        vl_total = in.readDouble();
        new_preco = in.readDouble();
        barcode_image = in.readString();
        qtde = in.readDouble();
        unidMed = in.readString();
        dataCad = in.readString();
        status = in.readString();
        id_lista = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(description);
        dest.writeString(gtin);
        dest.writeString(thumbnail);
        dest.writeDouble(preco);
        dest.writeDouble(vl_total);
        dest.writeDouble(new_preco);
        dest.writeString(barcode_image);
        dest.writeDouble(qtde);
        dest.writeString(unidMed);
        dest.writeString(dataCad);
        dest.writeString(status);
        dest.writeLong(id_lista);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override
        public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getVl_total() {
        return vl_total;
    }

    public void setVl_total(double vl_total) {
        this.vl_total = vl_total;
    }

    public double getNew_preco() {
        return new_preco;
    }

    public void setNew_preco(double new_preco) {
        this.new_preco = new_preco;
    }

    public String getBarcode_image() {
        return barcode_image;
    }

    public void setBarcode_image(String barcode_image) {
        this.barcode_image = barcode_image;
    }

    public double getQtde() {
        return qtde;
    }

    public void setQtde(double qtde) {
        this.qtde = qtde;
    }

    public String getUnidMed() {
        return unidMed;
    }

    public void setUnidMed(String unidMed) {
        this.unidMed = unidMed;
    }

    public String getDataCad() {
        return dataCad;
    }

    public void setDataCad(String dataCad) {
        this.dataCad = dataCad;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId_lista() {
        return id_lista;
    }

    public void setId_lista(long id_lista) {
        this.id_lista = id_lista;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", gtin='" + gtin + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", preco=" + preco +
                ", vl_total=" + vl_total +
                ", new_preco=" + new_preco +
                ", barcode_image='" + barcode_image + '\'' +
                ", qtde=" + qtde +
                ", unidMed='" + unidMed + '\'' +
                ", dataCad='" + dataCad + '\'' +
                ", status='" + status + '\'' +
                ", id_lista=" + id_lista +
                '}';
    }
}
