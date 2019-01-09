package com.example.jochemmortiers.bucketlist;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_bucketlist_item)
public class BucketListRow extends FrameLayout {

    BucketListItem item;
    BucketListUpdateListener listener;

    @ViewById(R.id.item_checkbox)
    CheckBox checkBox;

    @ViewById(R.id.item_textview)
    TextView textView;

    public BucketListRow(@NonNull Context context) {
        super(context);
    }

    public BucketListRow(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketListRow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void bind(BucketListItem item, BucketListUpdateListener listener){
        this.item = item;
        this.listener = listener;
        textView.setText(item.getTitle() + "\n" + item.getDescription());
        textView.setPaintFlags(item.isFinished() ? textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        checkBox.setChecked(item.isFinished());
    }

    @CheckedChange(R.id.item_checkbox)
    void checkChanged(CompoundButton button, boolean isChecked) {
        item.setFinished(isChecked);
        listener.bucketListItemUpdated(item);
    }

    public interface BucketListUpdateListener {
        void bucketListItemUpdated(BucketListItem item);
    }
}
