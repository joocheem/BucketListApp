package com.example.jochemmortiers.bucketlist;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.recycler)
    RecyclerView mRecycler;
    BucketListAdapter mBucketListAdapter;

    BucketListViewModel viewModel;

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
            viewModel.delete(mBucketListAdapter.getItem(viewHolder.getAdapterPosition()));

        }
    };

    @AfterViews
    void init() {
        mBucketListAdapter = new BucketListAdapter(this, new BucketListRow.BucketListUpdateListener() {
            @Override
            public void bucketListItemUpdated(BucketListItem item) {
                viewModel.update(item);
            }
        });
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mBucketListAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecycler);

        viewModel = new BucketListViewModel(this);
        viewModel.getAllBucketListItems().observe(this, new Observer<List<BucketListItem>>() {
            @Override
            public void onChanged(@Nullable List<BucketListItem> bucketListItems) {
                mBucketListAdapter.setItems(bucketListItems);
            }
        });
    }

    @Click(R.id.fab_add)
    void addBucketListRow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.add_item));
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.view_dialog_input, (ViewGroup) getWindow().getDecorView(), false);
        final EditText title = viewInflated.findViewById(R.id.title_input);
        final EditText description = viewInflated.findViewById(R.id.description_input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String titleInput = title.getText().toString();
                String descriptionInput = description.getText().toString();

                viewModel.insert(new BucketListItem(titleInput, descriptionInput));
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
