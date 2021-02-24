package br.com.listadecompras.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Produto_k extends RealmObject implements Parcelable {
    public String art_no;
    public String store_no;
    public String descr;
    public String origen;
    public String pr1;
    public String prc1vat;
    public String prc1mea;
    public String pr2;
    public String prc2vat;
    public String prc2mea;
    public String display;
    public String julian;
    public String iart_threshold;
    public String art_grp_no;
    public String art_grp_sub_no;
    public String dept_no;
    public String sell_unit;
    public String ispromo;
    public String instock;
    public Boolean iscombo;
    public String promo_text;
    public String promo_pv;
    public Boolean promo_type;
    public String barcode;

    public Produto_k() {

    }

    protected Produto_k(Parcel in) {
        art_no = in.readString();
        store_no = in.readString();
        descr = in.readString();
        origen = in.readString();
        pr1 = in.readString();
        prc1vat = in.readString();
        prc1mea = in.readString();
        pr2 = in.readString();
        prc2vat = in.readString();
        prc2mea = in.readString();
        display = in.readString();
        julian = in.readString();
        iart_threshold = in.readString();
        art_grp_no = in.readString();
        art_grp_sub_no = in.readString();
        dept_no = in.readString();
        sell_unit = in.readString();
        ispromo = in.readString();
        instock = in.readString();
        byte tmpIscombo = in.readByte();
        iscombo = tmpIscombo == 0 ? null : tmpIscombo == 1;
        promo_text = in.readString();
        promo_pv = in.readString();
        byte tmpPromo_type = in.readByte();
        promo_type = tmpPromo_type == 0 ? null : tmpPromo_type == 1;
        barcode = in.readString();
    }

    public static final Creator<Produto_k> CREATOR = new Creator<Produto_k>() {
        @Override
        public Produto_k createFromParcel(Parcel in) {
            return new Produto_k(in);
        }

        @Override
        public Produto_k[] newArray(int size) {
            return new Produto_k[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(art_no);
        dest.writeString(store_no);
        dest.writeString(descr);
        dest.writeString(origen);
        dest.writeString(pr1);
        dest.writeString(prc1vat);
        dest.writeString(prc1mea);
        dest.writeString(pr2);
        dest.writeString(prc2vat);
        dest.writeString(prc2mea);
        dest.writeString(display);
        dest.writeString(julian);
        dest.writeString(iart_threshold);
        dest.writeString(art_grp_no);
        dest.writeString(art_grp_sub_no);
        dest.writeString(dept_no);
        dest.writeString(sell_unit);
        dest.writeString(ispromo);
        dest.writeString(instock);
        dest.writeByte((byte) (iscombo == null ? 0 : iscombo ? 1 : 2));
        dest.writeString(promo_text);
        dest.writeString(promo_pv);
        dest.writeByte((byte) (promo_type == null ? 0 : promo_type ? 1 : 2));
        dest.writeString(barcode);
    }
}
