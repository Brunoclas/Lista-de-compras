package br.com.listadecompras.model;

public class Barcode_Request {
    private String passport;
    private String barcode;

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "Barcode_Request{" +
                "passport='" + passport + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
