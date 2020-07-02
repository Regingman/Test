package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Dialog.ExampleDialog;
import com.example.test.Model.Comments;
import com.example.test.Service.ApiClient;
import com.example.test.Service.CommentService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAnother extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    List<Comments> commentList = new ArrayList<>();
    ListView listView;
    int id;
    Long length[] = new Long[30];
    //Button button;
    CommentsAdapter commentsAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                openDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        listView = findViewById(R.id.commentView);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }

        Intent intent = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        final CommentService commentService = ApiClient.getClient().create(CommentService.class);

        Call call = commentService.getComment(intent.getIntExtra("id", id));
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                commentList = (List<Comments>) response.body();
                commentsAdapter = new CommentsAdapter(getApplicationContext(), commentList, length);
                listView.setAdapter(commentsAdapter);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void applyComments(String name, String email, String desc) {
        commentList.add(new Comments(1, 1, name, email, desc));
        commentsAdapter = new CommentsAdapter(getApplicationContext(), commentList, length);
        listView.setAdapter(commentsAdapter);
    }


    public class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            openDialog();
        }
    }


    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    class CommentsAdapter extends ArrayAdapter<Comments> {

        private Context context;
        private List<Comments> comments = null;

        CommentsAdapter(Context c, List<Comments> comments, Long[] length) {
            super(c, R.layout.comment_row, R.id.name, comments);
            this.context = c;
            this.comments = comments;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.comment_row, parent, false);

            TextView myName = row.findViewById(R.id.name);
            TextView myEmail = row.findViewById(R.id.email);
            TextView myBody = row.findViewById(R.id.body);
            myName.setText(comments.get(position).getName());
            myEmail.setText(comments.get(position).getEmail());
            myBody.setText(comments.get(position).getBody());
            return row;
        }

    }

}
