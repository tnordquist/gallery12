package edu.cnm.deepdive.gallery12.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.gallery12.BuildConfig;
import edu.cnm.deepdive.gallery12.R;
import edu.cnm.deepdive.gallery12.databinding.FragmentImageDialogBinding;
import edu.cnm.deepdive.gallery12.model.Image;
import org.jetbrains.annotations.NotNull;


public class ImageDialogFragment extends DialogFragment {

  private Image image;

  public static ImageDialogFragment newInstance(Image image) {
    ImageDialogFragment fragment = new ImageDialogFragment();
    Bundle args = new Bundle();
    args.putSerializable("image", image);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(getArguments() != null) {
      image = (Image) getArguments().getSerializable("image");
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(
      @Nullable Bundle savedInstanceState) {

    FragmentImageDialogBinding binding = FragmentImageDialogBinding.inflate(LayoutInflater.from(getContext()));

    if(image.getHref() != null) {
      Picasso.get().load(String.format(BuildConfig.CONTENT_FORMAT, image.getHref()))
          .into(binding.imageDetail);
    }
    binding.imageDescription.setText((image.getDescription()) != null ? image.getDescription() : "N/A");
    binding.imageId.setText((image.getId() != null) ? "Id: " +  image.getId() : "N/A");
    binding.imageType.setText((image.getContentType() != null) ? "Image type: " + image.getContentType() : "N/A");
    binding.imageUrl.setText((image.getHref() != null) ? "Url: " + image.getHref() : "N/A");
    binding.imageDatetime.setText((image.getCreated() != null) ? "Created date: " + image.getCreated() : "N/A");

    return new AlertDialog.Builder(
        getContext())
        .setTitle((image.getTitle() != null) ? image.getTitle() : "Untitled")
        .setView(binding.getRoot())
        .setPositiveButton("close", (dlg, which) -> {
        })
        .create();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return null;
  }
}