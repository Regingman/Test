package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.Model.Post;
import com.example.test.Service.ApiClient;
import com.example.test.Service.PostService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    Long length[] = new Long[30];
    List<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        PostService postService = ApiClient.getClient().create(PostService.class);
        Call call = postService.getPosts();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                posts = (List<Post>) response.body();
                listView.setAdapter(new PostAdapter(getApplicationContext(), posts, length));
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ActivityAnother.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                intent.putExtra("id", posts.get(position).getId());
                startActivity(intent);


            }
        });
    }

    class PostAdapter extends ArrayAdapter<Long> {

        private Context context;
        private List<Post> posts = null;

        PostAdapter(Context c, List<Post> posts, Long[] length) {
            super(c, R.layout.row, R.id.myTitle, length);
            this.context = c;
            this.posts = posts;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.row, parent, false);

            TextView myTitle = row.findViewById(R.id.myTitle);
            TextView mySybtitle= row.findViewById(R.id.subtitle);
            TextView myNumber = row.findViewById(R.id.myNumber);
            myTitle.setText(posts.get(position).getTitle());
            mySybtitle.setText(posts.get(position).getBody());
            Log.d("MyNumber", posts.get(position).getId() + "");
            myNumber.setText(posts.get(position).getId()+"");
            return row;
        }
    }

}
