package com.dwi.maurea.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dwi.maurea.data.local.entitiy.ProfileEntity

@Dao
interface MaureaDao {
    @Query("SELECT * FROM profile_entities")
    fun getProfile(): LiveData<ProfileEntity>

    @Query("SELECT name FROM profile_entities")
    fun getName(): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profileEntity: ProfileEntity)
}
