[![develop](https://circleci.com/gh/gokhanaliccii/InfiniteImageListingApp/tree/develop.svg?style=svg)](https://circleci.com/gh/gokhanaliccii/InfiniteImageListingApp/tree/develop)

# InfiniteImageListingApp

Sample image listing app which uses unsplash api. When we scroll down the list, it automatically loads new images from service during the load time, loading bar displayed at the bottom of screen. Application architecture is **CLEAN MVP** and **not used any third party library**. Due to third party limitations and future of application I decided to make internal libraries which makes network requests and json serialization easier and generic.

Loading | Loaded | Load More | Failed|
--- | --- | --- | --- |
*![images loading](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/data_loading.png)*|*![images loaded](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/loaded.png)*|*![image load more](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/load_more.png)*|![image load failed](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/screenshots/data_load_failed.png)



## Development Plan
 1. Creating Continuous Integration Pipeline
 
     >I chosed "circle-ci" and created pipeline. I applied branch merge restriction on **develop branch** it won't accept merges until circle-ci result. In that pipeline it run unit tests and lint check
       
 2. Creating JsonParser Library
 
    >I wanted to create json parser library because it will make json deserialization easy. My purpose was give json string with intented model to library and it will return that model which is filled by given json properties. I used reflection to fill model's field. Unit tests are written to be sure functionality working as expectedly and documenting.
    
 3. Creating Http library
  
     >I wanted to create http library because I will use it heavily during the project and it will prevent code duplications and time wasting in the future. To make using this library easier I added annotation functionality so developer only need to define service endpoint and required properties :) Unit tests are written to be sure functionality working as expectedly and documenting.

 4. Implementing Application Logic
    >I implemented infinite scroll logic using clean MVP architecture and I used Kotlin and Java. Additionaly I created in memory cache for enhance image displaying performance and to listen application lifecycle I created 'LifeCycleBag', basically we can listen lifecycle events even when we don't have activity or fragment reference :)


## CI Overview

Image | Description |
:--- | :--- |
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
val person = input.jsonToList(NamedPerson::class.java)

class NamedPerson {
  lateinit var name: String
}
```

Advanced Usage 
```kotlin
  val input = "{\"name\":\"gokhan\",\"age\":1,\"friends\":[{\"name\":\"ahmet\"}]}"
  val student = input.jsonTo(Student::class.java)

   class Friend {
        lateinit var name: String
    }

    class Student {
        var age: Int = 0
        lateinit var name: String

        @JsonList(Friend::class)
        lateinit var friends: List<Friend>
    }
```

I create two extension function **jsonTo** and **jsontoList** on String class and . I used reflection for json mapping. **Problematic** part of this library is reference class has to has default constructor. And if object contains sub object or array it should annotate that field [Json Parser Examples With Annotations](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/jsonparser/src/test/java/com/gokhanaliccii/jsonparser/JsonParserTest.kt)

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
WasyHttpClient process annotations to make service request. We have to use [TYPE](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/httpclient/src/main/java/com/gokhanaliccii/httpclient/annotation/method/TYPE.kt) annotation for object mapping
[EasyHttpClient Example1](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/httpclient/src/test/java/com/gokhanaliccii/httpclient/HttpClientTest.java)        [EasyHttpClient Example2](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/httpclient/src/androidTest/java/com/gokhanaliccii/httpclient/HttpClientTest.java) 

## Application

#### Package Convention
I used package by feature convention. I created five different packages; common, datasource, dialog, ui, widget. Each package grouped by their feature name usually, **common** package contains util classes and extensions, **datasource** package contains repositories but currently we have only image repository now, **dialog** package contains common dialogs, **ui** package contains fragments and activities by feature separated, **widget** package contains custom views.

#### Architecture
I used clean MVP architecture. At the service layer I used dto objects to map service responses to ui objects. With this approach if the service response or service will change in the future we would just change our dto mapping logic :) I initialized singleton object like image cache, service layer etc at the application class. I added unit and integration test for httpclient and json parser. But I could not write for application because I need **third party** libraries like **mockito** . If I had chance to use third party libraries I would add tests for this layer

#### Infinite Scroll Logic
I figure outed load more via [EndlessRecyclerViewScrollListener](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/app/src/main/java/com/gokhanaliccii/infiniteimagelisting/common/recyclerview/EndlessRecyclerViewScrollListener.kt) and handle pagination logic at [ImageListPresenter](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/app/src/main/java/com/gokhanaliccii/infiniteimagelisting/ui/images/ImageListPresenter.kt).

#### Useful Code Snippents

It checks images from memory cache and if it is not exist load from url

```kotlin
fun ImageView.loadImage(url: String) {
    tag = url
    val bitmapFromCache = imageCache.getBitmapFromMemCache(url)
    if (bitmapFromCache != null) {
        setImageBitmap(bitmapFromCache)
    } else {
        loadAndCacheImages(this, url)
    }
}
```
It listens intented lifecycle event without retain activity/fragment instance

```kotlin
  init {
        InfiniteImageListingApp.fragmentLifeCycleBag.attachToLifeCycle("onFragmentStopped"){
            Log.i("ImageListPresenter", "onFragmentStopped")        
        }
    }
```    

With [Header](https://github.com/gokhanaliccii/InfiniteImageListingApp/blob/develop/httpclient/src/main/java/com/gokhanaliccii/httpclient/annotation/header/Header.kt) annotation we can define header params easily

```kotlin
   @POST("/photos")
   @Header("Authorization:Client-ID XXX")
   Request<Photo> getPhotos();
``` 

___
## Personal Note
First of all I had fun during this application and I spent most of my time (%80) to create internal libraries. I think that libraries are important for application extension and maintainability. I heavily used kotlin for this project but I wish to use it with thirdparty libraries :) 

#### Improvements
* I don't use 'usecase' approach for this project to make lightweight
* Http library's retry and cache implemantations are not completed yet
* Configuration change supported for this app but it's not working like my wants
* Http and json parser needs that object types explicity
* I need to improve utest coverages
* During the project development sometimes I couldn't apply TDD :(
* Loading animation
* Detail Page
* I don't seperate application's dependency initialization at another moduele/package to keep app simple

### Wish To Use 
* Databinding
* Rx | Coroutines
* Gson
* Retrofit
* Koin
* Espresso
* MockK


