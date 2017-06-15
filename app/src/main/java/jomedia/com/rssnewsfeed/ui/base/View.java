package jomedia.com.rssnewsfeed.ui.base;

public interface View<P extends Presenter> {

    void bindPresenter(P presenter);
    P getPresenter();
}
