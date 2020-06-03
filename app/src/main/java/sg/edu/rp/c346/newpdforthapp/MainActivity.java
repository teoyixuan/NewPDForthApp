package sg.edu.rp.c346.newpdforthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // menu
    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private ActionBarDrawerToggle toggle;

    ConstraintLayout con, constraint;
    ListView lv;
    ArrayAdapter aa;
    ArrayList<Data> al;
    ArrayList<Float> alSpendIncome;
    Button btnAdd;
    TextView tvIncomeNum, tvSpendNum, tvBalanceNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // menu
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav_view.setNavigationItemSelectedListener(this);
        nav_view.setCheckedItem(R.id.home);


        tvIncomeNum = findViewById(R.id.tvIncomeNum);
        tvSpendNum = findViewById(R.id.tvSpendNum);
        tvBalanceNum = findViewById(R.id.tvBalanceNum);
        con = findViewById(R.id.con);
        constraint = findViewById(R.id.constraint);
        constraint.bringChildToFront(btnAdd);
        constraint.invalidate();

        btnAdd = findViewById(R.id.buttonAdd);
        btnAdd.bringToFront();
        btnAdd.invalidate();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Add_Page.class);
                startActivityForResult(i, 9);
            }
        });

        lv = findViewById(R.id.lv);
        DBHelper db = new DBHelper(MainActivity.this);
        al = db.getAllData();
        alSpendIncome = db.getData();
        db.close();
        aa = new arrayAdapter(MainActivity.this, R.layout.row_main_page, al);
        lv.setAdapter(aa);

        if (alSpendIncome.size() != 0) {
            float income = Float.valueOf(alSpendIncome.get(0).toString().trim()).floatValue();
            float spend = Float.valueOf(alSpendIncome.get(1).toString().trim()).floatValue();
            Log.d("1231231", "displayIncome: " + income);
            Log.d("1231231", "displaySpend: " + spend);
            tvSpendNum.setText("$ " + spend);
            tvIncomeNum.setText("$ " + income);
            float balance = income - spend;
            if (balance < 0){
                balance = 0;
            }
            tvBalanceNum.setText("$ " + balance);
        }

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int lastFirstVisibleItem;
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int i1, int totalItemCount) {
                if (totalItemCount > 12) {
                    if (lastFirstVisibleItem < firstVisibleItem) {
                        con.setVisibility(View.GONE);
                        lv.setPadding(0, 0, 0, 0);
                        Toast.makeText(getApplicationContext(), "Scrolling down the listView",
                                Toast.LENGTH_SHORT).show();
                    }
                    if (lastFirstVisibleItem > firstVisibleItem) {
                        con.setVisibility(View.VISIBLE);
                        lv.setPadding(0,180,0,0);
                        Toast.makeText(getApplicationContext(), "Scrolling up the listView",
                                Toast.LENGTH_SHORT).show();
                    }
                    lastFirstVisibleItem = firstVisibleItem;
                }
            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper db = new DBHelper(MainActivity.this);
            al.clear();
            alSpendIncome.clear();
            al = db.getAllData();
            alSpendIncome = db.getData();
            db.close();
            aa = new arrayAdapter(MainActivity.this, R.layout.row_main_page, al);
            lv.setAdapter(aa);

            if (alSpendIncome.size() != 0) {
                float income = Float.valueOf(alSpendIncome.get(0).toString().trim()).floatValue();
                float spend = Float.valueOf(alSpendIncome.get(1).toString().trim()).floatValue();
                Log.d("123123", "displayIncome: " + income);
                Log.d("123123", "displaySpend: " + spend);
                tvSpendNum.setText("$ " + spend);
                tvIncomeNum.setText("$ " + income);
                float balance = income - spend;
                if (balance < 0){
                    balance = 0;
                }
                tvBalanceNum.setText("$ " + balance);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                break;
            case R.id.summary:
                Intent intent = new Intent(MainActivity.this, Summary_Page.class);
                startActivity(intent);
                break;
            case R.id.history:
                Intent intent1 = new Intent(MainActivity.this, History_Page.class);
                startActivity(intent1);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
