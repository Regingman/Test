package com.example.test.Service;

import com.example.test.Model.Comments;
import com.example.test.Model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommentService {
    @GET("comments")
    Call<List<Comments>> getComment(@Query("postId") int id);
}
