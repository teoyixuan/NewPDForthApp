package sg.edu.rp.c346.newpdforthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineDataSet.Mode;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Comparator;

import static com.github.mikephil.charting.data.LineDataSet.Mode.CUBIC_BEZIER;

public class Summary_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Float> alData;
    private ArrayList<Float> alAvgData;
    private ArrayList<Balance> alBalance;
    TextView tvCalIncome, tvCalSpend, tvAvgIncome, tvAvgSpend, tvBoxIncome, tvBoxSpend;
    LineChart lineChart;

    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private ActionBarDrawerToggle toggle;

    int yes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary__page);

        lineChart = findViewById(R.id.lineChart);
        tvAvgIncome = findViewById(R.id.tvAvgIncome);
        tvAvgSpend = findViewById(R.id.tvAvgSpend);
        tvCalIncome = findViewById(R.id.tvCalIncome);
        tvCalSpend = findViewById(R.id.tvCalSpend);
        tvBoxIncome = findViewById(R.id.tvBoxIncome);
        tvBoxSpend = findViewById(R.id.tvBoxSpend);

        tvBoxIncome.setBackgroundColor(Color.GRAY);
        tvBoxSpend.setBackgroundColor(Color.GRAY);

        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav_view.setNavigationItemSelectedListener(this);
        nav_view.setCheckedItem(R.id.summary);

        DBHelper db = new DBHelper(Summary_Page.this);
        alData = db.getData();
        alAvgData = db.getAvgData();
        alBalance = db.getBalance();
        db.close();

        Log.d("alBalance", "onCreate: " + alBalance.get(0).getDateListIncome());

        float income = Float.valueOf(alData.get(0).toString().trim()).floatValue();
        float spend = Float.valueOf(alData.get(1).toString().trim()).floatValue();
        float avgIncome = Float.valueOf(alAvgData.get(0).toString().trim()).floatValue();
        float avgSpend = Float.valueOf(alAvgData.get(1).toString().trim()).floatValue();

        tvCalIncome.setText("" + income);
        tvCalSpend.setText("" + spend);
        tvAvgIncome.setText("Average income /day: $ " + avgIncome);
        tvAvgSpend.setText("Average spend /day: $ " + avgSpend);

        refreshChart(yes);

        tvBoxIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yes != 1) {
                    tvBoxIncome.setBackgroundColor(Color.BLUE);
                    tvBoxSpend.setBackgroundColor(Color.GRAY);
                    yes = 1;
                } else {
                    tvBoxIncome.setBackgroundColor(Color.GRAY);
                    yes = 0;
                }
                refreshChart(yes);
            }
        });

        tvCalIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yes != 1) {
                    tvBoxIncome.setBackgroundColor(Color.BLUE);
                    tvBoxSpend.setBackgroundColor(Color.GRAY);
                    yes = 1;
                } else {
                    tvBoxIncome.setBackgroundColor(Color.GRAY);
                    yes = 0;
                }
                refreshChart(yes);
            }
        });

        tvCalSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(yes != 2){
                    tvBoxSpend.setBackgroundColor(Color.BLUE);
                    tvBoxIncome.setBackgroundColor(Color.GRAY);
                    yes = 2;
                } else {
                    tvBoxSpend.setBackgroundColor(Color.GRAY);
                    yes = 0;
                }
                refreshChart(yes);
            }
        });

        tvBoxSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(yes != 2){
                    tvBoxSpend.setBackgroundColor(Color.BLUE);
                    tvBoxIncome.setBackgroundColor(Color.GRAY);
                    yes = 2;
                } else {
                    tvBoxSpend.setBackgroundColor(Color.GRAY);
                    yes = 0;
                }
                refreshChart(yes);
            }
        });
    }

    public void refreshChart(int yes){
        if (yes == 1) {
            // for income
            ArrayList<Float> incomeList = alBalance.get(0).getIncomeList();
            ArrayList<String> dateIncomeList = alBalance.get(0).getDateListIncome();
            ArrayList<Entry> lineEntries = new ArrayList<>();

            for (int i = 0; i < incomeList.size(); i++) {
                lineEntries.add(new Entry(i + 1, incomeList.get(i)));
            }
            Log.d("11111111111", "onCreate: " + dateIncomeList);

            if(!dateIncomeList.get(0).equals("hi")){
                dateIncomeList.add(0, "hi");
            }

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(dateIncomeList));
            xAxis.setGranularity(1);
            xAxis.setAxisMinimum(1);

            LineDataSet lineDataSet = new LineDataSet(lineEntries, "Data Set 1");
            // end for income
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet.setColor(Color.BLUE);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);

            LineData data = new LineData(dataSets);
            lineChart.setData(data);
            lineChart.invalidate();
        } else if (yes == 0){
            // for balance
            // first line
            ArrayList<Float> incomeList = alBalance.get(0).getIncomeList();
            ArrayList<String> dateIncomeList = alBalance.get(0).getDateListIncome();
            ArrayList<Entry> lineEntries = new ArrayList<>();
            for (int i = 0; i < incomeList.size(); i++) {
                lineEntries.add(new Entry(i + 1, incomeList.get(i)));
            }
            Log.d("11111111111", "onCreate: " + dateIncomeList);
            LineDataSet lineDataSet = new LineDataSet(lineEntries, "Income");
            lineDataSet.setColor(Color.BLUE);
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            // second line
            ArrayList<Float> spendList = alBalance.get(0).getSpendList();
            ArrayList<String> dateSpendList = alBalance.get(0).getDateListSpend();
            ArrayList<Entry> lineEntries2 = new ArrayList<>();
            for (int i = 0; i < spendList.size(); i++) {
                lineEntries2.add(new Entry(i + 1, spendList.get(i)));
            }
            LineDataSet lineDataSet1 = new LineDataSet(lineEntries2, "Spend");
            lineDataSet1.setColor(Color.RED);
            lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            if(dateIncomeList.size() != 0 && !dateIncomeList.get(0).equals("hi")){
                dateIncomeList.add(0, "hi");
            }

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(dateIncomeList));
            xAxis.setGranularity(1);
            xAxis.setAxisMinimum(1);
            // end for balance
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);
            dataSets.add(lineDataSet1);
            LineData data = new LineData(dataSets);
            lineChart.setData(data);
            lineChart.invalidate();
        } else if (yes == 2){
            ArrayList<Float> spendList = alBalance.get(0).getSpendList();
            ArrayList<String> dateSpendList = alBalance.get(0).getDateListSpend();
            ArrayList<Entry> lineEntries = new ArrayList<>();

            for (int i = 0; i < spendList.size(); i++) {
                lineEntries.add(new Entry(i + 1, spendList.get(i)));
            }
            Log.d("11111111111", "onCreate: " + dateSpendList);

            if(!dateSpendList.get(0).equals("hi")){
                dateSpendList.add(0, "hi");
            }

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(dateSpendList));
            xAxis.setGranularity(1);
            xAxis.setAxisMinimum(1);

            LineDataSet lineDataSet = new LineDataSet(lineEntries, "Data Set 1");
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet.setColor(Color.RED);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);

            LineData data = new LineData(dataSets);
            lineChart.setData(data);
            lineChart.invalidate();
        }
    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent intent = new Intent(Summary_Page.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.summary:
                break;
            case R.id.history:
                Intent intent1 = new Intent(Summary_Page.this, History_Page.class);
                startActivity(intent1);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}