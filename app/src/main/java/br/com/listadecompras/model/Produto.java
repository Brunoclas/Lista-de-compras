package br.com.listadecompras.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class Produto implements Parcelable {
    private String description;
    private String gtin;
    private String thumbnail;
    private double preco;
    private double old_preco;
    private double new_preco;
    private String barcode_image;
    private double qtde;
    private String unidMed;

    protected Produto(Parcel in) {
        description = in.readString();
        gtin = in.readString();
        thumbnail = in.readString();
        preco = in.readDouble();
        old_preco = in.readDouble();
        new_preco = in.readDouble();
        barcode_image = in.readString();
        qtde = in.readDouble();
        unidMed = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(gtin);
        dest.writeString(thumbnail);
        dest.writeDouble(preco);
        dest.writeDouble(old_preco);
        dest.writeDouble(new_preco);
        dest.writeString(barcode_image);
        dest.writeDouble(qtde);
        dest.writeString(unidMed);
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

    public double getOld_preco() {
        return old_preco;
    }

    public void setOld_preco(double old_preco) {
        this.old_preco = old_preco;
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

    @Override
    public String toString() {
        return "Produto{" +
                "description='" + description + '\'' +
                ", gtin='" + gtin + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", preco=" + preco +
                ", old_preco=" + old_preco +
                ", new_preco=" + new_preco +
                ", barcode_image='" + barcode_image + '\'' +
                ", qtde=" + qtde +
                ", unidMed='" + unidMed + '\'' +
                '}';
    }
}
