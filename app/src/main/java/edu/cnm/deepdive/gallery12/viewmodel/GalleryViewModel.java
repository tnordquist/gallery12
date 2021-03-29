package edu.cnm.deepdive.gallery12.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.gallery12.model.Gallery;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class GalleryViewModel extends AndroidViewModel {

  private final MutableLiveData<Gallery> gallery;
  private final MutableLiveData<List<Gallery>> galleries;
  private final MutableLiveData<Throwable> throwable;

  public GalleryViewModel(
      @NonNull @NotNull Application application) {
    super(application);
    galleries = new MutableLiveData<>();
    gallery = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
  }

  public LiveData<Gallery> getGallery() {
    return gallery;
  }

  public LiveData<List<Gallery>> getGalleries() {
    return galleries;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }
}
