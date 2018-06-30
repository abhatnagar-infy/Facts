package module;

import android.app.Application;
import android.content.res.Resources;

public class ApplicationModule {
    private static Application sApplication;

    public static Application applicationContext() {
        return sApplication;
    }

    public static void setsApplication(Application sApplication) {
        ApplicationModule.sApplication = sApplication;
    }

    public static Resources getResources() {
        return  sApplication.getResources();
    }
}
