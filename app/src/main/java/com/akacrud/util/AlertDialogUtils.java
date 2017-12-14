package com.akacrud.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.akacrud.R;

/**
 * Created by luisvespa on 12/14/17.
 */

public class AlertDialogUtils {
    // Context From
    private Context context;

    /**
     * Crea un diálogo tipo Alert predeterminado por el sistema
     * @param context
     * @param title
     * @param message
     * @return Diálogo
     */
    public static android.support.v7.app.AlertDialog createAlertDialog(Context context, String title, String message) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(
                context,
                AlertDialog.THEME_HOLO_LIGHT);
                builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    /**
     * Crea un diálogo tipo Alert predeterminado por el sistema
     * @param context
     * @param title
     * @param message
     * @return Diálogo
     */
    public static android.support.v7.app.AlertDialog createAlertDialogConfirmation(Context context, String title, String message) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(
                context,
                AlertDialog.THEME_HOLO_LIGHT);
                builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {

                        dialogInterface.cancel();
                    }
                });

        return builder.create();
    }

}
