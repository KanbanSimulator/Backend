package inno.kanban.KanbanSimulator.utils;

public class ResponseWrapper<T> {

    private T payload;

    private String error;

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ResponseWrapper(T payload) {
        this.payload = payload;
    }

    public ResponseWrapper(String error) {
        this.error = error;
    }

    public static <T> ResponseWrapper<T> from(T payload) {
        return new ResponseWrapper(payload);
    }

    public static <T> ResponseWrapper<T> error(String error) {
        return new ResponseWrapper(error);
    }
}
