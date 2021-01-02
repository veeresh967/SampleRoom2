package xyz.net7.sampleroom.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {DataItem.class}, version = 1, exportSchema = false)
public abstract class DataRoomDbase extends RoomDatabase {

    private static DataRoomDbase INSTANCE;

    public abstract DataDAO dataDAO();

    public static DataRoomDbase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataRoomDbase.class, DataRoomDbase.class.getName())
                    //if you want create db only in memory, not in file
                    //Room.inMemoryDatabaseBuilder(context.getApplicationContext(), DataRoomDbase.class)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
