package edu.cnm.deepdive.gallery12.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.gallery12.R;
import edu.cnm.deepdive.gallery12.adapter.GalleryAdapter;
import edu.cnm.deepdive.gallery12.databinding.FragmentGalleryBinding;
import edu.cnm.deepdive.gallery12.model.Image;
import edu.cnm.deepdive.gallery12.viewmodel.MainViewModel;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class GalleryFragment extends Fragment {

  private static final int PICK_IMAGE_REQUEST = 1023;
  private MainViewModel viewModel;
  private GalleryAdapter adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu,
      @NonNull @NotNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_gallery, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    edu.cnm.deepdive.gallery12.databinding.FragmentGalleryBinding binding = FragmentGalleryBinding
        .inflate(inflater, container, false);
    Context context = getContext();
    int span = (int) Math.floor(context.getResources().getDisplayMetrics().widthPixels
        / context.getResources().getDimension(R.dimen.gallery_item_width));
    adapter = new GalleryAdapter(context);
    binding.galleryView.setAdapter(adapter);
    binding.addImage.setOnClickListener((v) -> pickImage());
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view,
      @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getImage().observe(getViewLifecycleOwner(), this::updateGallery);
    viewModel.getImages().observe(getViewLifecycleOwner(), this::updateGallery);
  }

  private void pickImage() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Choose image to upload"),
        PICK_IMAGE_REQUEST);
  }

  private void updateGallery(Image image) {
    List<Image> images = adapter.getImages();
    if (image != null && !images.contains(image)) {
      images.add(0, image);
      adapter.notifyItemInserted(0);
    }
  }

  private void updateGallery(List<Image> images) {
    adapter.getImages().clear();
    adapter.getImages().addAll(images);
    adapter.notifyDataSetChanged();
  }
}