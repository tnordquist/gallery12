package edu.cnm.deepdive.gallery12.controller;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.cnm.deepdive.gallery12.R;
import edu.cnm.deepdive.gallery12.databinding.FragmentImageBinding;
import edu.cnm.deepdive.gallery12.viewmodel.ImageViewModel;
import java.util.UUID;

public class ImageFragment extends Fragment {

  private FragmentImageBinding binding;
  private ImageViewModel viewModel;
  private UUID galleryId;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentImageBinding.inflate(inflater);
    return binding.getRoot();
  }


}