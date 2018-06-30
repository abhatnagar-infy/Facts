package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import androidproficiency.com.facts.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import model.Item;
import presenter.RecyclerItemClickListener;

/**
 * Adapter class that binds data to the recycler view
 */
public class AdapterExample extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int EMPTY = 2;

    private Context context;
    private List<Item> itemList;
    private int itemLayout;
    private boolean isLoadingAdded = false;
    private RecyclerItemClickListener recyclerItemClickListener;

    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public AdapterExample() {
    }

    public AdapterExample(Context context, List<Item> itemList, int itemLayout) {
        this.context = context;
        this.itemList = itemList;
        this.itemLayout = itemLayout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case EMPTY:
                viewHolder = getEmptyViewHolder(parent, inflater);
                break;
            case ITEM:
                viewHolder = getItemViewHolder(parent, inflater);
                break;
            case LOADING:
                viewHolder = getProgressViewHolder(parent, inflater);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getEmptyViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.empty_row, parent, false);
        return new EmptyVH(view);
    }

    @NonNull
    private RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(itemLayout, parent, false);
        return new ExampleHolder(view);
    }

    @NonNull
    private RecyclerView.ViewHolder getProgressViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.item_progress, parent, false);
        return new LoadingVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = itemList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final ExampleHolder exampleHolder = (ExampleHolder) holder;
                if (!itemList.get(position).isItemNull()) {
                    if (null != item.getTitle()) {
                        exampleHolder.title.setText(item.getTitle());
                        exampleHolder.title.setVisibility(View.VISIBLE);
                    } else {
                        exampleHolder.title.setVisibility(View.GONE);
                    }

                    if (null != item.getDescription()) {
                        exampleHolder.desc.setText(item.getDescription());
                        exampleHolder.desc.setVisibility(View.VISIBLE);
                    } else {
                        exampleHolder.desc.setVisibility(View.GONE);
                    }

                    if (null != item.getImagePath()) {
                        Glide
                                .with(context)
                                .load(item.getImagePath())
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        return false;   // return false if you want Glide to handle everything else.
                                    }
                                })
                                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                                .centerCrop()
                                .crossFade()
                                .error(R.drawable.placeholder)
                                .into(exampleHolder.imageView);
                        exampleHolder.imageView.setVisibility(View.VISIBLE);
                    } else {
                        exampleHolder.imageView.setVisibility(View.GONE);
                    }
                }
                break;
            case LOADING:
            case EMPTY:
                // Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!isLoadingAdded && itemList.get(position).isItemNull())
            return EMPTY;

        return (position == itemList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    private void add(Item r) {
        itemList.add(r);
        notifyItemInserted(itemList.size() - 1);
    }

    public void addAll(List<Item> moveResults) {
        for (Item result : moveResults) {
            add(result);
        }
    }

    private void remove(Item r) {
        int position = itemList.indexOf(r);
        if (position > -1) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Item());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = itemList.size() - 1;
        Item item = getItem(position);

        if (item != null) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Item getItem(int position) {
        return itemList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */

    protected class ExampleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.txt_title)
        TextView title;
        @Bind(R.id.txt_desc)
        TextView desc;
        @Bind(R.id.imageView)
        ImageView imageView;

        ExampleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (recyclerItemClickListener != null)
                recyclerItemClickListener.onItemClickListener(getAdapterPosition());
        }
    }

    /**
     * Loading layout ViewHolder
     */

    protected class LoadingVH extends RecyclerView.ViewHolder {
        LoadingVH(View itemView) {
            super(itemView);
        }
    }

    protected class EmptyVH extends RecyclerView.ViewHolder {
        EmptyVH(View itemView) {
            super(itemView);
        }
    }
}