package org.itstep.android1602.babakov_hw_95.DialogFragment;


import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;

import org.itstep.android1602.babakov_hw_95.Datable;
import org.itstep.android1602.babakov_hw_95.R;


public class DialogFragmentFind extends DialogFragment {
    private EditText minET;
    private EditText maxET;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setTitle(R.string.Entering_Values)
                .setMessage(R.string.Enter_range_values)
                .setIcon(R.drawable.info)
                .setView(R.layout.activity_find);

        dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button btnPositive = (Button) getDialog().findViewById(R.id.idBtnFind);
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            // присвоить id полю
                            minET = (EditText) getDialog().findViewById(R.id.idEditTextMin);
                            maxET = (EditText) getDialog().findViewById(R.id.idEditTextMax);
                            double min = Double.parseDouble(minET.getText().toString());
                            double max = Double.parseDouble(maxET.getText().toString());
// TODO: 31.08.2017 stream API
//                            if (repHashMap.getHashMap().values().stream().max(String::compareTo).get() > max ) {
//                                Toast toast = Toast.makeText(getActivity(), "Значения не в диапазоне поиска!", Toast.LENGTH_SHORT);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
//                            } else {
                                // добавить строки в hashMap при помощи метода интерфейса
                                datable.find(min, max);
                                dialog.dismiss();//закрываем диалог
//                            }//if
                        } catch (NumberFormatException e) {
                            Toast toast = Toast.makeText(getActivity(), R.string.Fill_fields, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }//try-catch
                    }
                });

                Button btnNegative = (Button) getDialog().findViewById(R.id.idBtnExit);
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