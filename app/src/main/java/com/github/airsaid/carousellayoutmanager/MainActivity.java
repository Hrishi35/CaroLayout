package com.github.airsaid.carousellayoutmanager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.airsaid.carousellayoutmanager.widget.recycler.CarouselLayoutManager;
import com.github.airsaid.carousellayoutmanager.widget.recycler.CarouselZoomPostLayoutListener;
import com.github.airsaid.carousellayoutmanager.widget.recycler.CenterScrollListener;
import com.github.airsaid.carousellayoutmanager.widget.recycler.DefaultChildSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<ImageList> mLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initAdapter();
    }

    private void initAdapter() {

        mLists = new ArrayList<>();
//        for (String cover : covers) {
//            mLists.add(cover);
//        }
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please Wait ...");
        dialog.show();
        String url = "https://qmine.000webhostapp.com/image/retrieve.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    Log.d("sdf", "response" + response);
                    JSONArray array = object.getJSONArray("images");
                    Log.d("jjk", "array" + array);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        ImageList img = new ImageList();
                        img.setImageurl(ob.getString("url"));

                        Log.d("aa", "img" + img);
                        mLists.add(img);
                        Log.d("aa", "list" + mLists);
                    }
                    CarouselLayoutManager manager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, false);
                    manager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
                    manager.setMaxVisibleItems(6);
                    mRecyclerView.setLayoutManager(manager);
                    mRecyclerView.setHasFixedSize(true);
                    MyAdapter adapter = new MyAdapter(getApplicationContext(),mLists);
                    Log.d("list","here"+mLists);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.addOnScrollListener(new CenterScrollListener());

                    DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
                        @Override
                        public void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v) {
                            final int position = recyclerView.getChildLayoutPosition(v);
                            final String msg = String.format(Locale.CHINA, "Item %1$d was clicked", position);
                           // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }, mRecyclerView, manager);

                    manager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {
                        @Override
                        public void onCenterItemChanged(int adapterPosition) {
                            Log.e("test", "onCenterItemChanged position: " + adapterPosition);
                        }
                    });

                    mRecyclerView.smoothScrollToPosition(0);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);



    }

}
