[![develop](https://circleci.com/gh/gokhanaliccii/InfiniteImageListingApp/tree/develop.svg?style=svg)](https://circleci.com/gh/gokhanaliccii/InfiniteImageListingApp/tree/develop)

# InfiniteImageListingApp

Sample image listing app which uses unsplash api. When we scroll down to list it automaticall load new images from service during to load time application loading bar visible at the bottom of screen. Application architecture is **CLEAN MVP** and **not used any third party library**. Due to third party limitation and future of application I decided to make internal libraries which makes network requests and json serialization easier and generic.

Loading | Loaded | Load More | Failed|
--- | --- | --- | --- |
*![images loading](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/data_loading.png)*|*![images loaded](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/loaded.png)*|*![image load more](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/load_more.png)*|![image load failed](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/data_load_failed.png)



## Development Plan
 1. Creating continuous integration pipeline
 
     >I chosed "circle-ci" and created pipeline. I applied branch merge restriction on **develop branch** it won't accept merges until circle-ci result. In that pipeline it run unit tests and lint check
       
 2. Creating json parser library
 
    >I wanted to create json parser library because It will make json deserialization easy. My purpose was give json string with intented model to library and it will return that model which is filled by given json properties. I used reflection to fill model's field. To be sure functionality working expectedly and documenting I wrote unit tests.
    
 3. Creating http library
  
     >I wanted to create http library because I will use it heavily during the project and it will prevent code duplication and time wasting in future. To make using this library easier I added annotation functionality so developer only need to define service endpoint and requeired properties :) To be sure functionality working expectedly and documenting I wrote unit and integration tests.

 4. Implementing Application Logic
    >I implemented infinite scroll logic using clean MVP architecture and I used Kotlin and Java. Additionaly I created in memory cache for enhance image displaying performance and to listen application lifecycle I created 'LifeCycleBag', basically we can listen lifecycle events even when we don't have activity or fragment reference :)


## CI Overview

Image | Description |
--- | --- |
*![images loaded](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/pull_request.png)*|When I create pr it trigger circle-ci job
*![images loaded](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/pull_request_ci_result.png)*|Circle-ci job result at pr page
*![images loaded](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/pr_after_fixed.png)*|After failed test fixed pr page
*![images loaded](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/ci_report_detail.png)*|Circle-ci dashboard

## Json Parser Library

Dependency
```gradle
implementation project(':jsonparser')
```
Usage for object mapping
```kotlin
val input = "{\"name\":\"gokhan\",\"age\":1}"
val person = input.jsonTo(NamedPerson::class.java)

class NamedPerson {
  lateinit var name: String
}
```
Usage for array mapping
```kotlin
val input = "[{\"name\":\"gokhan\",\"age\":1}]"
val student = input.jsonToList(NamedPerson::class.java)

class NamedPerson {
  lateinit var name: String
}
```

I create two extension function **jsonTo** and **jsontoList** on String class. I used reflection for json mapping. **Problematic** part of this library is reference has to has default constructor. [Json Parser Examples](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/jsonparser/src/test/java/com/gokhanaliccii/jsonparser/JsonParserTest.kt)

## Http Client Library
Dependency
```gradle
implementation project(':httpclient')
```
Usage

```kotlin
   interface UnSplashImageService {
        @GET("/photos")
        @TYPE(value = Photo.class, isArray = true)
        Request<Photo> getImages(@Query("client_id") String clientId);
    }
    
     EasyHttpClient httpClient = EasyHttpClient.with(new JsonRequestQueue(), BASE_URL);
        UnSplashImageService imageService = httpClient.create(UnSplashImageService.class);

        imageService.getImages(CLIENT_ID).enqueue(new HttpRequestQueue.HttpResult<Photo>() {
            @Override
            public void onRequestFailed(@NotNull Exception exception) {
            }

            @Override
            public void onResponse(Photo response) {
            }

            @Override
            public void onResponse(@NotNull List<? extends Photo> response) {
            }
        }, false, false);
``` 
[EasyHttpClient Example1](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/httpclient/src/test/java/com/gokhanaliccii/httpclient/HttpClientTest.java)        [EasyHttpClient Example2](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/httpclient/src/androidTest/java/com/gokhanaliccii/httpclient/HttpClientTest.java) 

