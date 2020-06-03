package sg.edu.rp.c346.newpdforthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class History_Page extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener
{

    ListView lvHistory;
    ArrayAdapter aa;

//    private DrawerLayout drawerLayout;
//    private NavigationView nav_view;
//    private ActionBarDrawerToggle toggle;

    private ArrayList<Data> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history__page);

        lvHistory = findViewById(R.id.lvHistory);

//        drawerLayout = findViewById(R.id.drawer_layout);
//        nav_view = findViewById(R.id.nav_view);
//        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        nav_view.setNavigationItemSelectedListener(this);
//        nav_view.setCheckedItem(R.id.home);

        DBHelper db = new DBHelper(History_Page.this);
        al = db.getAllData();
        db.close();
        aa = new arrayAdapterSearch(History_Page.this, R.layout.row_search, al);
        lvHistory.setAdapter(aa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                aa.getFilter().filter(s);
                Log.d("hi", "onQueryTextChange" + s);
                return false;
            }
        });

        return true;
    }

//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (toggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.home:
//                Intent intent1 = new Intent(History_Page.this, MainActivity.class);
//                startActivity(intent1);
//                break;
//            case R.id.summary:
//                Intent intent = new Intent(History_Page.this, Summary_Page.class);
//                startActivity(intent);
//                break;
//            case R.id.history:
//                break;
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
