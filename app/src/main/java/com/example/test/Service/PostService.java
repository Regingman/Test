package com.example.test.Service;

import com.example.test.Model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {
    @GET("posts")
    Call<List<Post>> getPosts();
}
