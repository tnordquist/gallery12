package edu.cnm.deepdive.gallery12.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.gallery12.BuildConfig;
import edu.cnm.deepdive.gallery12.model.Gallery;
import edu.cnm.deepdive.gallery12.model.Image;
import edu.cnm.deepdive.gallery12.model.User;
import io.reactivex.Single;
import java.util.List;
import java.util.UUID;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface GalleryServiceProxy {

  @GET("users/me")
  Single<User> getProfile(@Header("Authorization") String bearerToken);

  @Multipart
  @POST("galleries/{id}/images")
  Single<Image> post(@Path("id") UUID id, @Header("Authorization") String bearerToken,
      @Part MultipartBody.Part file, @Part("title") RequestBody title);

  @Multipart
  @POST("galleries/{id}/images")
    Single<Image> post(@Path("id") UUID id, @Header("Authorization") String bearerToken,
      @Part MultipartBody.Part file, @Part("title") RequestBody title,
      @Part("description") RequestBody description);

  @GET("images")
  Single<List<Image>> getAllImages(@Header("Authorization") String bearerToken);

  @GET("galleries/{id}")
  Single<Gallery> getGallery(@Path("id") UUID id, @Header("Authorization") String bearToken);

  @GET("galleries")
  Single<List<Gallery>> getGalleries(@Header("Authorization") String bearerToken);

  static GalleryServiceProxy getInstance() {
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

    private static final GalleryServiceProxy INSTANCE;

    static {
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .client(client)
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(GalleryServiceProxy.class);
    }

  }

}
