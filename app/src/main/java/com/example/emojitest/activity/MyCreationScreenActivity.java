package com.example.emojitest.activity;


import static com.example.emojitest.activity.ImageMyCreationScreenActivity.getFilePathToMediaID;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.RecoverableSecurityException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emojitest.R;
import com.example.emojitest.adapter.MyCreationAdapterMyGallery;
import com.example.emojitest.util.SystemUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyCreationScreenActivity extends AppCompatActivity implements MyCreationAdapterMyGallery.OnClickImage {
    RecyclerView rcvListImg;
    List<Uri> listImage = new ArrayList<>();
    MyCreationAdapterMyGallery myCreationAdapter;
    public static ImageView back, cancel, select_all, unselect_all, clear_item, clear_all;
    TextView tv_toolbar;
    public static RelativeLayout ll_select, ll_delete;
    int cout = 0;
    String path = "";
    private boolean isClick = false;
    public static RelativeLayout rlBanner;
    ActivityResultLauncher<IntentSenderRequest> deleteIntent = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            for (Uri con : myCreationAdapter.getSelectedUris()) {
                if (cout == myCreationAdapter.getSelectedCount()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        deleteFromGallery(con.getPath());
                    }
                }
            }

            loadListCreation();
        }
    });
    String[] extensions = new String[]{"jpg", "jpeg", "JPG", "JPEG"};


    public void onCreate(Bundle bundle) {
        SystemUtil.setLocale(this);
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_creation);
        mapping();
        viewListener();
    }

    private void mapping() {
        tv_toolbar = findViewById(R.id.txt);
        rcvListImg = findViewById(R.id.rcvListImg);
        back = findViewById(R.id.back);
        cancel = findViewById(R.id.cancel);
        select_all = findViewById(R.id.select_all);
        unselect_all = findViewById(R.id.unselect_all);
        clear_item = findViewById(R.id.clear_item);
        clear_all = findViewById(R.id.clear_all);
        ll_select = findViewById(R.id.ll_select);
        ll_delete = findViewById(R.id.ll_dele);
    }

    public static void requestPermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 922);
            }
        }
    }


    public void showCancel() {
        cancel.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
    }

    public void onResume() {
        requestPermissions(this);
        super.onResume();
        try {
            loadListCreation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isClick = false;

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void viewListener() {
        tv_toolbar.setText(getText(R.string.my_creation));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCreationAdapter.selectAll();
                showCancel();
                ll_select.setVisibility(View.VISIBLE);
                ll_delete.setVisibility(View.VISIBLE);
                select_all.setVisibility(View.GONE);
                clear_all.setVisibility(View.VISIBLE);
                clear_item.setVisibility(View.GONE);
                unselect_all.setVisibility(View.VISIBLE);

            }
        });
        unselect_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCreationAdapter.clearSelection1();
                select_all.setVisibility(View.VISIBLE);
                unselect_all.setVisibility(View.GONE);
                ll_delete.setVisibility(View.GONE);
                clear_item.setVisibility(View.GONE);
                clear_all.setVisibility(View.GONE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCreationAdapter.clearSelection();
                cancel.setVisibility(View.GONE);
                ll_select.setVisibility(View.GONE);
                ll_delete.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                ll_delete.setVisibility(View.GONE);

            }
        });
        clear_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv_cancel, tv_delete;
                final Dialog dialog = new Dialog(MyCreationScreenActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_delete);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                tv_cancel = dialog.findViewById(R.id.cancel);
                tv_delete = dialog.findViewById(R.id.delete);

                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<Uri> selectedUris = myCreationAdapter.getSelectedUris();

                        for (Uri uri : selectedUris) {
                            path = uri.getPath();
                        }
                        if (Build.VERSION.SDK_INT >= 30) {
                            for (Uri con : selectedUris) {
                                deleteImageAPI30(con.getPath());
                            }
                        } else {
                            Timer timer = new Timer();
                            timer.scheduleAtFixedRate(new TimerTask() {
                                @RequiresApi(api = Build.VERSION_CODES.R)
                                @Override
                                public void run() {
                                    if (cout > myCreationAdapter.getSelectedUris().size()) {
                                        timer.cancel();
                                        cout = 0;
                                        runOnUiThread(() -> {
                                            loadListCreation();
                                        });
                                    } else {
                                        for (Uri con : selectedUris) {
                                            if (cout == selectedUris.indexOf(con)) {
                                                deleteFromGallery(con.getPath());
                                            }
                                        }
                                        cout += 1;
                                    }
                                }
                            }, 0, 200);
                        }
                        cancel.setVisibility(View.GONE);
                        ll_select.setVisibility(View.GONE);
                        ll_delete.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                myCreationAdapter.removeSelectedItems();
                TextView tv_cancel, tv_delete;
                final Dialog dialog = new Dialog(MyCreationScreenActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_delete);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                tv_cancel = dialog.findViewById(R.id.cancel);
                tv_delete = dialog.findViewById(R.id.delete);
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<Uri> selectedUris = myCreationAdapter.getSelectedUris();
                        if (selectedUris.isEmpty()) {
                            // Handle empty selected URIs
                            return;
                        }
                        for (Uri uri : selectedUris) {
                            path = uri.getPath();
                        }
                        if (Build.VERSION.SDK_INT >= 30) {
                            for (Uri con : selectedUris) {
                                deleteImageAPI30(con.getPath());
                            }
                        } else {
                            Timer timer = new Timer();
                            timer.scheduleAtFixedRate(new TimerTask() {
                                @RequiresApi(api = Build.VERSION_CODES.R)
                                @Override
                                public void run() {
                                    if (cout > myCreationAdapter.getSelectedUris().size()) {
                                        timer.cancel();
                                        cout = 0;
                                        runOnUiThread(() -> {
                                            loadListCreation();
                                        });
                                    } else {
                                        for (Uri con : selectedUris) {
                                            if (cout == selectedUris.indexOf(con)) {
                                                deleteFromGallery(con.getPath());
                                            }
                                        }
                                        cout += 1;
                                    }
                                }
                            }, 0, 200);
                        }
                        cancel.setVisibility(View.GONE);
                        ll_select.setVisibility(View.GONE);
                        ll_delete.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        myCreationAdapter.notifyDataSetChanged();
                        if (listImage.size() == 0) {
                            findViewById(R.id.layoutNoPics).setVisibility(View.VISIBLE);
                        } else {
                            findViewById(R.id.layoutNoPics).setVisibility(View.GONE);
                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }


    public void loadListCreation() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyCreationScreenActivity.this, 4, GridLayoutManager.VERTICAL, false);

        rcvListImg.setLayoutManager(gridLayoutManager);
        rcvListImg.setHasFixedSize(true);
        listImage = loadAllImages(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
        myCreationAdapter = new MyCreationAdapterMyGallery(this, listImage, new MyCreationAdapterMyGallery.OnClickImage() {
            @Override
            public void onClickImage(int pos) {
                if (!isClick) {
                    myCreationAdapter.clearSelection();
                    cancel.setVisibility(View.GONE);
                    back.setVisibility(View.VISIBLE);
                    ll_delete.setVisibility(View.GONE);
                    ll_select.setVisibility(View.GONE);
                    Intent intent = new Intent(MyCreationScreenActivity.this, ImageMyCreationScreenActivity.class);
                    intent.putExtra("uri", listImage.get(pos).getPath());
                    startActivity(intent);
                    isClick = true;
                }
            }
        });
        rcvListImg.setAdapter(myCreationAdapter);
        if (listImage.size() == 0) {
            findViewById(R.id.layoutNoPics).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.layoutNoPics).setVisibility(View.GONE);
        }
    }

    private List<Uri> loadAllImages(String str) {
        int size;
        HashMap hashMap = new HashMap();
        File file = new File(str, "/Emoji Maker");
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    if (!file2.isDirectory()) {
                        for (String endsWith : this.extensions) {
                            if (file2.getAbsolutePath().endsWith(endsWith)) {
                                hashMap.put(Long.valueOf(file2.lastModified()), Uri.fromFile(file2));
                            }
                        }
                    }
                }
            }
        }
        if (hashMap.size() == 0) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(hashMap.keySet());
        Collections.sort(arrayList);
        size = arrayList.size();
        ArrayList arrayList2 = new ArrayList();
        for (size--; size >= 0; size--) {
            arrayList2.add(hashMap.get(arrayList.get(size)));
        }
        return arrayList2;
    }

    @Override
    public void onClickImage(int pos) {
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
                        recoverableSecurityException = (RecoverableSecurityException) securityException;
                    } else {
                        throw new RuntimeException(securityException.getMessage(), securityException);
                    }
                    IntentSender intentSender = recoverableSecurityException.getUserAction().getActionIntent().getIntentSender();
                    try {
                        startIntentSenderForResult(intentSender, 1003, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                } else {
                    throw new RuntimeException(securityException.getMessage(), securityException);
                }
            }
        } else {
            try {
                new File(str).delete();
                Intent intent = new Intent("load_my_creation");
                LocalBroadcastManager.getInstance(MyCreationScreenActivity.this).sendBroadcast(intent);
                finish();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        query.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void deleteImageAPI30(String path) {
        ContentResolver contentResolver = this.getContentResolver();
        ArrayList<Uri> arrayList2 = new ArrayList();
        Long mediaID = getFilePathToMediaID(path, this);
        Uri urii = ContentUris.withAppendedId(MediaStore.Images.Media.getContentUri("external"), mediaID);
        arrayList2.add(urii); // You need to use the Uri you got using ContentUris.withAppendedId() method
        Collections.addAll(arrayList2);
        IntentSender intentSender = MediaStore.createDeleteRequest(contentResolver, arrayList2).getIntentSender();
        IntentSenderRequest senderRequest = new IntentSenderRequest.Builder(intentSender).setFillInIntent(null).setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION, 0).build();
        deleteIntent.launch(senderRequest);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}