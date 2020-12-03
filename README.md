# SpecialTechniquesAndroid

## Recycler view added

### activity_main
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/usersrecyclerview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</RelativeLayout>
```

### user_car
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <LinearLayout
        android:orientation="horizontal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">

        <ImageView
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:src="@drawable/ic_launcher_background" />

        <LinearLayout
            android:layout_marginRight="100dp"
            android:paddingLeft="10dp"
            android:paddingTop="5dp"
            android:orientation="vertical"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <TextView
                android:id="@+id/usename"
                android:text="User name"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>

            <TextView
                android:id="@+id/useraddress"
                android:text="Address"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>

        </LinearLayout>
        
        <ImageView
            android:layout_marginRight="5dp"
            android:src="@drawable/call"
            android:layout_gravity="center"
            android:padding="10dp"
            android:layout_width="40dp"
            android:layout_height="40dp"/>


    </LinearLayout>

</RelativeLayout>
```
### UserAdapter
```
package com.rancreation.specialtechniquesandroid.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rancreation.specialtechniquesandroid.R;
import com.rancreation.specialtechniquesandroid.model.user.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    List<User> allUsers;


    public UserAdapter(List<User> allUsers) {

        this.allUsers = allUsers;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new UserViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        String name = allUsers.get(position).getName();
        String address = allUsers.get(position).getAddress().toString();


        holder.name.setText(name);
        holder.address.setText(address);



    }

    @Override
    public int getItemCount() {
        return allUsers.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView address;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.usename);
            address = itemView.findViewById(R.id.useraddress);


        }
    }
}

```

### Main Activity
only for Recycler view code
```
public class MainActivity extends AppCompatActivity {
  RecyclerView userRecyclerview;
  UserAdapter userAdapter;
  
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("12345","on create");

        userRecyclerview = findViewById(R.id.usersrecyclerview);

        // no need to recycler view
        // mService = UserViewModel.getusers(BASE_URL);

        // getAllusers();



    }
    
  // Main Activity has also data fetch layer, After data fetching values pass to this function
   public void handleRecyclerview(List<User> alluser){

        userRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(alluser);
        userRecyclerview.setAdapter(userAdapter);

    }
}
```

## Retrofit for data retrieving
### MainActivity
```
public class MainActivity extends AppCompatActivity {

    private String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public UserService mService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // userRecyclerview = findViewById(R.id.usersrecyclerview);
        
        mService = UserViewModel.getusers(BASE_URL);
        getAllusers();
    }
    
    private void getAllusers(){

        mService.getAPIusers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                Log.i("12345","response"+response.body().get(0).getName());

                List<User> allUsers = response.body();

                //this use for pass fetched data to another function
                handleRecyclerview(allUsers);
                
//                Log.i("1234", "users "+allUsers.toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("12345", "error"+t);
            }
        });
    }
    
}
```
## snippt of JSON
```
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
      "name"
```
### UserViewModel
```
public class UserViewModel {

//    private String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static UserService getusers(String BASE_URL){

        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
```
### RetrofitClient
```
package com.rancreation.specialtechniquesandroid.remote;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit =null;

    public static Retrofit getClient(String baseURL){

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}

```
### User Service
this is access from main clss within getAllUsers() function
```
package com.rancreation.specialtechniquesandroid.remote;

import com.rancreation.specialtechniquesandroid.model.Feed;
import com.rancreation.specialtechniquesandroid.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("users")
    Call<List<User>> getAPIusers();

}
```
### UsrModel
User.java
```
package com.rancreation.specialtechniquesandroid.model.user;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rancreation.specialtechniquesandroid.model.user.address.Address;
import com.rancreation.specialtechniquesandroid.model.user.address.geo.Geo;

public class User {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("address")
    @Expose
    private Address address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

/*
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
*/

```
Address.java
```
package com.rancreation.specialtechniquesandroid.model.user.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rancreation.specialtechniquesandroid.model.user.address.geo.Geo;

public class Address {
    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("suite")
    @Expose
    private String suite;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    @SerializedName("geo")
    @Expose
    private Geo geo;
}

```
Geo.java
```
package com.rancreation.specialtechniquesandroid.model.user.address.geo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geo {

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("lng")
    @Expose
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
```

## Whole Project Gradle Dependencies
```
//retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    //retrofit gson
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```


