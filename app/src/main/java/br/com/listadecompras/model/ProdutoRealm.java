package br.com.listadecompras.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ProdutoRealm extends RealmObject {

    @Required
    private String description;

    @Required
    private String gtin;

    private String thumbnail;
    private double preco;
    private double old_preco;
    private double new_preco;
    private String barcode_image;
    private double qtde;
    private String unidMed;

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
}
