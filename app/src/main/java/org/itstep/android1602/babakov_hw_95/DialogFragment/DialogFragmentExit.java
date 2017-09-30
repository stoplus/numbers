package org.itstep.android1602.babakov_hw_95.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import org.itstep.android1602.babakov_hw_95.R;

public class DialogFragmentExit extends DialogFragment {

    @NonNull // создание диалога
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // получить ссылку на активность, вызвавшую диалог
        final FragmentActivity current = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(current);

        return builder
                .setTitle("Выход")
                .setMessage("Вы уверены?")
                .setIcon(R.drawable.exit)
                .setNegativeButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        current.finish();
                    }
                })
                .setPositiveButton("Нет", null) // не назначаем слушателя кликов по кнопке "Нет"
                .setCancelable(false)           // запрет закрытия диалога кнопкой Назад
                .create();
    } // onCreateDialog
}//DialogFragmentExit