package com.example.programmer.mediago2.mp3Helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.programmer.mediago2.entity.AlbumEntity;
import com.example.programmer.mediago2.entity.AritistEntity;
import com.example.programmer.mediago2.entity.MediaEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MusicHelper {
    private int i = 0;

    private Context context;
    private static MusicHelper mInstance;

    private MusicHelper(Context context) {
        this.context = context;

    }

    public static MusicHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (MusicHelper.class) {
                if (mInstance == null) {
                    mInstance = new MusicHelper(context);
                }
            }
        }
        return mInstance;
    }

    public List getAllPlayList() {
        List list = new ArrayList();
        ContentResolver cn = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = cn.query(uri, null, null, null, null);
        cursor.moveToFirst();
        do {

            int pos = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            list.add(cursor.getString(pos));

        } while (cursor.moveToNext());
        return list;
    }

    public List getAllArtistt() {
        List<AritistEntity> list = new LinkedList<>();


        AritistEntity entity;
        ContentResolver cn = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = cn.query(uri, null, null, null, null);
        cursor.moveToLast();
        do {
            entity = new AritistEntity();
            int posId = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);


            entity.setId(cursor.getString(posId));


            int pos_album_aritist = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            entity.setName_abum_ar(cursor.getString(pos_album_aritist));

            if (cursor.getString(posId).equalsIgnoreCase("<unknown>"))
                continue;

            int posName = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
            entity.setName(cursor.getString(posName));


            list.add(entity);

        } while (cursor.moveToPrevious());
        return list;
    }

    public List<String> getArt() {
        List<String> list = new LinkedList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?",
                new String[]{String.valueOf(MediaStore.Audio.Media.ALBUM_ID)},
                null);

        if (cursor.moveToFirst()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            list.add(path);
            Log.d("pattth", list.get(0));
        }
        return list;
    }

    public List getAllAlbums() {
        List list = new ArrayList();
        AlbumEntity entity = new AlbumEntity();
        ContentResolver cn = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = cn.query(uri, null, null, null, null);
        cursor.moveToLast();
        do {

            int posId = cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            entity.setId(cursor.getString(posId));
            int posId2 = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID);
            entity.setAlbume_id(cursor.getString(posId));
            int posArtist = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            entity.setName_aritist(cursor.getString(posId));

            int posName = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            entity.setName(cursor.getString(posName));
            list.add(entity.getName());


        } while (cursor.moveToPrevious());
        return list;
    }


    public List<AlbumEntity> getAllAlbumsTest() {
        List<AlbumEntity> list = new ArrayList();

        ContentResolver cn = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = cn.query(uri, null, null, null, null);
        cursor.moveToLast();
        do {
            AlbumEntity entity = new AlbumEntity();

            int posId = cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            entity.setId(cursor.getString(posId));
            int posId2 = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID);
            entity.setAlbume_id(cursor.getString(posId));
            int posArtist = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            entity.setName_aritist(cursor.getString(posId));

            int posName = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            entity.setName(cursor.getString(posName));
            list.add(entity);


        } while (cursor.moveToPrevious());
        return list;
    }

    public List<MediaEntity> getAllMedia() {
        List<MediaEntity> list = new ArrayList();
        MediaEntity entity;

        ContentResolver cn = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = cn.query(uri, null, null, null, null);
        cursor.moveToLast();
        do {
            entity = new MediaEntity();
            int posId = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            entity.setId(cursor.getString(posId));
            int posId2 = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            entity.setAlbume_id(cursor.getString(posId));
            int posArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            entity.setArtist_id(cursor.getString(posArtist));


            int posName = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            entity.setName(cursor.getString(posName));
            //Log.d("nammm",cursor.getString(posName));

            int posDur = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            String time = cursor.getString(posDur);
            long munite = TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(time));


            entity.setDuration(munite + " minutes");
            list.add(entity);

        } while (cursor.moveToPrevious());

        return list;
    }


}
