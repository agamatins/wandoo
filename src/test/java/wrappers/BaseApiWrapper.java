package wrappers;

public abstract class BaseApiWrapper {

    int localPort;

    public BaseApiWrapper() {
    }

    public String getTarget(String path) {
        return String.format("http://localhost:%s/%s", localPort, path);
    }
}
