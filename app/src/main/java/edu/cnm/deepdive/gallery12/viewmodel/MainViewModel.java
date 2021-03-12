package edu.cnm.deepdive.gallery12.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.gallery12.model.User;
import edu.cnm.deepdive.gallery12.service.UserRepository;
import org.jetbrains.annotations.NotNull;

public class MainViewModel extends AndroidViewModel {

  private final UserRepository userRepository;
//  private final MutableLiveData<GoogleSignInAccount> account;
//  private final MutableLiveData<User> user;
//  private final MutableLiveData<Throwable> throwable;

  public MainViewModel(
      @NonNull @NotNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
//    account = new MutableLiveData<>(userRepository.getAccount);
  }
}
