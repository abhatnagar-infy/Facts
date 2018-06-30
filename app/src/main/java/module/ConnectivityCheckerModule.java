package module;

import android.content.Context;
import android.net.ConnectivityManager;
import fetcher.ConnectivityChecker;
import fetcher.NetworkConnectivityChecker;

/**
 * Checks if the connectivity is available.
 */
public class ConnectivityCheckerModule {

    private ConnectivityCheckerModule() {
    }

    public static ConnectivityChecker networkConnectivityChecker() {
        return new NetworkConnectivityChecker((ConnectivityManager) ApplicationModule.applicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE)
        );
    }
}
