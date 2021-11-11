package mdane.myapplication;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseConfig;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class v extends FragmentActivity {

    ListView listView;
    ListView lv;
   private ArrayList<String> listViewContent;
    private  ArrayList<HashMap<String, String>> listViewContent2;
    private  ArrayList<String> usernames;
    private   ArrayList<Double> latitudes;
    private   ArrayList<Double> longitudes;
    private  ArrayAdapter arrayAdapter;
    private  Button button;
    private   Button button2;
    private  EditText e1;
    private  EditText e3;
    private  Button make;
    private Button button6;
    private  String Content1;
    private  String Content2;
    private Location location;
    private  HashMap a;
    private  String[] aArray;
    //    String      jsonStr;
    private  Boolean hasnoNews;
    private  LocationManager locationManager;
    private  String provider;
    private   ListAdapter adapter;
    private  Handler aHandler = new Handler();
    private String mSearch;
    static boolean GoogleSelected;
    private boolean customSelected;
    protected static ArrayList<HashMap<String, String>>ListViewContentStatic;
protected static String listViewPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this);
        ParseUser.enableAutomaticUser();
        ParseUser.getCurrentUser().toString();
        setContentView(R.layout.v); Log.i("ParseObject",  ParseUser.getCurrentUser().toString());
        hasnoNews = true;
        customSelected=false;
        listView = (ListView) findViewById(R.id.listView);
        if (listViewContent == null || listViewContent.size() == 0) {
            listViewContent = new ArrayList<String>();
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewContent);
            listView.setAdapter(arrayAdapter);
        } else {
            arrayAdapter.notifyDataSetChanged();
        }
        listViewContent2 = new ArrayList<HashMap<String, String>>();

        listViewContent.add("Finding Today's Market Movers...");
        e1 = (EditText) findViewById(R.id.exc);


        e3 = (EditText) findViewById(R.id.ind);
        make = (Button) findViewById(R.id.make);
        button6=(Button)findViewById(R.id.button6);
        e1.setVisibility(View.INVISIBLE);
        e3.setVisibility(View.INVISIBLE);
        make.setVisibility(View.INVISIBLE);
        button = (Button) findViewById(R.id.button3);

        button2 = (Button) findViewById(R.id.button4);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                a = new HashMap();
                SpawnThread1(position);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpawnThread();
            }
        });
    }


    public void SpawnThread1(final int position) {
        if(customSelected){ mSearch = listViewContent2.get(position).get("business");}else{
        mSearch = listViewContent.get(position);}
        setContentView(R.layout.main2);
        lv = (ListView) findViewById(R.id.list);
        new GetSearch().execute();
    }

    public void updateUI1() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
//                Log.i("string", listViewContent2.get(position).get("url").toString());

                ListViewContentStatic=listViewContent2;
                ParseObject aObject=new ParseObject("URL");
                aObject.addAllUnique("urls",ListViewContentStatic);
                aObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null)
                        {;
;
                            }
                    }
                });
              //  I.putExtra("strings", listViewContent2.get(position).get("url").toString());
            //    I.putExtra("desp", listViewContent2.get(position).get("description").toString());
            //    I.putExtra("strings1", listViewContent2.get(position + 1).get("url").toString());
              // I.putExtra("desp1", listViewContent2.get(position + 1).get("description").toString());

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void updateUI() {
        // setContentView(R.layout.v);
        //  Looper.prepare();
        if(customSelected){

            adapter = new SimpleAdapter(v.this, listViewContent2,
                    R.layout.list_items, new String[]{"business", "Industry","exchange"},
                    new int[]{R.id.Business, R.id.Industry,R.id.exchange});}

        if(GoogleSelected){
            adapter = new SimpleAdapter(v.this, listViewContent2,
                R.layout.list_items, new String[]{"business", "Industry","exchange"},
                new int[]{R.id.Business, R.id.Industry,R.id.exchange});}
        new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
            @Override
            public void run() {
                listView.setVisibility(View.VISIBLE);
                if(customSelected||GoogleSelected){listView.setAdapter(adapter);adapter.areAllItemsEnabled();}
                arrayAdapter.notifyDataSetChanged();}


        });
        if (GoogleSelected) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {

                    ListViewContentStatic=listViewContent2;     listViewPosition=listViewContent2.get(position).get("Industry");
                    ParseObject aObject=new ParseObject("URL");
                    aObject.addAllUnique("urls",ListViewContentStatic);
                    aObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                        }
                    }); //   Intent I=new Intent(getApplicationContext(),v2.class);I.putExtra("strings",listViewContent);startActivity(I);
                }
            });
        }
    }



    public void SpawnThread() {
        final Thread aThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Document webpage = Jsoup.connect("http://www.stockcharts.com/def/servlet/SC.scan?s=I.Y|TSAL[t.t_eq_s]![as0,20,tv_gt_40000]![tv0_gt_as1,20,tv*4]![tc0_gt_tc1]&report=predefalli").userAgent("Mozilla").get();
                            ParseTable.setWebPage(webpage);

                            String[] a = new String[200];
                            listViewContent.addAll(ParseTable.getAllExchanges(webpage, a));

                            Log.i("array", Integer.toString(listViewContent.size()));
                            updateUI();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        aThread.start();
    }


    public void SpawnThreadButton2(View view) {
        e1.setVisibility(View.VISIBLE);
        e3.setVisibility(View.VISIBLE);
        make.setVisibility(View.VISIBLE);
        ;
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewContent2.clear();
                customSelected=true;
                Content1 = e1.getText().toString();
                Content2 = e3.getText().toString();
                e1.setVisibility(View.INVISIBLE);
                e3.setVisibility(View.INVISIBLE);
                make.setVisibility(View.INVISIBLE);
                Log.i(Content1, Content2);

                button2List();

            }

        });

    }
  public void  button6func(View view) {
      e1.setVisibility(View.VISIBLE);
      e3.setVisibility(View.VISIBLE);
      make.setVisibility(View.VISIBLE);
      listViewContent2.clear();
      customSelected = false;
      GoogleSelected = true;
      Content1 = e1.getText().toString();
      Content2 = e3.getText().toString();

      make.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              listViewContent2.clear();
              customSelected=true;
              Content1 = e1.getText().toString();
              Content2 = e3.getText().toString();
              e1.setVisibility(View.INVISIBLE);
              e3.setVisibility(View.INVISIBLE);
              make.setVisibility(View.INVISIBLE);
              Log.i(Content1, Content2);

              button2List();

          }

      });

  }


    public void button2List() {
        aArray = new String[200];

        final Thread aThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        if(GoogleSelected)
                        {
                            ArrayList aList = ParseTable.googleResults( Content1, Content2);
                            listViewContent2.addAll(aList);
                            updateUI();
                        }
                        else{
                        try {
                            Document webpage = Jsoup.connect("http://www.stockcharts.com/def/servlet/SC.scan?s=I.Y|TSAL[t.t_eq_s]![as0,20,tv_gt_40000]![tv0_gt_as1,20,tv*4]![tc0_gt_tc1]&report=predefalli").userAgent("Mozilla").get();
                            ParseTable.setWebPage(webpage);

                          //  ArrayList aList = ParseTable.getUserSelectedExchangesandSectors(aArray, Content1, Content2, webpage);
                       //     listViewContent2.addAll(aList);

                            Log.i("array", Integer.toString(listViewContent2.size()));
                            updateUI();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


    }); aThread.start();}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // locationManager.removeUpdates(this);

    }

    private class GetSearch extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
listViewContent2.clear();
            // Making a request to url and getting response
            String url = "https://api.cognitive.microsoft.com/bing/v5.0/news/search?q=" + mSearch;

            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray searchData = jsonObj.getJSONArray("value");
                    // Getting JSON Array node
                    // JSONArray ajsonObj = jsonObj.getJSONArray("value");
                    String[][] newsarticles = new String[searchData.length()][4];
                    // looping through All Contacts
                    for (int i = 0; i < searchData.length(); i++) {
                        // JSONObject c = ajsonObj.getJSONObject(i);
                        JSONObject person = searchData.getJSONObject(i);
                        //  String email = c.getString("email");
                        //   String address = c.getString("address");


                        newsarticles[i][0] = person.getString("name");
                        newsarticles[i][1] = person.getString("url");
                        newsarticles[i][2] = person.getString("description");
                        newsarticles[i][3] = person.getString("datePublished");
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("url", newsarticles[i][1]);
                        contact.put("description", newsarticles[i][2]);
                      contact.put("name",  mSearch);
                        //    contact.put("mobile", mobile);

                        // adding contact to contact list
                        listViewContent2.add(contact);
                    }
                } catch (final JSONException e) {
                    //  Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            listView = (ListView) findViewById(R.id.list);
            adapter = new SimpleAdapter(v.this, listViewContent2,
                    R.layout.list_items, new String[]{"url", "description"},
                    new int[]{R.id.description, R.id.urls});

            listView.setAdapter(adapter);
            adapter.areAllItemsEnabled();
            updateUI1();
        }
    }
}
