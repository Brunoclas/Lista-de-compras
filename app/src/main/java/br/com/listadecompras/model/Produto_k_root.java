package br.com.listadecompras.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Produto_k_root implements Parcelable {
    public int r;
    public int t;
    public Produto_k d;

    protected Produto_k_root(Parcel in) {
        r = in.readInt();
        t = in.readInt();
        d = in.readParcelable(Produto_k.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(r);
        dest.writeInt(t);
        dest.writeParcelable(d, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Produto_k_root> CREATOR = new Creator<Produto_k_root>() {
        @Override
        public Produto_k_root createFromParcel(Parcel in) {
            return new Produto_k_root(in);
        }

        @Override
        public Produto_k_root[] newArray(int size) {
            return new Produto_k_root[size];
        }
    };

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public Produto_k getD() {
        return d;
    }

    public void setD(Produto_k d) {
        this.d = d;
    }
}
