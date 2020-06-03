package sg.edu.rp.c346.newpdforthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ForthApp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DATA = "data";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_REMARK = "remark";
    private static final String COLUMN_ICON = "icon";
    private static final String COLUMN_AMOUNT = "amount";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Note
        // (id INTEGER PRIMARY KEY AUTOINCREMENT, note_content TEXT, rating
        // INTEGER );
        String createNoteTableSql = "CREATE TABLE " + TABLE_DATA + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT, "
                + COLUMN_REMARK + " TEXT, "
                + COLUMN_ICON + " INTEGER, "
                + COLUMN_AMOUNT + " REAL )";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

    public Long insertData(String date, String remark, int icon, float amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_REMARK, remark);
        values.put(COLUMN_ICON, icon);

        long result = db.insert(TABLE_DATA, null, values);
        db.close();
        Log.d("SQL Insert", "ID:" + result); //id returned, shouldn’t be -1

        return result;
    }


    public ArrayList<Data> getAllData() {
        ArrayList<Data> datas = new ArrayList<Data>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_DATE + ","
                + COLUMN_REMARK + ","
                + COLUMN_ICON + ","
                + COLUMN_AMOUNT + "  FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                String remark = cursor.getString(2);
                int icon = cursor.getInt(3);
                float amount = cursor.getFloat(4);

                Data data = new Data(id, icon, amount, date, remark);
                datas.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return datas;
    }

    public ArrayList<Float> getData() {
        ArrayList<Float> datas = new ArrayList<Float>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_AMOUNT + ","
                + COLUMN_ICON + "  FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        float income = 0;
        float spend = 0;
        float mostIncome = 0;
        float mostSpend = 0;
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                float amount = cursor.getFloat(1);
                int icon = cursor.getInt(2);

                if (icon == 2131230868) {
                    income += amount;
                    if (amount > mostIncome) {
                        mostIncome = amount;
                    }
                } else if (icon == 2131230845) {
                    spend += amount;
                    if (amount > mostSpend) {
                        mostSpend = amount;
                    }
                }

            } while (cursor.moveToNext());
        }
        datas.add(income);
        datas.add(spend);
        datas.add(mostIncome);
        datas.add(mostSpend);
        cursor.close();
        db.close();
        return datas;
    }

    public ArrayList<Float> getAvgData() {
        ArrayList<Float> datas = new ArrayList<Float>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_AMOUNT + ","
                + COLUMN_ICON + ","
                + COLUMN_DATE + "  FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        float avgIncome = 0;
        float avgSpend = 0;
        float income = 0;
        float spend = 0;
        ArrayList<String> dateListSpend = new ArrayList<String>();
        ArrayList<String> dateListIncome = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                float amount = cursor.getFloat(1);
                int icon = cursor.getInt(2);
                String date = cursor.getString(3);

                if (icon == 2131230868) {
                    income += amount;
                    if (!dateListIncome.contains(date)) {
                        dateListIncome.add(date);
                    }
                } else if (icon == 2131230845) {
                    spend += amount;
                    if (!dateListSpend.contains(date)) {
                        dateListSpend.add(date);
                    }
                }

                avgIncome = income / dateListIncome.size();
                avgSpend = spend / dateListSpend.size();
                // if date is not inside the list
                // add the date inside the list
                // if the date is inside the list just add the amount

            } while (cursor.moveToNext());
        }
        datas.add(income);
        datas.add(spend);
        datas.add(avgIncome);
        datas.add(avgSpend);

        cursor.close();
        db.close();
        return datas;
    }

    public ArrayList<Balance> getBalance() {
        ArrayList<Balance> datas = new ArrayList<Balance>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_AMOUNT + ","
                + COLUMN_ICON + ","
                + COLUMN_DATE + "  FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<Float> incomeList = new ArrayList<>();
        ArrayList<Float> spendList = new ArrayList<>();
        ArrayList<String> dateListSpend = new ArrayList<String>();
        ArrayList<String> dateListIncome = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                float amount = cursor.getFloat(1);
                int icon = cursor.getInt(2);
                String date = cursor.getString(3);

                if (icon == 2131230868) {
                    if (!dateListIncome.contains(date)) {
                        dateListIncome.add(date);
                        Collections.sort(dateListIncome, new Comparator<String>() {
                            @Override
                            public int compare(String arg0, String arg1) {
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                int compareResult = 0;
                                try {
                                    Date arg0Date = format.parse(arg0);
                                    Date arg1Date = format.parse(arg1);
                                    compareResult = arg0Date.compareTo(arg1Date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    compareResult = arg0.compareTo(arg1);
                                }
                                return compareResult;
                            }
                        });

                        for (int i = 0; i < dateListIncome.size(); i++) {
                            if (dateListIncome.get(i).equals(date)) {
                                incomeList.add(i, amount);
                            }
                        }
//                        ArrayList<Date> datess = new ArrayList<>();
//                        datess = getDates(dateListIncome.get(0), dateListIncome.get(dateListIncome.size()-1));
//                        Log.d("only for datess", "getBalance: "+ datess);
                    } else {
                        for (int i = 0; i < dateListIncome.size(); i++) {
                            if (dateListIncome.get(i).equals(date)) {
                                float number = incomeList.get(i);
                                number += amount;
                                incomeList.set(i, number);
                            }
                        }
                    }

                } else if (icon == 2131230845) {
                    if (!dateListSpend.contains(date)) {
                        dateListSpend.add(date);

                        Collections.sort(dateListSpend, new Comparator<String>() {
                            @Override
                            public int compare(String arg0, String arg1) {
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                int compareResult = 0;
                                try {
                                    Date arg0Date = format.parse(arg0);
                                    Date arg1Date = format.parse(arg1);
                                    compareResult = arg0Date.compareTo(arg1Date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    compareResult = arg0.compareTo(arg1);
                                }
                                return compareResult;
                            }
                        });

                        for (int i = 0; i < dateListSpend.size(); i++) {
                            if (dateListSpend.get(i).equals(date)) {
                                spendList.add(i, amount);
                            }
                        }

                    } else {
                        for (int i = 0; i < dateListSpend.size(); i++) {
                            if (dateListSpend.get(i).equals(date)) {
                                float number = spendList.get(i);
                                number += amount;
                                spendList.set(i, number);
                            }
                        }
                    }

                }

                // if date is not inside the list
                // add the date inside the list
                // if the date is inside the list just add the amount

            } while (cursor.moveToNext());
        }
        Balance balance = new Balance(incomeList, spendList, dateListIncome, dateListSpend);
        datas.add(balance);

        cursor.close();
        db.close();
        return datas;
    }

    public int updateData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ICON, data.getIconId());
        values.put(COLUMN_DATE, data.getDate());
        values.put(COLUMN_AMOUNT, data.getAmount());
        values.put(COLUMN_REMARK, data.getRemark());

        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_DATA, values, condition, args);
        db.close();
        return result;
    }

    public int deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_DATA, condition, args);
        db.close();
        return result;
    }

    private static ArrayList<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }
}
