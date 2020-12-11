package com.example.MeguaPlantsAdmin.herramientas;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Manejador_Bitmap  {
    private static final long MEGA_1 =1000000 ;

    public Manejador_Bitmap() {

    }

    public Bitmap crear_desde_ruta_absoluta(String ruta){
        Bitmap bitmap= BitmapFactory.decodeFile(ruta);

        return rotar_bitmap(90,bitmap);
    }



    private Bitmap rotar_bitmap(int grados,Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.postRotate(grados);

        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }


    public Bitmap  rotar_foto (  Uri uri ,Activity activity){
        ExifInterface exif=null;
        Bitmap bitmap=null;
        try {
            // exif = new ExifInterface(uri.getEncodedPath());
            exif = new ExifInterface(uri.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int rotationInDegrees = exifToDegrees(rotation);
        try {
           bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  rotar_bitmap(rotationInDegrees,bitmap);


    }



    public Bitmap  rotar_imagen (  Uri uri ,Activity activity){
        ExifInterface exif=null;
        Bitmap bitmap=null;
        try {
            // exif = new ExifInterface(uri.getEncodedPath());
             exif = new ExifInterface((getRealPathFromURI(uri,activity)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int rotationInDegrees = exifToDegrees(rotation);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  rotar_bitmap(rotationInDegrees,bitmap);


    }

    private int exifToDegrees(int rotation) {
        if (rotation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (rotation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (rotation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }

    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface( imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt( ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270: rotate = 270;
                break;
                case ExifInterface.ORIENTATION_ROTATE_180: rotate = 180;
                break;
                case ExifInterface.ORIENTATION_ROTATE_90: rotate = 90;
                break;
            }
            Log.v("hola", "Exif orientation: " + orientation);
        }
        catch (Exception e)
        { e.printStackTrace();
        }
        return rotate;
    }
    private String getRealPathFromURI(Uri contentURI,Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) {

        }
        else { cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);

    }
        return null;

    }





    public byte[] de_bitmap_a_byte_array_comprees(Bitmap bitmap){
    ByteArrayOutputStream baos= new ByteArrayOutputStream();
    long peso= bitmap.getByteCount();
    if(peso>MEGA_1*10){
        bitmap.compress(Bitmap.CompressFormat.JPEG,60,baos);
    }
    if(peso<MEGA_1*10 && peso>MEGA_1*6){
        bitmap.compress(Bitmap.CompressFormat.JPEG,66,baos);
    }
    if(peso<MEGA_1*6 && peso>MEGA_1*3){
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
    }
    if(peso<MEGA_1*3 && peso>MEGA_1*1){
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
    }
     if(peso<MEGA_1){
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
     }


    byte[] foto_byte= baos.toByteArray();
    return  foto_byte;

    }
}

