package com.lubasu.barakamachumu.crdbtrans;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by baraka.machumu on 7/16/2018.
 */

public interface TransactionsDao {
    @Query("SELECT * FROM transactions")
    List<Transactions> getAll();

    @Query("SELECT * FROM product WHERE name LIKE :name LIMIT 1")
    Transactions findByName(String name);

    @Insert
    void insertAll(List<Transactions> transactions);

    @Update
    void update(Transactions transactions);

    @Delete
    void delete(Transactions transactions);
}
