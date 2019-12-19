package com.example.museus;

import com.example.museus.models.Museums;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiRest {
    String BASE_URL = "https://do.diba.cat/api/dataset/museus/format/json/";

    //We get the Data from the API
    @GET("pag-ini/{numinici}/pag-fi/{numfinal}")
    Call<Museums> getData(@Path("numinici") int pagini, @Path("numfinal") int pagfi);

    static ApiRest createAPIRest() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ApiRest.class);
    }
}

