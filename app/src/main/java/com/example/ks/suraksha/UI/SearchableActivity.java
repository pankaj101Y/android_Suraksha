package com.example.ks.suraksha.UI;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.ks.suraksha.Adapter.AdmissionAdapter;
import com.example.ks.suraksha.Adapter.RecyclerClickListener;
import com.example.ks.suraksha.Adapter.SearchAdapter;
import com.example.ks.suraksha.Database.DatabaseManager;
import com.example.ks.suraksha.MainActivity;
import com.example.ks.suraksha.R;
import com.example.ks.suraksha.Utility.Constants;

public class SearchableActivity extends AppCompatActivity {
    private RecyclerView searchRecycler;
    private SearchAdapter searchAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        searchRecycler=findViewById(R.id.search_recyclerView);
        gridLayoutManager=new GridLayoutManager(this,1);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String name) {
        searchAdapter=new SearchAdapter(DatabaseManager.getDatabaseManager(this).getSearchResult(name), new RecyclerClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent viewChildIntent=new Intent(SearchableActivity.this, ChildActivity.class);
                viewChildIntent.putExtra("admissionIndex",position);
                viewChildIntent.putExtra("sendingActivity", Constants.SEARCHABLE);
                startActivity(viewChildIntent);
            }
        });
        searchRecycler.setLayoutManager(gridLayoutManager);
        searchRecycler.setAdapter(searchAdapter);
    }
}
