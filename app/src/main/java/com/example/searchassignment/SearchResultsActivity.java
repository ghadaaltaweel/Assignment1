package com.example.searchassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.searchassignment.adapter.SearchAdapter;
import com.example.searchassignment.databinding.ActivitySearchResultsBinding;
import com.example.searchassignment.model.ImagesModel;
import com.example.searchassignment.model.UrlsModel;
import com.example.searchassignment.viewModel.SearchViewModel;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    SearchViewModel viewModel;
    ActivitySearchResultsBinding binding;
    SearchAdapter adapter;
    ImagesModel imagesModel;
    UrlsModel urlsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_results);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding.setLifecycleOwner(this);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        //setting recyclerView
        binding.rvImg.setLayoutManager(new LinearLayoutManager(this));

        imagesModel = new ImagesModel();
        urlsModel = new UrlsModel();

        String search_keyword = getIntent().getStringExtra("search_keyword");
        viewModel.search_keyword.set(search_keyword);
        binding.searchKeyword.setText(search_keyword);
        final String url = "https://api.unsplash.com/search/photos?client_id=pg3fF62PgvO5EYTzvA6zySHwvpXPgxcAUb59xBbd5EY&page=1"
                + "&query=" + search_keyword;

        viewModel.request(url);
        //Observing on list
        viewModel.listMutableLiveData.observe(this, new Observer<ArrayList<ImagesModel>>() {
            @Override
            public void onChanged(ArrayList<ImagesModel> imagesModels) {
                adapter = new SearchAdapter(SearchResultsActivity.this, imagesModels);
                binding.rvImg.setAdapter(adapter);
                binding.progressBar.setVisibility(View.GONE);
                binding.searchKeyword.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}