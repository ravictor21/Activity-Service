package com.example.activityservice;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;

public class WallpaperchangeService extends Service implements Runnable {
    /*referensi ganbar wallpaper disimpan dalam sebuah array, wallpaper1 dan
    Wallpaper2 adalah nama file png atau jpeg anda */

    private int wallpaperId[] = {R.drawable.wallpaper1, R.drawable.wallpaper2};
    /*Deklarasi variabel yg digunakan utk menyimpan durasi yg dipzlih user */
    private int waktu;
    /*Deklarasi variabel flag utk nge check gambar mana yang akan di tampilkan berikutnya */

    private int FLAG = 0;
    private Thread t;
    /*Deklarasi 2 buah variabel bitmat utk menyimpan gambar wallpaper */
    private Bitmap gambar;
    private Bitmap gambar1;

    @Override
    public IBinder onBind(Intent argo) {
        //ToD0 Auto-generatoed method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        super.onStartCommand(intent, flag, startId);
        /* peroleh bundle yang dikirim mealua intent dari MainActivity */
        Bundle bundle = intent.getExtras();
        /*baca nilai yg terimpan dengan kunci 'durasi' */
        waktu = bundle.getInt("durasi");
        /*Inisialisasi obyek Bitmap dgn gambar wallpaper */
        gambar = BitmapFactory.decodeResource(getResources(), wallpaperId[0]);
        gambar1 = BitmapFactory.decodeResource(getResources(), wallpaperId[1]);
        /*Memulai sebuah thtead agar sevice tetap berjalan di latar belakang. */

        t = new Thread(WallpaperchangeService.this);
        t.start();
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    /*Method run() yang berisi kode yg dieksekusi oleh thread yg baru saja dibuat */
    @Override
    public void run() {
        WallpaperManager myWallpaper;
        myWallpaper = WallpaperManager.getInstance(this);
        try {
            while (true) {
                if (FLAG == 0) {
                    myWallpaper.setBitmap(gambar);
                    FLAG++;
                } else {
                    myWallpaper.setBitmap(gambar1);
                    FLAG--;
                }
                Thread.sleep(100 * waktu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}