package edu.cnm.deepdive.gallery12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.gallery12.BuildConfig;
import edu.cnm.deepdive.gallery12.adapter.GalleryAdapter.Holder;
import edu.cnm.deepdive.gallery12.databinding.ItemGalleryBinding;
import edu.cnm.deepdive.gallery12.model.Image;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<Image> images;
  private final LayoutInflater inflater;

  public GalleryAdapter(Context context) {
    this.context = context;
    images = new ArrayList<>();
    inflater = LayoutInflater.from(context);
  }

  public List<Image> getImages() {
    return images;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemGalleryBinding binding = ItemGalleryBinding.inflate(inflater, parent, false);
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(images.get(position));
  }

  @Override
  public int getItemCount() {
    return images.size();
  }

  static class Holder extends RecyclerView.ViewHolder {

    private final ItemGalleryBinding binding;

    public Holder(ItemGalleryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(Image image) {
      Picasso.get()
          .load(String.format(BuildConfig.CONTENT_FORMAT, image.getHref()))
          .into(binding.image);
      String title = image.getTitle();
      String filename = image.getName();
      String description = image.getDescription();
      binding.title.setText((title != null) ? title : filename);
      if (description != null) {
        binding.image.setContentDescription(description);
        binding.image.setTooltipText(description);
      }
        binding.description.setText(description);

    }
  }
}
