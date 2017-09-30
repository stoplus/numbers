package org.itstep.android1602.babakov_hw_95;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RepositoryHM {
    private Map<Integer, Double> hashMap;
    private boolean flag = true;

    public Map<Integer, Double> getHashMap() {
        return hashMap;
    }

    public void setHashMap(Map<Integer, Double> hashMap) {
        this.hashMap = hashMap;
    }

    public void createhashMap() {
        hashMap = new HashMap<>();// создаем RepositoryHM

        for (int i = 0; i < 10; i++) {
            int firstNum = Utils.getRandom(1, 100);
            while (hashMap.keySet().contains(firstNum)) {
                firstNum = Utils.getRandom(1, 100);
            }
            double secondNum = Utils.getRandom(-9.0, 9.0);

            hashMap.put(firstNum, secondNum);//добавляем значения в hashMap
        }
    }

    //добавление записи
    public void add(int k, double v) {
        hashMap.put(k, v);
    }

    //удаление записи по ключу
    public void delete(int k) {
        hashMap.remove(k);
    }

    //сортировка по увеличению/уменьшению значения для любых переменных
    public <K extends Comparable<? super K>, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {//используем K, V для разных форматов
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());//создаем список
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {//помещаем список в колекцию
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                if (flag) return (o1.getValue()).compareTo(o2.getValue());//реверс сортировки
                else return (o2.getValue()).compareTo(o1.getValue());//реверс сортировки
            }
        });
        flag = !flag;//реверс сортировки

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());//перезаписываем заново
        }
        return result;
    }//sortByValue

    //сортировка по увеличению/уменьшению ключа
    public  Map<Integer, Double> sortByKey(Map<Integer, Double> map) {//используем K, V для разных форматов
        List<Map.Entry<Integer, Double>> list = new LinkedList<>(map.entrySet());//создаем список
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                if (flag) return (o1.getKey()).compareTo(o2.getKey());//реверс сортировки
                else return (o2.getKey()).compareTo(o1.getKey());//реверс сортировки
            }//помещаем список в колекцию
        });
        flag = !flag;//реверс сортировки

        Map<Integer, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());//перезаписываем заново
        }
        return result;
    }//sortByKey

    //вывод всех double из заданного диапазона значений
    public Map<Integer, Double> findDouble(double min, double max) {
        Map<Integer, Double> newHashMap = new HashMap<>();

        for (Map.Entry entry : hashMap.entrySet()) {
            int key = (int) entry.getKey();
            double value = (double) entry.getValue();

            if (value > min && value < max) newHashMap.put(key, value);
        }
        return newHashMap;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry entry : hashMap.entrySet()) {
            sb.append(String.valueOf(entry.getKey()) + " --> ");    //берем значения
            sb.append(String.valueOf(entry.getValue() + "\n"));
        }
        String str = sb.toString();
        return str;
    }

    public ArrayList<String> getArrayListString() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Map.Entry entry : hashMap.entrySet()) {
            arrayList.add(String.valueOf(entry.getKey()) + " --> " + (entry.getValue()));    //берем значения
        }
        return arrayList;
    }
}
