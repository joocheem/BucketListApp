package com.example.jochemmortiers.bucketlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected final List<T> items = new ArrayList<>();
    private RecyclerItemClickListener<T> mRecyclerItemClickListener;


    public RecyclerItemClickListener<T> getRecyclerItemClickListener() {
        return mRecyclerItemClickListener;
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<T> items, boolean notify) {
        this.items.clear();
        this.items.addAll(items);
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void setItems(List<T> items) {
        setItems(items, true);
    }

    public void addItem(T item) {
        items.add(item);
        notifyItemInserted(items.size());
    }

    public void addItem(int index, T item) {
        items.add(index, item);
        notifyItemInserted(index);
    }

    public void removeItem(int pos) {
        items.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, getItemCount() - pos);
    }

    public void removeItem(T pItem) {
        final int index = items.indexOf(pItem);
        if (index != -1) {
            removeItem(index);
        }
    }

    public List<T> getData() {
        return items;
    }

    public T getItem(int position) {
        if (position > -1 && position < items.size()) {
            return items.get(position);
        }
        return null;
    }

    public int indexOf(T pItem) {
        return items.indexOf(pItem);
    }

    public void setItemClickListner(RecyclerItemClickListener<T> clickListener) {
        mRecyclerItemClickListener = clickListener;
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public interface RecyclerItemClickListener<Model> {
        void onItemClick(Model item);
    }
}
