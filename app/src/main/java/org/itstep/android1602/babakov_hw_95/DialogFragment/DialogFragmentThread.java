package org.itstep.android1602.babakov_hw_95.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import org.itstep.android1602.babakov_hw_95.R;
import java.util.Collections;

import static org.itstep.android1602.babakov_hw_95.MainActivity.repHashMap;

public class DialogFragmentThread extends DialogFragment {
    private TextView tvMin;
    private TextView tvMax;
    private TextView tvSum;
    private TextView tvAver;
    private Handler h;
    private double summ = 0;

    private Runnable min = new Runnable() {
        public void run() {
            double min = Collections.min(repHashMap.getHashMap().values());
            tvMin.setText(String.format("%.10f", min));
        }
    };

    private Runnable max = new Runnable() {
        public void run() {
            double max = Collections.max(repHashMap.getHashMap().values());
            tvMax.setText(String.format("%.10f", max));
        }
    };

    private Runnable sum = new Runnable() {
        public void run() {
            for (double f : repHashMap.getHashMap().values()) {
                summ += f;
            }
            tvSum.setText(String.format("%.10f", summ));
        }
    };

    private Runnable aver = new Runnable() {
        public void run() {
            double aver = summ / repHashMap.getHashMap().values().size();
            tvAver.setText(String.format("%.10f", aver));
        }
    };

    @NonNull // создание диалога
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // получить ссылку на активность, вызвавшую диалог
        final FragmentActivity current = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(current);
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_thread, null);//создаем вид
        tvMax = (TextView) view.findViewById(R.id.idTVMaxValue);
        tvMin = (TextView) view.findViewById(R.id.idTVMinValue);
        tvSum = (TextView) view.findViewById(R.id.idTVSumValue);
        tvAver = (TextView) view.findViewById(R.id.idTVAverValue);

        h = new Handler();

        Thread t = new Thread(new Runnable() {
            public void run() {
                //появление с задержкой
                h.postDelayed(min, 500);
                h.postDelayed(max, 1000);
                h.postDelayed(sum, 1500);
                h.postDelayed(aver, 2000);
                //моментальное появление
//                h.post(min);
//                h.post(max);
//                h.post(sum);
//                h.post(aver);
            } // run
        });
        t.start();

        return builder
                .setTitle("Вывод данных")
                .setMessage("Параллельная обработка в Runnable")
                .setIcon(R.drawable.info)
                .setPositiveButton("Назад", null) // не назначаем слушателя кликов по кнопке
                .setView(view)
                .create();
    } // onCreateDialog
}//DialogFragmentExit

