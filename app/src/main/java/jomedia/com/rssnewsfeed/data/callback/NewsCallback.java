package jomedia.com.rssnewsfeed.data.callback;

public interface NewsCallback<T> {
    void onEmit(T data);
    void onCompleted();
    void onError(Throwable throwable);
}
