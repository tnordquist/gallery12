package edu.cnm.deepdive.gallery12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.gallery12.BuildConfig;
import edu.cnm.deepdive.gallery12.adapter.ImageAdapter.Holder;
import edu.cnm.deepdive.gallery12.databinding.ItemImageBinding;
import edu.cnm.deepdive.gallery12.model.Image;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ImageAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<Image> images;
  private final LayoutInflater inflater;
  private final OnImageClickHelper onImageClickHelper;

  public ImageAdapter(Context context,
      List<Image> images,
      OnImageClickHelper onImageClickHelper) {
    this.context = context;
    this.images = images;
    this.onImageClickHelper = onImageClickHelper;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @NotNull
  @Override
  public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    ItemImageBinding binding = ItemImageBinding.inflate(inflater, parent, false);
    return new Holder(binding, onImageClickHelper);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return images.size();
  }

  public class Holder extends RecyclerView.ViewHolder implements OnClickListener {

    private final ItemImageBinding binding;
    private Image image;
    private OnImageClickHelper onImageClickHelper;

    public Holder(ItemImageBinding binding, OnImageClickHelper onImageClickHelper) {
      super(binding.getRoot());
      this.binding = binding;
      this.onImageClickHelper = onImageClickHelper;
      binding.getRoot().setOnClickListener(this);
    }

    private void bind(int position) {
      image = images.get(position);
      if(image.getHref() != null) {
        Picasso.get().load(String.format(BuildConfig.CONTENT_FORMAT, image.getHref()))
            .into(binding.imageGallery);
      }
      binding.imageTitle.setText(image.getTitle());
      binding.imageDescription.setText(image.getDescription());
    }

    @Override
    public void onClick(View view) {
      onImageClickHelper.onImageClick(images.get(getAdapterPosition()), getAdapterPosition());
    }
  }

  public interface OnImageClickHelper {
    void onImageClick(Image image, int position);
  }

}
