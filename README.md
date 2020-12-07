# Arimac Test important things

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

