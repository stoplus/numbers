package org.itstep.android1602.babakov_hw_95.DialogFragment;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.itstep.android1602.babakov_hw_95.Datable;
import org.itstep.android1602.babakov_hw_95.R;

import static org.itstep.android1602.babakov_hw_95.MainActivity.repHashMap;

public class DialogFragmentDelete extends DialogFragment {
    private EditText keyET;
    private Datable datable; // переменная для связи с главной активностью
    private Dialog dialog;

    @Override // Метод onAttach() вызывается в начале жизненного цикла фрагмента, и именно здесь
    // мы можем получить контекст фрагмента, в качестве которого выступает класс MainActivity
    // Так как MainActivity реализует интерфейс Datable, то мы можем преобразовать контекст
    // к данному интерфейсу.
    public void onAttach(Context context) {
        super.onAttach(context);

        // это важное действие - запомнить ссылку на активность
        datable = (Datable) context;
    } // onAttach

    @NonNull // построить диалог с получением данных из активности и обработчиком кнопки
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setTitle(R.string.del_entry)
                .setMessage(R.string.enter_value_key_del_entry)
                .setIcon(R.drawable.remove)
                .setView(R.layout.activity_delete);

        dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button btnPositive = (Button) getDialog().findViewById(R.id.idBtnDel);
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            // присвоить id полю
                            keyET = (EditText) getDialog().findViewById(R.id.idEditTextKeyDel);
                            int key = Integer.parseInt(keyET.getText().toString());

                            if (keyET == null) {
                                Toast toast = Toast.makeText(getActivity(), R.string.Fill_fields, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else if (!repHashMap.getHashMap().containsKey(key)) {//repHashMap делаем static
                                Toast toast = Toast.makeText(getActivity(), R.string.no_such_key, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {
                                // добавить строки в hashMap при помощи метода интерфейса
                                datable.delete(key);
                                dialog.dismiss();//закрываем диалог
                            }//if
                        } catch (NumberFormatException e) {
                            Toast toast = Toast.makeText(getActivity(), R.string.Fill_fields, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }//try-catch
                    }
                });

                Button btnNegative = (Button) getDialog().findViewById(R.id.idBtnBack);
                btnNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();//закрываем диалог
                    }
                });
            }
        });
        return dialog;
    } // onCreateDialog
} // class DialogFragmentDelete