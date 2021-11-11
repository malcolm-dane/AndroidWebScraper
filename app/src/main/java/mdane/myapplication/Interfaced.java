package mdane.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Interfaced extends AppCompatActivity implements GoogleSearch{
        public ArrayList<HashMap<String, String>> listViewContent2;
    Button aButton;
    HashMap mainHash;
        Button aButton2;
    Button aButton3;
    Button aButton4;
    EditText eIndustry;
    EditText eBusiness;
    EditText eExchange;
    String mIndustry;
    String mExchange;
    String mBusiness;
            ListView listView;
    SimpleAdapter sAdapter;
    private String Status="";
    private final String MarketMovers="MARKET_MOVERS";
    public static String staticUrl;
    public static ArrayList<HashMap<String, String>> listViewContentStatic;
    public static ArrayList ListViewContentStatic;
    String[] aArray; String AExchange;String AIndustry; Document Awebpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaced);
      //  this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Parse.initialize(this);
        ParseUser.enableAutomaticUser();
        ParseUser.getCurrentUser().toString();
        setupUI();
        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Status = MarketMovers;
                listView = (ListView) findViewById(R.id.listView2);
                sAdapter = new SimpleAdapter(getApplicationContext(), listViewContent2,
                        R.layout.list_items, new String[]{"business", "Industry", "exchange"},
                        new int[]{R.id.Business, R.id.Industry, R.id.exchange});
                listView.setAdapter(sAdapter);
                getMarketMovers();
                if (Status.equalsIgnoreCase("MARKET_MOVERS")) {
                    Thread aThread=new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String A = listViewContent2.get(position).get("Industry");
                                            listViewContent2.clear();
                                            updateUI();
                                           doThreadedRun(gResults(A, ""));
                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Intent I=new Intent(getApplicationContext(),Web.class);listViewContentStatic=listViewContent2; String theUrl=listViewContent2.get(position).get("Industry");
                                                    I.putExtra("aUrl",theUrl); startActivity(I);
                                                }
                                            });

                                        }}    );
                                        }
                                    });

                    aThread.start(); } }
});

                listViewContent2 = new ArrayList<HashMap<String, String>>();

            }
public void setupUI(){
    aButton=(Button)findViewById(R.id.button);
    aButton2=(Button)findViewById(R.id.button2);
    aButton3=(Button)findViewById(R.id.button3);
    aButton4=(Button)findViewById(R.id.button4);
    aButton4.setVisibility(View.INVISIBLE);
    eIndustry=(EditText)findViewById(R.id.Industry);
    eBusiness=(EditText)findViewById(R.id.Business);
    eExchange=(EditText)findViewById(R.id.Exchange);
    eIndustry.setVisibility(View.INVISIBLE);
    eBusiness.setVisibility(View.INVISIBLE);
    eExchange.setVisibility(View.INVISIBLE);
}
public void CustomSearch(View aView){
eIndustry.setVisibility(View.VISIBLE);
    eBusiness.setVisibility(View.VISIBLE);
    eExchange.setVisibility(View.VISIBLE);
    aButton4.setVisibility(View.VISIBLE);
    setListView();

    aButton4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
;    AExchange = eExchange.getText().toString();
            AIndustry=eIndustry.getText().toString();
            mBusiness=eBusiness.getText().toString();
       Thread aThread=    new Thread(
               new Runnable() {
                   @Override
                   public void run() {

                       doThreadedRun(getUserSelectedExchangesandSectors(mBusiness,AExchange,AIndustry));
                       updateUI();
                       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               String A=listViewContent2.get(position).get("business");
                               listViewContent2.clear();
                               listViewContent2.addAll(gResults(A,""));
                             updateUI();
                               listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                   @Override
                                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                       Intent I=new Intent(getApplicationContext(),Web.class); String aUrl=listViewContent2.get(position).get("Industry");
                                       I.putExtra("aUrl",aUrl);listViewContentStatic=listViewContent2; startActivity(I);
                                   }
                               });  }



                       });



                   }
               });aThread.start();}


});}

    public void keywordSearch(View view){
        eIndustry.setVisibility(View.INVISIBLE);
        eBusiness.setVisibility(View.INVISIBLE);
        eExchange.setVisibility(View.INVISIBLE);
        eExchange.setVisibility(View.VISIBLE);
        eExchange.setHint("Enter term");
setListView();
        aButton4.setVisibility(View.VISIBLE);
        aButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search=eExchange.getText().toString();
                doThreadedRun(gResults(search,""));
//                updateUI();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        listViewContentStatic=listViewContent2;
                        String aUrl=listViewContent2.get(position).get("Industry");
                        Intent I=new Intent(getApplicationContext(),Web.class);
                        I.putExtra("aUrl",aUrl);startActivity(I);
                    }
                });
            }
        });

    }
public void getMarketMovers(){
  Thread athread=new Thread(
          new Runnable() {
              @Override
              public void run() {
                  try {
                      Document webpage = Jsoup.connect("http://www.stockcharts.com/def/servlet/SC.scan?s=I.Y|TSAL[t.t_eq_s]![as0,20,tv_gt_40000]![tv0_gt_as1,20,tv*4]![tc0_gt_tc1]&report=predefalli").userAgent("Mozilla").get();
                      ParseTable.setWebPage(webpage);
                      String[] a = new String[201];
                      getAllExchanges(webpage,a);
                      updateUI();
                  }
                  catch(IOException e){}
              }
          }
          );
    athread.start();
}



        @Override
        public ArrayList gResults(final String Industry,final String Exchange) {
Thread a=new Thread(
        new Runnable() {
            @Override
            public void run() {
                listViewContent2.addAll(ParseTable.googleResults(Industry, Exchange));
                updateUI();
            }
        });a.start();
; return listViewContent2;
        }

        @Override
        public ArrayList googleResults(final String URL) {
            Thread a=new Thread(
                    new Runnable() {
                        @Override
                        public void run() {

                            listViewContent2.addAll(ParseTable.googleResults(URL));
                            updateUI();

                        }
                    });a.start();


            return listViewContent2;
        }

        @Override
        public ArrayList getUserSelectedExchangesandSectors( final String ab, final String bb,final String cb) {
            aArray = new String[200];
            Thread C = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {

                            listViewContent2.addAll(ParseTable.getUserSelectedExchangesandSectors(ab, bb, cb));

                        }
                    });
            C.start();

            // listViewContent2.addAll(getUserSelectedExchangesandSectors(aAr,  Exchange, Industry, webpage));
            return listViewContent2;
        }

        @Override
        public ArrayList getAllExchanges(Document webpage, String[] tickersAndnames) {
            listViewContent2.addAll(ParseTable.getAllExchanges(webpage,tickersAndnames));
            return listViewContent2;
        }

        @Override
        public void BingSearch() {

        }

        @Override
        public void updateUI() {

            runOnUiThread(new Runnable() {

    @Override

    public void run()
    {
        sAdapter.notifyDataSetChanged();
    }
});

}

public void setListView(){
    listView = (ListView) findViewById(R.id.listView2);
    sAdapter = new SimpleAdapter(getApplicationContext(), listViewContent2,
            R.layout.list_items, new String[]{"business", "Industry", "exchange"},
            new int[]{R.id.Business, R.id.Industry, R.id.exchange});
    listView.setAdapter(sAdapter);
    ParseObject _aList=new ParseObject("lvtwo");
    _aList.put("listviewtwo",listViewContent2.toString());
    _aList.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            Log.i("lv1","stocks");

        }
    });
}

public void test(Document h,String[] hh){

    doThreadedRun(getAllExchanges(h,hh));
}
/*This method is used to do the ParseTAble implmentation since they are all networked requests. This way strict mode doesnt need to be changed*/
public void doThreadedRun(final ArrayList aList)
    {
      Thread arrayList=new Thread(
              new Runnable() {
                  @Override
                  public void run() {
                      ParseObject _aList=new ParseObject("stockssss");
                      _aList.put("someStocksss",aList.toString());
                      _aList.saveInBackground(new SaveCallback() {
                          @Override
                          public void done(ParseException e) {
                              Log.i("saved","stocks");

                          }
                      });
                      listViewContent2.addAll(aList);
                         }
              });arrayList.start();

}

}