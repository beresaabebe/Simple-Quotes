package com.beckytech.simplequotes;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://type.fit/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void GetAllQuotes(QuoteResponseListener listener) {
        CallQuotes callQuotes = retrofit.create(CallQuotes.class);
        Call<List<QuoteResponse>> call = callQuotes.callQuotes();
        call.enqueue(new Callback<List<QuoteResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<QuoteResponse>> call, @NonNull Response<List<QuoteResponse>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Request not successful!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<List<QuoteResponse>> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallQuotes {
        @GET("api/quotes")
        Call<List<QuoteResponse>> callQuotes();
    }
}
