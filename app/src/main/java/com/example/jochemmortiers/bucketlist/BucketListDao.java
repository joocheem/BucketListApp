package com.example.jochemmortiers.bucketlist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BucketListDao {
    @Query("SELECT * FROM bucketlist")
    LiveData<List<BucketListItem>> getBucketListItems();

    @Insert
    void addBucketListItem(BucketListItem bucketListItem);

    @Delete
    void deleteBucketListItem(BucketListItem bucketListItem);

    @Update
    void updateBucketListItem(BucketListItem bucketListItem);
}
