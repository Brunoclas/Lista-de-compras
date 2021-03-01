package br.com.listadecompras.ui.acitivities.main;

public interface MainC {
    interface View{
        void callBarcodeReader();
        void loadBar();
    }
    interface Presenter{
        void callBarcodeReader();
        void loadBar();
    }

}
