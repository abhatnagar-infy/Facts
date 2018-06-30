package androidproficiency.com.facts.activity;

import android.support.multidex.MultiDexApplication;

import module.ApplicationModule;

public class FactsApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationModule.setsApplication(this);
    }
}
