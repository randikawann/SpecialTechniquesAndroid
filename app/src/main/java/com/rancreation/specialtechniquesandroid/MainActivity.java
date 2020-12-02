package com.rancreation.specialtechniquesandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.rancreation.specialtechniquesandroid.Adapter.UserAdapter;
import com.rancreation.specialtechniquesandroid.model.Feed;
import com.rancreation.specialtechniquesandroid.model.user.User;
import com.rancreation.specialtechniquesandroid.remote.UserService;
import com.rancreation.specialtechniquesandroid.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    private String BASE_URL = "https://jsonplaceholder.typicode.com/";
    public UserService mService;

    RecyclerView userRecyclerview;
    UserAdapter userAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("12345","on create");

        userRecyclerview = findViewById(R.id.usersrecyclerview);


        mService = UserViewModel.getusers(BASE_URL);

        getAllusers();



    }

    private void getAllusers(){

        mService.getAPIusers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.i("12345","response"+response.body().get(0).getName());

                List<User> allUsers = response.body();


                handleRecyclerview(allUsers);
                Log.i("1234", "users "+allUsers.toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("12345", "error"+t);
            }
        });
    }

    public void handleRecyclerview(List<User> alluser){

        userRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(alluser);
        userRecyclerview.setAdapter(userAdapter);

//        userAdapter = new UserAdapter(alluser);

    }
}

/*
[
  {
    "id": 1,
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "Sincere@april.biz",
    "address": {
      "street": "Kulas Light",
      "suite": "Apt. 556",
      "city": "Gwenborough",
      "zipcode": "92998-3874",
      "geo": {
        "lat": "-37.3159",
        "lng": "81.1496"
      }
    },
    "phone": "1-770-736-8031 x56442",
    "website": "hildegard.org",
    "company": {
      "name": "Romaguera-Crona",
      "catchPhrase": "Multi-layered client-server neural-net",
      "bs": "harness real-time e-markets"
    }
  },
  {
    "id": 2,
    "name": "Ervin Howell",
    "username": "Antonette",
    "email": "Shanna@melissa.tv",
    "address": {
 */