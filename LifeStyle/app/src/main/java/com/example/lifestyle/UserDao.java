package com.example.lifestyle;


import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserTable userTable);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * from user_table")
    List<UserTable> getAll();

    @Query("SELECT * from user_table where id=(:userId)")
    UserTable getById(String userId);

}
