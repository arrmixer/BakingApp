# BakingApp
This is the second project from the Android Nano Degree Program at Udacity. Some of the specs of the app are as follows:
- Use MediaPlayer/Exoplayer to display videos.
- Handle error cases in Android.
- Add a widget to your app experience.
- Leverage a third-party library in your app.
- Use Fragments to create a responsive design that works on phones and tablets.

Here is a snippet from the Udacity Webpage: 
"Your task is to create a Android Baking App that will allow Udacity’s resident baker-in-chief, 
Miriam, to share her recipes with the world. You will create an app that will allow a user to select
a recipe and see video-guided steps for how to complete it."

I decided to follow the same convention I used for my popular movies app. Used MVVM and fragments attached to the same container unless I was using the master-detail view which then I used the fragment container and detail container like in the Big Nerd Ranch book. Specifically, I used the master-detail look for the recipe page and the corresponding recipe step pages. 

I had all my activities extend a single activity (as in the book) to add modularity to my fragments. As I understand it, 
I've created a Single activity with multiple fragment senario (I think?). Please feel free to correct me if I'm wrong. 

I addition, I used Retrofit and GSON this time since I needed to use a third-party library instead of a built in Network Util to do my JSON parsing and network requests.

I kept Room for my DB needs, ViewModel, and LiveData for my lifecycle requirements of course with some SaveInstanceState sprinkled in... nothing fancy just basic implementation..... 

Exoplayer was used to display the videos if were available to play the "to-do" videos. If not, a toast message appears to alert the user.

This app has its own Widget which shows the Recipe title and the list of ingredients for the last recipe the user was using. When the user clicks on any item in the list it takes them straight to the recipe page. 

I finally got to write some Espresso Tests. I wrote tests on UI for recycler view and intents nothing fancy just isDisplayed(), isKey(), and isText().

Please forgive my UI. Definitely a weak point of mine and needs to be address! 

Here are the links to everything I used to make the project (besides Udacity course work of course): 

Links for App research:

Expresso Recorder:
https://developer.android.com/studio/test/espresso-test-recorder


create Widgets:
https://medium.com/@puruchauhan/android-widget-for-starters-5db14f23009b

converter using GSON:
https://medium.com/@amit.bhandari/storing-java-objects-other-than-primitive-types-in-room-database-11e45f4f6d22

Intent Service to JobService
https://android.jlelse.eu/keep-those-background-services-working-when-targeting-android-oreo-sdk-26-cbf6cc2bdb7f

https://blog.klinkerapps.com/android-o-background-services/

https://developer.android.com/reference/android/support/v4/app/JobIntentService

Espresso: 
https://stackoverflow.com/questions/39688297/how-to-check-if-a-view-is-visible-on-a-specific-recyclerview-item

https://github.com/udacity/AdvancedAndroid_TeaTime

Enjoy. 

Angel
