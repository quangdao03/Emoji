package com.example.emojitest.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.RecoverableSecurityException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.emojitest.R;
import com.example.emojitest.util.SystemUtil;


import java.io.File;
import java.util.ArrayList;

public class ImageMyCreationScreenActivity extends Activity {
    ImageView back, btnShare, btnDelete, imageView;
    Uri phototUri = null;
    File file;
    TextView tv_toolbar;
    ArrayList<Uri> uris = new ArrayList<>();
    LinearLayout ll_image;

    private boolean isShareClicked = false;

    public void onCreate(Bundle bundle) {
        SystemUtil.setLocale(this);
        super.onCreate(bundle);
        setContentView(R.layout.activity_share_image_result);

        initViews();
        phototUri = Uri.parse(getIntent().getStringExtra("uri"));
//        imageView.setImageURI(phototUri);
        Glide.with(this).load(phototUri.toString()).format(DecodeFormat.PREFER_ARGB_8888).into(imageView);
    }

    public void initViews() {
        back = findViewById(R.id.back);
        btnShare = findViewById(R.id.btnShare);
        btnDelete = findViewById(R.id.btnDelete);
        imageView = findViewById(R.id.image);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        ll_image = findViewById(R.id.ll_image);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_toolbar.setText(R.string.my_creation);

        btnShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShareClicked) {
                    shareFileImage(phototUri.toString());
                    isShareClicked = true;
                }
            }
        });

        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShareClicked = false;
    }

    public void showDialogDelete() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_cancel = dialog.findViewById(R.id.cancel);
        TextView tv_delete = dialog.findViewById(R.id.delete);
        file = new File(phototUri.getPath());

        tv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                    file.delete();
                    Intent intent = new Intent("load_my_creation");

                    LocalBroadcastManager.getInstance(ImageMyCreationScreenActivity.this).sendBroadcast(intent);
                    finish();
                } else {
                    //Delete file android 11
                    Long mediaID = getFilePathToMediaID(file.getAbsolutePath(), ImageMyCreationScreenActivity.this);
                    Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.getContentUri("external"), mediaID);
                    uris.clear();
                    uris.add(uri);
                    requestDeletePermission(uris);
                }
            }
        });

        dialog.show();
    }

    public static long getFilePathToMediaID(String songPath, Context context) {
        long id = 0;
        ContentResolver cr = context.getContentResolver();

        Uri uri = MediaStore.Files.getContentUri("external");
        String selection = MediaStore.Images.Media.DATA;
        String[] selectionArgs = {songPath};
        String[] projection = {MediaStore.Images.Media._ID};

        Cursor cursor = cr.query(uri, projection, selection + "=?", selectionArgs, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                id = Long.parseLong(cursor.getString(idIndex));
            }
        }

        return id;
    }

    private void requestDeletePermission(ArrayList<Uri> uris) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            PendingIntent pi = MediaStore.createDeleteRequest(getContentResolver(), uris);
            try {
                startIntentSenderForResult(pi.getIntentSender(), 1111, null, 0, 0, PendingIntent.FLAG_IMMUTABLE);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode != 0) {
            deleteFromGallery(phototUri.getPath());
        }
    }

    public void deleteFromGallery(String str) {
        String[] strArr = {"_id"};
        String[] strArr2 = {str};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(uri, strArr, "_data = ?", strArr2, null);
        if (query.moveToFirst()) {
            try {
                contentResolver.delete(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))), null, null);
            } catch (Exception securityException) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    RecoverableSecurityException recoverableSecurityException;
                    if (securityException instanceof RecoverableSecurityException) {
                        recoverableSecurityException =
                                (RecoverableSecurityException) securityException;
                    } else {
                        throw new RuntimeException(
                                securityException.getMessage(), securityException);
                    }
                    IntentSender intentSender = recoverableSecurityException.getUserAction()
                            .getActionIntent().getIntentSender();
                    try {
                        startIntentSenderForResult(intentSender, 1003,
                                null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                } else {
                    throw new RuntimeException(
                            securityException.getMessage(), securityException);
                }
            }
        } else {
            try {
                new File(str).delete();
//                Toast.makeText(this, getString(R.string.delete_successfully), Toast.LENGTH_LONG).show();
                Intent intent = new Intent("load_my_creation");
                LocalBroadcastManager.getInstance(ImageMyCreationScreenActivity.this).sendBroadcast(intent);
                finish();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        query.close();
    }

    String uri1 = "";

    private void shareFileImage(String path) {
        Log.d("TAG", "shareFileVideo: " + path);
        File file;
        if (path.contains("content://com."))
            intentFile(new File(Uri.parse(path).getPath()));
        else if (path.contains("content://")) {
            uri1 = "file://" + path;
            file = new File(Uri.parse(uri1).getPath());
            intentFile(file);
        } else {
            uri1 = "file://" + path;
            file = new File(Uri.parse(uri1).getPath());
            intentFile(file);
        }
    }

    private void intentFile(File file) {
        String packageName = getApplicationContext().getPackageName();

        if (file.exists()) {
            Uri _uri = FileProvider.getUriForFile(this,
                    packageName + ".provider", file);
            Intent intent2 = new Intent(Intent.ACTION_SEND);
            intent2.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
            intent2.setType("video/*");
            intent2.putExtra("android.intent.extra.STREAM", _uri);
            intent2.putExtra("android.intent.extra.TEXT", "Image");
            startActivity(Intent.createChooser(intent2, "Where to Share?"));
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
