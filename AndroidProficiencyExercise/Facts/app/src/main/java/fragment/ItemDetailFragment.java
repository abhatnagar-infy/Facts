package fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import androidproficiency.com.facts.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import model.Item;

/**
 * Created by Anubha on 12/04/18.
 */

public class ItemDetailFragment extends android.support.v4.app.DialogFragment {
    private static final String SELECTED_ITEM = "selected_item";
    private Item item;
    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.title)
    public TextView title;
    @Bind(R.id.desc)
    public TextView description;

    public static ItemDetailFragment newInstance(Item item) {
        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SELECTED_ITEM, item);
        itemDetailFragment.setArguments(mBundle);
        return itemDetailFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog mDialog = super.onCreateDialog(savedInstanceState);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return mDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.item_detail_fragment, container, false);
        ButterKnife.bind(this, mView);

        if (null != getArguments()) {
            item = (Item) getArguments().getSerializable(SELECTED_ITEM);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setImage();
        setTitle();
        setDescription();
    }

    private void setImage() {
        if( null != item.getImagePath()) {
            Glide
                    .with(getActivity())
                    .load(item.getImagePath())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                                   boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                       boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                    .centerCrop()
                    .crossFade()
                    .error(R.drawable.placeholder)
                    .into(image);
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.GONE);
        }
    }

    private void setDescription() {
        if(null != item.getTitle()) {
            description.setText(item.getDescription());
            description.setVisibility(View.VISIBLE);
        } else {
            description.setVisibility(View.GONE);
        }
    }

    private void setTitle() {
        if(null != item.getTitle()) {
            title.setText(item.getTitle());
            title.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
        }
    }
}