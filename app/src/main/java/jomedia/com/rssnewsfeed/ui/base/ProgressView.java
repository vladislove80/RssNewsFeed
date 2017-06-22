package jomedia.com.rssnewsfeed.ui.base;

public interface ProgressView {

    void showProgress();
    void hideProgress();
    void showNoDataMessage(String message);
    void showNewsStatus(String status);
    void showError(String errorMessage);
}
