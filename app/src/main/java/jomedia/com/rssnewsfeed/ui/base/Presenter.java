package jomedia.com.rssnewsfeed.ui.base;

public interface Presenter <V extends View> {

    void bindView(V view);
    void unbindView();
    V getView();
}
