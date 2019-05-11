package com.example.app_choferes.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.app_choferes.R;
import com.example.app_choferes.listeners.DialogInformationlistener;
import com.example.app_choferes.listeners.DialogYesNoListener;
import com.example.app_choferes.ui.activities.BasicActivity;

public final class DialogFactory {

    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static ProgressDialog createProgressDialog(Context context,
                                                      @StringRes int messageResource) {
        return createProgressDialog(context, context.getString(messageResource));
    }


    public static void showDialog(Context context, String title, String message, String positiveButton,
                                  String negativeButton, final DialogYesNoListener callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog_theme);

        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        callback.onPositive();
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        callback.onNegative();
                    }
                })
                .show();
    }

    public AlertDialog createYesNoDialog(Context context, String title, String message, String positiveButton,
                                         String negativeButton, final DialogYesNoListener callback) {

        return createYesNoDialog(context, title, message, positiveButton, negativeButton, callback, false);
    }

    private AlertDialog createYesNoDialog(Context context, String title, String message, String positiveButton,
                                          String negativeButton, final DialogYesNoListener callback, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog_theme);

        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(cancelable)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        callback.onPositive();
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        callback.onNegative();
                    }
                });

        return builder.create();
    }

    public static AlertDialog createInformationDialog(Context context, String title, String message,
                                                      String possitiveButton,
                                                      final DialogInformationlistener callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog_theme);

        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(possitiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onConfirm();
                    }
                });
        return builder.create();
    }

    public AlertDialog createYesNoCancelableDialog(final Context context, String title, String message, String positiveButton,
                                                   String negativeButton, String neutralButton, final DialogYesNoListener callback) {

        AlertDialog dialog = createYesNoDialog(context, title, message, positiveButton, negativeButton, callback, true);

        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, neutralButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static void showExitDialog(final AppCompatActivity activity) {
        Resources res = activity.getResources();
        String title = res.getString(R.string.exit_title);
        String message = res.getString(R.string.exit_message);
        String positive = res.getString(R.string.dialog_button_yes);
        String negative = res.getString(R.string.dialog_button_no);
        DialogFactory.showDialog(activity, title, message, positive, negative, new DialogYesNoListener() {
            @Override
            public void onPositive() {
                activity.finish();
            }

            @Override
            public void onNegative() {

            }
        });
    }

}