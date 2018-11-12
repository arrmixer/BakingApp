# BakingApp

## Description
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

## Implementation
For this project, I used Retrofit, GSON, and Exoplayer as my third-party libraries and Android architecture components Room, ViewModel, and LiveData.

Exoplayer was used to display the videos. If a video was not available,  then a toast message appears to alert the user.

The app includes a widget that shows the Recipe title and the list of ingredients for the last recipe the user was using. When the user clicks on any item in the list, it takes them straight to that recipe page.

I finally got to write some Espresso Tests. I wrote tests on UI for recycler view and intents nothing fancy just isDisplayed(), isKey(), and isText().

Please forgive my UI!

## Credits:
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
