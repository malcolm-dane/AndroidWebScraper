package mdane.myapplication;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import static mdane.myapplication.Interfaced.listViewContentStatic;

public class Web extends TabActivity {

    private WebView wv1;
    private String aUrl;
    private SimpleAdapter sAdapter;
    private ListView aList;
    ArrayList<HashMap<String, String>>WebViewContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        wv1 = (WebView) findViewById(R.id.web);

        WebViewClient a=new MyBrowser();
        wv1.setWebViewClient(a);
// Create an Intent to launch an Activity for the tab (to be reused)

        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
// Initialize a TabSpec for each tab and add it to the TabHost
    TabHost host = (TabHost)findViewById(android.R.id.tabhost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Web");
        host.addTab(spec);
        loadURL(getIntent());
        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("URLS");
        host.addTab(spec);
        setListView();
        //Tab 3
        spec = host.newTabSpec("Tab Three");
        Intent i=new Intent(getApplicationContext(),Interfaced.class);
        spec.setContent(R.id.tab3);
        spec.setContent(i);
        spec.setIndicator("Search");
        host.addTab(spec);



    }

        //this.setNewTab(this, tabHost, "tab3", R.string.textTabTitle3, android.R.drawable.star_on, R.id.tab3);







       // wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY) ;



    void loadURL(final Intent I){
        Handler aHandler=new Handler();
        aHandler.post(


                new Runnable() {
                    @Override
                    public void run() {
                     if(I.getStringExtra("aUrl")!=null){
                      wv1.loadUrl(I.getStringExtra("aUrl"));
                    }//else{wv1.loadUrl(staticUrl)}
                    }
                });


    }
    public void setListView(){
        aList = (ListView) findViewById(R.id.aList1);
       WebViewContent=new ArrayList<HashMap<String, String>>();
        sAdapter = new SimpleAdapter(getApplicationContext(),WebViewContent,
                R.layout.list_items, new String[]{"business", "Industry", "exchange"},
                new int[]{R.id.Business, R.id.Industry, R.id.exchange});
        aList.setAdapter(sAdapter);
        WebViewContent.addAll(listViewContentStatic);
        sAdapter.notifyDataSetChanged();
        aList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             wv1.loadUrl(WebViewContent.get(position).get("Industry"));
            }
        });
    }
}
 class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

