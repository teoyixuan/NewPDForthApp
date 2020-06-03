package sg.edu.rp.c346.newpdforthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_Page extends AppCompatActivity {

    EditText etRemarks, etDate, etAmount;
    Button btnDone, btnToday;
    RadioGroup rg;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__page);

        etRemarks = findViewById(R.id.etRemark);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        btnDone = findViewById(R.id.btnDone);
        rg = findViewById(R.id.rg);
        btnToday = findViewById(R.id.btnToday);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int time = cal.get(Calendar.HOUR_OF_DAY);


                DatePickerDialog dialog = new DatePickerDialog(
                        Add_Page.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = "";
                month = month + 1;
                if(day < 10){
                     date = "0" + day + "/" + month + "/" + year;
                    if (month < 10){
                        date = "0" + day + "/" + "0" + month + "/" + year;
                    }
                } else{
                    if (month < 10){
                        date = day + "/" + "0" + month + "/" + year;
                    }
                    date = day + "/" + month + "/" + year;
                }
                etDate.setText(date);
            }
        };

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = getDate();
                etDate.setText(date);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etRemarks.getText().length() == 0 || etAmount.getText().length() == 0 || etDate.getText().length() == 0 || rg.getCheckedRadioButtonId() == -1){
                    Toast.makeText(Add_Page.this, "Incomplete data",
                            Toast.LENGTH_SHORT).show();
                } else{
                    String remark = etRemarks.getText().toString();
                    float amount = Float.parseFloat(etAmount.getText().toString());
                    String date = etDate.getText().toString();
                    int radioButtonID = rg.getCheckedRadioButtonId();
                    View radioButton = (RadioButton) findViewById(radioButtonID);
                    int idx = rg.indexOfChild(radioButton);

                    int icon = 0;
                    if (idx == 0){
                        icon = R.drawable.minus22;
                    }
                    else{
                        icon = R.drawable.plus;
                    }

                    DBHelper dbh = new DBHelper(Add_Page.this);
                    long inserted_id = dbh.insertData(date, remark, icon, amount);
                    dbh.close();


                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    protected String getDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        Log.i("Today's date : ", formattedDate);
        return formattedDate;
    }
}
