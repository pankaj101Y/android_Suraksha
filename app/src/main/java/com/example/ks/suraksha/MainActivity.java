package com.example.ks.suraksha;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.ks.suraksha.Adapter.AdmissionAdapter;
import com.example.ks.suraksha.Adapter.RecyclerClickListener;
import com.example.ks.suraksha.UI.AddAdmissionActivity;
import com.example.ks.suraksha.UI.AddFiltersActivity;
import com.example.ks.suraksha.UI.ChildActivity;
import com.example.ks.suraksha.UI.LoginSignUpActivity;
import com.example.ks.suraksha.UI.ProfileActivity;
import com.example.ks.suraksha.UI.SearchableActivity;
import com.example.ks.suraksha.UI.StatsActivity;
import com.example.ks.suraksha.Utility.Constants;
import com.example.ks.suraksha.Utility.Stats;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mainRecycler;
    private AdmissionAdapter admissionAdapter;
    private GridLayoutManager gridLayoutManager;
    private int selected=-1;
    private static final int FILTER_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        ImageView v= navigationView.getHeaderView(0).findViewById(R.id.imageView);
        v.setImageResource(R.drawable.logo);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addChildIntent=new Intent(MainActivity.this, AddAdmissionActivity.class);
                startActivity(addChildIntent);
            }
        });

        mainRecycler=findViewById(R.id.main_recyclerView);
        admissionAdapter=new AdmissionAdapter(this, new RecyclerClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent viewChildIntent=new Intent(MainActivity.this, ChildActivity.class);
                viewChildIntent.putExtra("admissionIndex",position);
                viewChildIntent.putExtra("sendingActivity", Constants.MAIN);
                startActivity(viewChildIntent);
            }
        });
        gridLayoutManager=new GridLayoutManager(this,1);
        mainRecycler.setLayoutManager(gridLayoutManager);
        mainRecycler.setAdapter(admissionAdapter);

        fab.setImageResource(R.drawable.add_child);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                if (selected!=-1&&newState==DrawerLayout.STATE_IDLE){
                    switch (selected){
                        case 0:
                            Intent loopIntent=new Intent(MainActivity.this,MainActivity.class);
                            startActivity(loopIntent);
                            selected=-1;
                            finish();
                            break;
                        case 1:
                            Intent filterIntent=new Intent(MainActivity.this,AddFiltersActivity.class);
                            startActivityForResult(filterIntent,FILTER_REQUEST);
                            selected=-1;
                            break;

                        case 4:
                            AppData.clearCredentials(MainActivity.this);
                            Intent restartIntent=new Intent(MainActivity.this,MainActivity.class);
                            startActivity(restartIntent);
                            selected=-1;
                            finish();
                            break;
                        case 2:
                            Intent statsIntent=new Intent(MainActivity.this, StatsActivity.class);
                            startActivity(statsIntent);
                            selected=-1;
                            break;

                        case 3:
                            Intent profileIntent=new Intent(MainActivity.this, ProfileActivity.class);
                            startActivity(profileIntent);
                            selected=-1;
                    }
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        startUpChecks();
    }

    private void startUpChecks() {
        if (!AppData.isSignUp(this)){
            Intent signUpIntent=new Intent(this, LoginSignUpActivity.class);
            startActivity(signUpIntent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName componentName=new ComponentName(this, SearchableActivity.class);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName));
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.Filters){
            selected=1;
        }else if (id==R.id.LogOut)
            selected=4;
        else if (id==R.id.Statics)
            selected=2;
        else if (id==R.id.Admissions)
            selected=0;
        else if (id==R.id.Profile)
            selected=3;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK&&requestCode==FILTER_REQUEST){
            if (data.getBooleanExtra("isFilterHasResult",false))
                admissionAdapter.notifyDataSetChanged();
            else
                Toast.makeText(getApplicationContext(),"NO RESULT FOR FILTERS",Toast.LENGTH_LONG).show();
        }
    }
}
