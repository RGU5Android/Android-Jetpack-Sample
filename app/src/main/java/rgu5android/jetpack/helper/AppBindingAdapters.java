package rgu5android.jetpack.helper;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import rgu5android.jetpack.R;
import rgu5android.jetpack.injection.module.GlideApp;

public class AppBindingAdapters {

    @BindingAdapter("setImageUrl")
    public static void setImageUrl(final ImageView imageView, final String url) {
        GlideApp.with(imageView.getContext())
            .load(url)
            .placeholder(R.drawable.ic_person)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.IMMEDIATE)
            .into(imageView);
    }
}
