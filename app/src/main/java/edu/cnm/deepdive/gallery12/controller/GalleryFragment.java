package edu.cnm.deepdive.gallery12.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import edu.cnm.deepdive.gallery12.NavGraphDirections;
import edu.cnm.deepdive.gallery12.NavGraphDirections.OpenUploadProperties;
import edu.cnm.deepdive.gallery12.R;
import edu.cnm.deepdive.gallery12.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

  private static final int PICK_IMAGE_REQUEST = 1023;

  private FragmentGalleryBinding binding;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentGalleryBinding.inflate(inflater, container, false);
    Context context = getContext();
    int span = (int) Math.floor(context.getResources().getDisplayMetrics().widthPixels
        / context.getResources().getDimension(R.dimen.gallery_item_width));
    binding.galleryView.setLayoutManager(new GridLayoutManager(context, span));
    binding.pickImage.setOnClickListener((v) -> pickImage());
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
      OpenUploadProperties action = NavGraphDirections.openUploadProperties(data.getData());
      Navigation.findNavController(binding.getRoot()).navigate(action);
    }
  }

  private void pickImage() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Choose image to upload"), PICK_IMAGE_REQUEST);
  }

}