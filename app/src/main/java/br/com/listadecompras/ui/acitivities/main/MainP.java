package br.com.listadecompras.ui.acitivities.main;

import android.content.Intent;

import br.com.listadecompras.zxing.client.android.CaptureActivity;

public class MainP implements MainC.Presenter {

    MainC.View view;

    public MainP(MainC.View view) {
        this.view = view;
    }


    @Override
    public void callBarcodeReader() {
        view.callBarcodeReader();
    }

    @Override
    public void loadBar() {
        view.loadBar();
    }
}
