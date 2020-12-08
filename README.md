# Arimac Test important things

## 4. Recycler View ONclickedLisner
most of time recycler view on clicked data should be access with activity file. but it is very dificult to handle in apater. thatswhy onclicked listner can be easily use to that.
### UserAdapter.java
```
package com.rancreation.arimactest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    JSONArray propertyArray;
    JSONObject singleproperty;
    Context context;
    ItemClickListener itemClickListener;
    
// 2. Change the constructer with ItemClickListner parameter
    public UserAdapter(JSONArray propertyArray, Context context, ItemClickListener itemClickListener) {
        this.propertyArray = propertyArray;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }


// 1. Add interface according to needed value to MainActivity
    interface ItemClickListener {
        void onItemClick(int position, JSONObject product);
    }
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        try {
            String username = propertyArray.getJSONObject(position).getString("first_name");
            String userEmail = propertyArray.getJSONObject(position).getString("email");
            String usrimage = propertyArray.getJSONObject(position).getString("avatar");
            

            holder.userName.setText(username);
            holder.userEmail.setText(userEmail);
            Glide.with(context).load(usrimage).into(holder.userimage);

//          3. Added the onclicklistner in holder.
            holder.usercardxml.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleproperty = null;
                    try {
                        singleproperty = propertyArray.getJSONObject(position);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
//           4. Added interface attribute using itemClickLister
                    itemClickListener.onItemClick(position, singleproperty);
                }
            });

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
        return propertyArray.length();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView userName2;
        TextView userEmail;
        ImageView userimage;
        LinearLayout usercardxml;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
            userimage = itemView.findViewById(R.id.user_image);
            usercardxml = itemView.findViewById(R.id.usercardxml);


        }
    }

}

```
## MainActivity.java changers

remove unnessary code from this... 
```
//          6. change the impletmentation method (auto access with changing adapter paramter)
public class MainActivity extends AppCompatActivity implements UserAdapter.ItemClickListener {

    private String BASE_URL = "https://reqres.in/api/users";

    RecyclerView userRecyclerview;
    UserAdapter userAdapter;
    

    private void setupRecyclerView(JSONArray propertyArray) {

        userRecyclerview.setLayoutManager(new LinearLayoutManager(this));
  
  //          5. changing parameters according to the last value "this" -> indicate onclicklistnter
        userAdapter = new UserAdapter(propertyArray, this, this);
        userRecyclerview.setAdapter(userAdapter);
    }
    
    
//          7. added new onItemClick method. as new implementation
    @Override
    public void onItemClick(int position, JSONObject product) {
        Log.i("1234", "Clicked item: "+position+ "value "+product);

    }
    

}
```

## 3. Get Data from json file

get data from json file as input 
```
try {
            InputStream inputStream = getAssets().open("editDeviceType.json");
            String data = convertStreamToString(inputStream);
            JSONArray jsonArray = new JSONArray(data);
            int count = jsonArray.length();
            editDeviceTypeList = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                EditDeviceType editDeviceType = new EditDeviceType();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                editDeviceType.setId(jsonObject.getInt("id"));
                editDeviceType.setDeviceTypeCode(jsonObject.getString("deviceTypeCode"));
                editDeviceType.setDeviceTypeName(jsonObject.getString("deviceTypeName"));
                editDeviceType.setDeviceTypeImage(jsonObject.getString("image"));
                editDeviceTypeList.add(editDeviceType);
            }
            editDeviceTypeAdapter = new EditDeviceTypeAdapter(this, this, editDeviceTypeList);
            recyclerView.setAdapter(editDeviceTypeAdapter);
     }catch(Exception e){
      e.printStackTrace();
        }
```
covert stream use to convert json string 
```
public static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
```

## 2. Get request API:

### Response handle jsonobjects
```
private void viewValues(Response response) {

        try{
            JSONObject jsonObject = new JSONObject(response.getResponseBody());
            Log.i("1234", "Json object "+jsonObject); // Json object {"page":1,"per_page":6,"total":12,"total_pages":2,"data":[{"i ...
            JSONArray propertyArray = jsonObject.getJSONArray("data");
            Log.i("1234", "object array "+propertyArray); // object array [{"id":1,"email":"george.bluth@reqres.in","first_name":"Georg ...
            JSONObject single_person = propertyArray.getJSONObject(0);
            Log.i("1234", "Single person "+single_person); // Single person {"id":1,"email":"george.bluth@reqres.in","first_name":"Georg
            String email = single_person.getString("email");
            Log.i("1234", "email "+email); // email george.bluth@reqres.in
            String imageString = single_person.getString("avatar");
            Log.i("1234", "image string "+imageString); // image string https://reqres.in/img/faces/1-image.jpg

            Glide.with(this).load(imageString).into(imageview); //view image using glide and update image view
            
        }catch (JSONException jsonException){
            Log.i("1234", "JSON expression "+jsonException);

        }
```
another way to show string value... it is more usefull in userAdapter
```
String username = propertyArray.getJSONObject(position).getString("first_name");
```
glide dependencies
```
//glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
```

### somecomplex get request handle

```
private void getProfile() {
        try {
            Request request = new Request(Endpoints.GET_PROFILE, Request.Method.GET);
            RestClient client = new RestClient(this, request, new ResponseCallback() {
                @Override
                public void onResponseReceive(Response response) {
                    onGetProfileComplete(response);
                }
            });

            client.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
### Response handle
```
private void onGetProfileComplete(Response response) {
        app.getSettings().setProfileData(response.getResponseBody()); // Call Application activity to save fetch data
        JSONObject jsonProfileDataObject = null;
        try {
                jsonProfileDataObject = new JSONObject(new Settings(this).getProfileData());
                app.setMe(JSONParser.parseUser(jsonProfileDataObject));  // Pass objects to app Application activity
                JSONObject object = new JSONObject(response.getResponseBody());
                JSONObject objectData = object.getJSONObject("data");
                JSONObject objectUser = objectData.getJSONObject("user");
                propertyListArray = objectUser.getJSONArray("propertyList");
                
        } catch (JSONException e) {
                e.printStackTrace();
        } catch (Exception e) {
                e.printStackTrace();
        }

}
```


## 1. Get and Post URL:
### Add permission for android manifest
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
### Post request API
```
private String BASE_URL = "https://reqres.in/api/users";

public  void getRestAPI(){
        try{
            Request request = new Request(BASE_URL, Request.Method.POST);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "morpheus");
            jsonObject.put("job", "leader");
//            jsonObject.put("password", "");
            request.setJSONObject(jsonObject);
            RestClient client = new RestClient(this, request, new ResponseCallback() {
                @Override
                public void onResponseReceive(Response response) {
//                    Log.i("1234","response "+response.getResponseBody());
                    viewValues(response);

                }
            });
            client.execute();

        }catch (Exception e){
            Log.i("1234", "catch "+e);
            e.printStackTrace();
        }

    }
```
### Post respond Handle
```
private void viewValues(Response response) {


        try{
            JSONObject jsonObject = new JSONObject(response.getResponseBody());
            Log.i("1234", "Json object "+jsonObject);
//            JSONObject objectdata = jsonObject.getJSONObject("name");
            String name = jsonObject.getString("name");
            Log.i("1234", "name "+name);

        }catch (JSONException jsonException){
            Log.i("1234", "JSON expression "+jsonException);

        }

    }
```
## * Add Dependencies Arimac client
```
dependencies {
//Arimac dpendencies after added restclientlib-v4.2-release
    //library functions
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation project(path: ':restclientlib-v4.2-release')
    
    //basic android functions
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.2.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'

    //ok http and other functions related to network
    implementation 'com.squareup.okhttp3:okhttp:4.2.0'
    implementation 'com.squareup.okio:okio:1.14.1'

}

```

