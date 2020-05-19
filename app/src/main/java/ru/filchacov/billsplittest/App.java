package ru.filchacov.billsplittest;

import android.app.Application;

import androidx.room.Room;

import com.facebook.stetho.Stetho;

import ru.filchacov.billsplittest.db.User.UserDB;

public class App extends Application {
    public static App instance;

    private UserDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, UserDB.class, "User.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();


        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }

    public static App getInstance() {
        return instance;
    }

    public UserDB getDatabase() {
        return database;
    }

}
