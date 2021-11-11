package mdane.myapplication;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;
import android.app.Application;
import com.parse.Parse;
import com.parse.Parse.Configuration.Builder;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;


public class traintrackapp extends Application {

    @Override
    public void onCreate() {
            super.onCreate();
            Parse.enableLocalDatastore(this);
            Parse.initialize(new Builder(getApplicationContext()).applicationId("backend").clientKey("abcde").server("https://young-inlet-55443.herokuapp.com/parse/").build());
            ParseACL.setDefaultACL(new ParseACL(), true);




        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}