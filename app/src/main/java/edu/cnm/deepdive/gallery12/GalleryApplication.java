package edu.cnm.deepdive.gallery12;

import android.app.Application;
import edu.cnm.deepdive.gallery12.service.GoogleSignInService;

public class GalleryApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);
  }
}
