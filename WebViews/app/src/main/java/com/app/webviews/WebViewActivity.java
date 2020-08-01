package com.app.webviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WebViewActivity extends AppCompatActivity {

    private android.webkit.WebView webView = null;
    String urlString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);



        this.webView = findViewById(R.id.webViewId);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading, please wait ...");
        progressDialog.show();

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.clearCache(false);

        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);





        WebViewClient webViewClient = new WebViewClient();

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressDialog.show();
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                CookieSyncManager.getInstance().sync();
                progressDialog.dismiss();

            }
        });
        urlString = "https://gayathrisarees.com/";
        webView.loadUrl(urlString);

        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:

                        if (urlString.equals("https://gayathrisarees.com/")){
                            return true;
                        }
                        progressDialog.show();
                        urlString = "https://gayathrisarees.com/";
                        break;
                    case R.id.action_cart:

                        if (urlString.equals("https://gayathrisarees.com/cart/")){
                            return true;
                        }
                        progressDialog.show();
                        urlString = "https://gayathrisarees.com/cart/";
                        break;
                    case R.id.action_account:

                        if (urlString.equals("https://gayathrisarees.com/my-account/")){
                            return true;
                        }
                        progressDialog.show();
                        urlString = "https://gayathrisarees.com/my-account/";
                        break;
                }

                webView.loadUrl(urlString);
                return true;
            }
        });






    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



}


