package event;

public class RequestFailureEvent {
    private String failureReason;

    public RequestFailureEvent(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
