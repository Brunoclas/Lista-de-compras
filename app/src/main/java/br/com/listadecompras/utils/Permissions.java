package br.com.listadecompras.utils;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissions {
    public static boolean validatePermission(String[] permissions, Activity activity, int requestCode) {

        if (Build.VERSION.SDK_INT >= 23) {

            List<String> listPermissions = new ArrayList<>();

            /*Percorre as permissões passadas,
            verificando uma a uma
            * se já tem a permissao liberada */
            for (String permission : permissions) {
                Boolean permissionOk = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
                if (!permissionOk) listPermissions.add(permission);
            }

            /*Caso a lista esteja vazia, não é necessário solicitar permissão*/
            if (listPermissions.isEmpty()) return true;
            String[] novasPermissoes = new String[listPermissions.size()];
            listPermissions.toArray(novasPermissoes);

            //Solicita permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);

        }

        return true;
    }
}
