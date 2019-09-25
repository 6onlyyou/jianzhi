package fu.com.parttimejob.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import fu.com.parttimejob.R;


public class WebActivity extends AppCompatActivity {
    public final static String URL = "url";
    public final static String TITLE = "title";
    WebView mWebView;
    String imgurl;
    private ProgressBar progressBar;
    private Button go_share;
    private GestureDetector gestureDetector;
    private int downX, downY;
    String picUrl;
    private long backTime = 0;
    public static void startSelf(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                downX = (int) e.getX();
                downY = (int) e.getY();
            }
        });
        String url = getIntent().getStringExtra(URL);
        final String title = getIntent().getStringExtra(TITLE);
        ((TextView) findViewById(R.id.title)).setText(title + "");
        go_share = ((Button) findViewById(R.id.go_share));
        progressBar = ((ProgressBar) findViewById(R.id.pb));
        mWebView = ((WebView) findViewById(R.id.webView));
        progressBar.setMax(100);
        if(url.contains("yaoqin")){
            go_share.setVisibility(View.VISIBLE);
        }else{
            go_share.setVisibility(View.GONE);
        }
        go_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ContextCompat.checkSelfPermission(WebActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(WebActivity.this,
                            new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},
                            7);
                } else {
                }
            }
        });
        //屏幕自适应
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //对网页某些标签不支持,显示空白页的解决方案
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        //支持js
        webSettings.setJavaScriptEnabled(true);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final WebView.HitTestResult result = mWebView.getHitTestResult();
                if (null == result)
                    return false;
                int type = result.getType();
                switch (type) {
                    case WebView.HitTestResult.EDIT_TEXT_TYPE: // 选中的文字类型
                        break;
                    case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE: // 　地图类型
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE: // 带有链接的图片类型
                    case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                        if (Build.VERSION.SDK_INT >= 23) {
                            int REQUEST_CODE_CONTACT = 101;
                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            for (String str : permissions) {
                                if (WebActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) { //申请权限
                                    WebActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                                } else {

                                }
                            }
                        }


                        return true;
                }
                return false;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar != null) {
                    if (newProgress >= 100) {
                        progressBar.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(newProgress);
                    }
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return super.onJsConfirm(view, url, message, result); //alert 弹出
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                //域名拦截
                String[] str = url.split("/");
                String place = str[2];
                return super.shouldInterceptRequest(view, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                setWebImageClick(view);
            }
        });


        mWebView.loadUrl(url);

    }
    private  void setWebImageClick(WebView view) {

    }
    public void url2bitmap(String url) {
        Bitmap bitmap = null;
        try {
            URL iconUrl = new URL(picUrl);
            URLConnection connection = iconUrl.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            int length = httpURLConnection.getContentLength();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, length);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            bufferedInputStream.close();
            inputStream.close();
            if (bitmap != null) {
                final Bitmap finalBitmap = bitmap;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        save2Album(finalBitmap);
                    }
                });
            }
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WebActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
            });
            e.printStackTrace();
        }
    }

    private void save2Album(Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "相册名称");
        if (!appDir.exists()) appDir.mkdir();
        String[] str = picUrl.split("/");
        String fileName = str[str.length - 1];
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            try {
                savePhotoToMedia(WebActivity.this, file, fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }

        } catch (IOException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WebActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
            });
            e.printStackTrace();
        }
    }

    public static void savePhotoToMedia(Context context, File file, String fileName) throws FileNotFoundException {
        String uriString = MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
//        String uriString = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null);
        File file1 = new File(getRealPathFromURI(Uri.parse(uriString), context));
        updatePhotoMedia(file1, context);
    }

    //更新图库
    private static void updatePhotoMedia(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
        Toast.makeText(context, "保存成功,长按点击扫一扫即可加微信客服", Toast.LENGTH_SHORT).show();
    }

    //得到绝对地址
    private static String getRealPathFromURI(Uri contentUri, Context context) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String fileStr = cursor.getString(column_index);
        cursor.close();
        return fileStr;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (goBack()) return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean goBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();//返回上一浏览页面
            return true;
        } else {
            finish();//关闭Activity
        }
        return false;
    }

}
