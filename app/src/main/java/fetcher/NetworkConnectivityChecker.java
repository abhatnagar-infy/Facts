package fetcher;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Checks if network connection is available
 */
public class NetworkConnectivityChecker implements ConnectivityChecker {
    private final ConnectivityManager mConMgr;

    public NetworkConnectivityChecker(ConnectivityManager conMgr) {
        mConMgr = conMgr;
    }

    @Override
    public boolean isConnected() {
        boolean response = false;
        NetworkInfo activeNetwork = mConMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            response = true;
        }
        return response;
    }
}
