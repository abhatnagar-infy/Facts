package fetcher;

/**
 * Can be asked whether we are connected or not
 */
public interface ConnectivityChecker {
    boolean isConnected();
}