package edu.cnm.deepdive.gallery12.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.gallery12.NavGraphDirections;
import edu.cnm.deepdive.gallery12.NavGraphDirections.OpenUploadProperties;
import edu.cnm.deepdive.gallery12.R;
import edu.cnm.deepdive.gallery12.adapter.GalleryAdapter;
import edu.cnm.deepdive.gallery12.adapter.GalleryAdapter.OnGalleryClickHelper;
import edu.cnm.deepdive.gallery12.databinding.FragmentGalleryBinding;
import edu.cnm.deepdive.gallery12.model.Image;
import edu.cnm.deepdive.gallery12.viewmodel.GalleryViewModel;
import edu.cnm.deepdive.gallery12.viewmodel.ImageViewModel;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class GalleryFragment extends Fragment implements OnGalleryClickHelper {

  private static final int PICK_IMAGE_REQUEST = 1023;
  private ImageViewModel viewModel;
  private GalleryViewModel galleryViewModel;
  private GalleryAdapter adapter;
  private FragmentGalleryBinding binding;
  private ActivityResultLauncher<Intent> launcher;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    launcher = registerForActivityResult(new StartActivityForResult(),
        result -> {
          if (result.getResultCode() == Activity.RESULT_OK) {
            OpenUploadProperties action = NavGraphDirections.openUploadProperties(result.getData().getData());
            Navigation.findNavController(binding.getRoot()).navigate(action);
          }

        });
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu,
      @NonNull @NotNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_gallery, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
    boolean handled = true;
    //noinspection SwitchStatementWithTooFewBranches
    switch (item.getItemId()) {
      case R.id.action_refresh:
        viewModel.loadImages();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentGalleryBinding
        .inflate(inflater, container, false);
    binding.addImage.setOnClickListener((v) -> pickImage());
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view,
      @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    viewModel = new ViewModelProvider(getActivity()).get(ImageViewModel.class);
    galleryViewModel = new ViewModelProvider(getActivity()).get(GalleryViewModel.class);
    galleryViewModel.getGalleries().observe(getViewLifecycleOwner(), (galleries) -> {
      if (galleries != null) {
        binding.galleryView.setAdapter(new GalleryAdapter(getContext(), galleries, this));
      }
    });

  }

  private void pickImage() {
    Intent intent = new Intent();
//    intent.setType("image/*");
    intent.setType("*/*");
    intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "text/*", "application/*"});
    intent.setAction(Intent.ACTION_GET_CONTENT);
    launcher.launch(intent);
    startActivityForResult(Intent.createChooser(intent, "Choose image to upload"),
        PICK_IMAGE_REQUEST);
  }

  private void updateGallery(Image image) {
/*    List<Image> images = adapter.getGalleries();
    if (image != null && !images.contains(image)) {
      images.add(0, image);
      adapter.notifyItemInserted(0);
    }*/
  }

  private void updateGallery(List<Image> images) {
    /*adapter.getGalleries().clear();
    adapter.getGalleries().addAll(images);*/
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onGalleryClick(String galleryId, View view) {
    GalleryFragmentDirections.GalleryFragmentToImageFragment toImageFragment
        = GalleryFragmentDirections.galleryFragmentToImageFragment(galleryId);
    toImageFragment.setGalleryImages(galleryId);
    Navigation.findNavController(view).navigate(toImageFragment);
  }
}