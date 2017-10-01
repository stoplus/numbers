package org.itstep.android1602.babakov_hw_95;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.itstep.android1602.babakov_hw_95.DialogFragment.DialogFragmentAdd;
import org.itstep.android1602.babakov_hw_95.DialogFragment.DialogFragmentDelete;
import org.itstep.android1602.babakov_hw_95.DialogFragment.DialogFragmentExit;
import org.itstep.android1602.babakov_hw_95.DialogFragment.DialogFragmentFind;
import org.itstep.android1602.babakov_hw_95.DialogFragment.DialogFragmentThread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//https://github.com/cstew/Splash - ссылка для заставки
//https://habrahabr.ru/post/312516/ - ссылка для заставки
public class MainActivity extends AppCompatActivity implements Datable {
    private ListView listView;
    private String FILE_NAME = "BinaryFile.bin";
    private final static String DIR_SD = "BinaryFolder";
    public static RepositoryHM repHashMap;
    private ArrayAdapter adapter;
    private ArrayList arrayList;
    private Animation anim = null;
    private Resources res;//Доступ к ресурсам

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));//отложенное время запуска приложения. Можно удалить для быстрого запуска
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repHashMap = new RepositoryHM();
        listView = (ListView) findViewById(R.id.idListViev);

        repHashMap.createhashMap();//создаем колекцию

        repHashMap.setHashMap(repHashMap.sortByKey(repHashMap.getHashMap()));//сортируем по увеличению ключа
        arrayList = repHashMap.getArrayListString();//готовим для адаптера список

        adapter = new ArrayAdapter(getApplicationContext(), R.layout.activity_item, R.id.idTVItem, arrayList);//создаем адаптер
        listView.setAdapter(adapter);
        res = getResources();//Доступ к ресурсам
    }
    //------------------------------------------------------------------------------------------------------------------------------

    // Загрузка меню в окне
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // добавляем пункты через файл в ресурсах
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }//onCreateOptionsMenu

    // Обработка нажатий на кнопки в меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.idItemAdd:
                anim = null;
                DialogFragmentAdd dialogAdd = new DialogFragmentAdd();
                dialogAdd.show(getSupportFragmentManager(), "dialogAdd");// отображение диалогового окна
                break;
            case R.id.idItemDelete:
                anim = null;
                DialogFragmentDelete dialogDel = new DialogFragmentDelete();
                dialogDel.show(getSupportFragmentManager(), "dialogDelete");// отображение диалогового окна
                break;
            case R.id.idItemSortByValue:
                repHashMap.setHashMap(repHashMap.sortByValue(repHashMap.getHashMap()));
                show();
                anim = AnimationUtils.loadAnimation(this, R.anim.emergence);//анимация появление
                break;
            case R.id.idItemSortByKey:
                repHashMap.setHashMap(repHashMap.sortByKey(repHashMap.getHashMap()));
                show();
                anim = AnimationUtils.loadAnimation(this, R.anim.emergence);//анимация появление
                break;
            case R.id.idItemFind:
                anim = null;
                DialogFragmentFind dialogFind = new DialogFragmentFind();
                dialogFind.show(getSupportFragmentManager(), "dialogFind");// отображение диалогового окна
                break;
            case R.id.idItemLoad:
                loadTextTel();
                anim = AnimationUtils.loadAnimation(this, R.anim.open);
                break;
            case R.id.idItemSave:
                saveText();
                anim = AnimationUtils.loadAnimation(this, R.anim.save);
                break;
            case R.id.idItemRunnable:
                anim = null;
                DialogFragmentThread dialogThread = new DialogFragmentThread();
                dialogThread.show(getSupportFragmentManager(), "dialogThread");
                break;
            case R.id.idItemExit:
                anim = null;
                DialogFragmentExit dialogExit = new DialogFragmentExit();//создаем диалог только при нажатии на кнопку "Назад"
                dialogExit.show(getSupportFragmentManager(), "dialogExit");//показываем диалог
                break;
        } // switch
        // запускаем анимацию для компонента listView
        if (anim != null) listView.startAnimation(anim);

        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected
    //------------------------------------------------------------------------------------------------------------------------------

    private void show() {
        Animation anim;
        arrayList = repHashMap.getArrayListString();//готовим для адаптера список
        //adapter.notifyDataSetChanged();//не пашет
        adapter = new ArrayAdapter(getApplicationContext(), R.layout.activity_item, R.id.idTVItem, arrayList);//создаем адаптер
        listView.setAdapter(adapter);
        anim = AnimationUtils.loadAnimation(this, R.anim.emergence);//анимация появление
        if (anim != null) listView.startAnimation(anim);
    }//show

    //-----------------------------------------------------------------------------------------------------------------------------
    private void saveText() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast toast = Toast.makeText(this, res.getString(R.string.SD_card_not_available) + Environment.getExternalStorageState(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return;
        }

        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();

        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);

        // создаем каталог
        sdPath.mkdir();

        // формируем объект File, который содержит путь к файлу (в т.ч. имя файла)
        File sdFile = new File(sdPath, FILE_NAME);
        //  для записи в бинарный файл
        try (FileOutputStream fos = new FileOutputStream(sdFile)) {
            int capacity = (Double.BYTES + Integer.BYTES) * repHashMap.getHashMap().size();
            ByteBuffer bf = ByteBuffer.allocate(capacity);

            for (Map.Entry entry : repHashMap.getHashMap().entrySet()) {
                bf.putInt((Integer) entry.getKey());
                bf.putDouble((Double) entry.getValue());
            }
            fos.write(bf.array());

            Toast.makeText(this, res.getString(R.string.File_saved_to_SD_card), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, res.getString(R.string.Error_writing_file) + ex.getMessage(), Toast.LENGTH_LONG).show();
        } // try-catch
    } // saveText


    // чтение файла в строку, поместить строку в элемент просмотра
    private void loadTextTel() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, res.getString(R.string.SD_card_not_available) + Environment.getExternalStorageState(),
                    Toast.LENGTH_LONG).show();
            return;
        }

        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();

        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);

        // формируем объект File, который содержит путь к файлу
        File file = new File(sdPath, FILE_NAME);

        // если файл не существует, выход из метода
        if (!file.exists()) {
            Toast.makeText(this, res.getString(R.string.file_not_found) + sdPath + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
            return;
        }

        try (FileInputStream fin = new FileInputStream(file)) {
            int capAvailable = fin.available();//1200
            repHashMap.getHashMap().clear();//очищаем

            byte[] buffer = new byte[capAvailable];// массив байт для чтения из файла
            fin.read(buffer);// чтение

            ByteBuffer bf = ByteBuffer.allocate(capAvailable);
            bf.put(buffer);//ложим массив байт
            bf.flip();//значение предела стало соответствовать текущей позиции, а позиция — нулю

            while (bf.remaining() > 0) {//возвращает сколько еще элементов можно прочитать или записать
                int key = bf.getInt();
                double value = bf.getDouble();
                repHashMap.add(key, value);
            }
            show();
            Toast.makeText(this, res.getString(R.string.file_open_from_SD_card), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, res.getString(R.string.file_not_found) +"\n" + res.getString(R.string.Make_save), Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        } // try-catch
    } // loadText
    //-----------------------------------------------------------------------------------------------------------------------------

    // Перехватчик нажатия клавиши Назад для текущей активности - вызвать диалог выхода
    @Override public void onBackPressed() {
        // отображение диалогового окна
        DialogFragmentExit dialogExit = new DialogFragmentExit();//создаем диалог только при нажатии на кнопку "Назад"
        dialogExit.show(getSupportFragmentManager(), "dialogExit");//показываем диалог
    } // onBackPressed
    //-----------------------------------------------------------------------------------------------------------------------------

    @Override
    public void add(int key, double value) {
        Handler h = new Handler();

        Runnable without = new Runnable() {
            public void run() {
                show();
            }
        };

        Runnable with = new Runnable() {
            public void run() {
                repHashMap.add(key, value);//добавляем в список
                arrayList = repHashMap.getArrayListString();//готовим для адаптера список
                adapter = new ArrayAdapter(getApplicationContext(), R.layout.activity_item, R.id.idTVItem, arrayList);//создаем адаптер
                // TODO: при дефолтном  исполнении адаптера цифры почему-то становятся белыми(на API 21)
//                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

                listView.setAdapter(adapter);
                saveText();
            }
        };

        Thread t = new Thread(new Runnable() {
            public void run() {
                //появление с задержкой
                h.post(without);
                h.postDelayed(with, 1500);
            } // run
        });
        t.start();
    }//add

    @Override
    public void delete(int key) {
        repHashMap.delete(key);//удаляем по ключу
        show();
        saveText();
    }

    @Override
    public void find(double min, double max) {
        repHashMap.setHashMap(repHashMap.findDouble(min, max));
        show();
    }
}
