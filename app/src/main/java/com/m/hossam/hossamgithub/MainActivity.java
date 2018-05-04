package com.m.hossam.hossamgithub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.m.hossam.hossamgithub.Adapters.Main_Activity_Adapter;
import com.m.hossam.hossamgithub.Helpers.ConnectivityReceiver;
import com.m.hossam.hossamgithub.Helpers.Json_Controller;
import com.m.hossam.hossamgithub.Helpers.VolleyCallback;
import com.m.hossam.hossamgithub.Items.Main_Activity_Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ArrayList<Main_Activity_Item> items;
    private Main_Activity_Adapter adapter;
    LinearLayoutManager layoutManager;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int page = 1; /// default number to load first page
    private boolean loading = true;
    private SwipeRefreshLayout refreshLayout;

    List<String> list; /// adding checked items to list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>(); /// checked items list

        Button filter_click = findViewById(R.id.filter_click);
        filter_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_button_method();
            }
        });


        //////////////////////// identifying layout rows
        recycler = findViewById(R.id.main_list);
        recycler.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 1);
        recycler.setLayoutManager(layoutManager);
        items = new ArrayList<>();
        adapter = new Main_Activity_Adapter(items, MainActivity.this);
        recycler.setAdapter(adapter);
        ////////////////////////

        ////////////////////// to reload data and get new updates at swipe down
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (items != null) {
                    items.clear();
                    page = 1;
                }
                load_data(String.valueOf(page));
                refreshLayout.setRefreshing(false);
            }
        });
        //////////////////////


        //////////////////// Start of pagination
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 8) {/// add new items when scroll at last 8 items out of 10
                            loading = false;
                            Log.v("last...", "Last Item Wow !");
                            page = page + 1;
                            getLastPostsionNext(String.valueOf(page));
                        }
                    }
                }
            }
        });
        //////////////////// End of pagination


        load_data(String.valueOf(page)); ///// load data when app open

    }



    private void filter_button_method() {/////////////// checking list size and adding checked items into array then saving it in SharedPreferences as an array
        int searchListLength = items.size();
        for (int i = 0; i < searchListLength; i++) {
            if (items.get(i).isChecked()) {
                items.get(i).getId();
                Log.d("listitem", String.valueOf("id=" + items.get(i).getId()));
                list.add("id=" + items.get(i).getId());
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s);
            stringBuilder.append("&");
        }

        if (list.size() == 0) {
            Toast.makeText(MainActivity.this, getString(R.string.please_select_items), Toast.LENGTH_SHORT).show();
            list.clear();
        } else if (list.size() > 10) {
            Toast.makeText(MainActivity.this, getString(R.string.cant_select_more), Toast.LENGTH_SHORT).show();
            list.clear();
        } else {
            list.clear();
            SharedPreferences notif_array = getSharedPreferences("items_array", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_notaif = notif_array.edit();
            editor_notaif.putString("id", stringBuilder.toString());
            editor_notaif.apply();
            Intent intent = new Intent(MainActivity.this, Filtered_Activity.class);
            startActivity(intent);
        }

    }

    


    /////////////////////////////////////Start of Onstart Load method
    private void load_data(String pages) {
        if (ConnectivityReceiver.isConnected()) {

            new Json_Controller().GetDataFromServer(new VolleyCallback() { //Loading with progress dialog
                @Override
                public void onSuccess(String result) throws JSONException {
                    JSONArray array = new JSONArray(result);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        Main_Activity_Item model = new Main_Activity_Item();
                        model.setId(object.getString("id"));
                        model.setTitle(object.getString("title"));
                        model.setAlbum_id(object.getString("albumId"));
                        model.setPhoto(object.getString("thumbnailUrl"));
                        model.setFiltered("show");
                        items.add(model);
                    }
                    adapter = new Main_Activity_Adapter(items, MainActivity.this);
                    recycler.setAdapter(adapter);
                }

                @Override
                public void onError(VolleyError error) {
                }
            }, this, "https://jsonplaceholder.typicode.com/photos?_page=" + pages, false);

        } else {
            Toast.makeText(MainActivity.this, R.string.turn_net, Toast.LENGTH_LONG).show();
        }
    }
    /////////////////////////////////////End of Onstart Load method


    /////////////////////////////////////start of pagination method
    private void getLastPostsionNext(String pages) {
        if (ConnectivityReceiver.isConnected()) {

            new Json_Controller().GetDataFromServer_Without_bar(new VolleyCallback() { // to hide the loading progress while scrolling
                @Override
                public void onSuccess(String result) throws JSONException {
                    loading = true;
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        Main_Activity_Item model = new Main_Activity_Item();
                        model.setId(object.getString("id"));
                        model.setTitle(object.getString("title"));
                        model.setAlbum_id(object.getString("albumId"));
                        model.setPhoto(object.getString("thumbnailUrl"));
                        model.setFiltered("show");
                        items.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onError(VolleyError error) {
                }
            }, this, "https://jsonplaceholder.typicode.com/photos?_page=" + pages, false);

        } else {
            Toast.makeText(MainActivity.this, R.string.turn_net, Toast.LENGTH_LONG).show();
        }
    }
    /////////////////////////////////////End of pagination method


    @Override
    protected void onDestroy() {
        SharedPreferences.Editor clear = MainActivity.this.getSharedPreferences("items_array", Context.MODE_PRIVATE).edit();
        clear.clear();
        clear.apply();
        super.onDestroy();
    }
}
