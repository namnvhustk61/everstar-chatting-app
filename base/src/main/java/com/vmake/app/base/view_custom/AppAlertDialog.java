package com.vmake.app.base.view_custom;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;

import com.vmake.app.base.R;

public class AppAlertDialog {


    private static AlertDialog setAlertGeneral(
            Context context,
            String title,
            String message,
            int resIcon,
            String btnPositive, String btnNegative,
            View.OnClickListener onClickBtnPositive,
            View.OnClickListener onClickBtnNegative
    ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setTitle(title)
                .setIcon(resIcon);

        if (btnPositive != null) {
            builder.setPositiveButton(
                    btnPositive,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if (onClickBtnPositive != null) {
                                onClickBtnPositive.onClick(null);
                            }
                        }
                    });
        }

        if (btnNegative != null) {
            builder.setNegativeButton(
                    btnNegative,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if (onClickBtnNegative != null) {
                                onClickBtnNegative.onClick(null);
                            }
                        }
                    });
        }
        AlertDialog alert = builder.create();


        return alert;
    }

    private static AlertDialog setAlertGeneral(
            Context context,
            String title,
            String message,
            String btnPositive, String btnNegative,
            View.OnClickListener onClickBtnPositive,
            View.OnClickListener onClickBtnNegative
    ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setTitle(title);

        if (btnPositive != null) {
            builder.setPositiveButton(
                    btnPositive,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if (onClickBtnPositive != null) {
                                onClickBtnPositive.onClick(null);
                            }
                        }
                    });
        }

        if (btnNegative != null) {
            builder.setNegativeButton(
                    btnNegative,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if (onClickBtnNegative != null) {
                                onClickBtnNegative.onClick(null);
                            }
                        }
                    });
        }
        AlertDialog alert = builder.create();


        return alert;
    }

    public static void showMessage(Context context, String message) {
        setAlertGeneral(context, message, context.getString(R.string.ok), null, null, null, null).show();
    }

    public static void showMessage(Context context, String message, View.OnClickListener onClick) {
        setAlertGeneral(context, message, context.getString(R.string.ok), null, null, onClick, null).show();
    }

    public static void showMessage(Context context, String title, String message) {
        setAlertGeneral(context, title, message, context.getString(R.string.ok), null, null, null).show();
    }

    public static void showMessage(Context context, String message, String btnText, View.OnClickListener onClick) {
        setAlertGeneral(context, message, null, btnText, null, onClick, null).show();
    }

    public static void showMessage(Context context, String message, String btnText1, String btnText2, View.OnClickListener onClick1, View.OnClickListener onClick2) {
        AlertDialog dialog = setAlertGeneral(context, message, null, btnText1, btnText2, onClick1, onClick2);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
    }


    public void showDialogEdittext(
            Context context,
            String title,
            String message,
            String inputString,
            String btnPositive, String btnNegative,
            View.OnClickListener onClickBtnPositive,
            View.OnClickListener onClickBtnNegative

    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);

        FrameLayout container = new FrameLayout(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        final EditText input = new EditText(context);


        input.setText(inputString);
        input.setSelectAllOnFocus(true);

        params.setMargins((int) (19 * 2.0), (int) (5 * 2.0), (int) (14 * 2.0), (int) (14 * 2.0));
        input.setLayoutParams(params);
        container.addView(input);
        builder.setView(container);
        builder.setPositiveButton(btnPositive,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onClickBtnPositive != null) {
                            onClickBtnPositive.onClick(null);
                        }
                    }
                });

        builder.setNegativeButton(btnNegative,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (onClickBtnNegative != null) {
                            onClickBtnNegative.onClick(null);
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
