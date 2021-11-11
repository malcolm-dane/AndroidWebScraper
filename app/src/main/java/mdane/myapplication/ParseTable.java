/**
 * Created by - on 4/3/2017.
 */
package mdane.myapplication;

import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//Static Methods that the main application will call to populate ListVIewContent2 in INterfaced activity. Interfaced implements GoogleSearch interface which contains the method signatures.
public class ParseTable {
    static ArrayList allStocks;
    static Document webpage;
    //static final String volumegainers = URL.setURLStreamHandlerFactory("http://www.stockcharts.com/def/servlet/SC.scan?s=I.Y|TSAL[t.t_eq_s]![as0,20,tv_gt_40000]![tv0_gt_as1,20,tv*4]![tc0_gt_tc1]&report");
    // static String[] tickersAndnames;
    private static String exchange;
    private static String sector;
    private static String industry;
    HashMap a;

    //The constructor, it sets all of the search parameters to null
    public ParseTable(Document webpage) throws IOException {
        this.webpage = webpage;
        this.exchange = null;
        this.sector = null;
        this.industry = null;
    }

    public ParseTable(Document webpage, String exchange, String Industry, ArrayList aList) throws IOException {

        this.webpage = webpage;
        this.exchange = exchange;
        this.industry = Industry;
        this.allStocks = aList;
        // getUserSelectedExchangesandSectors(aList,exchange,Industry,webpage);
    }

    public ParseTable() {
    }

    public static String getExchange() {
        return exchange;
    }

    //Set the exchange to search
    public static void setExchange(String a) {
        a = exchange;
    }

    public static void setWebPage(Document a) {
        webpage = a;
    }

    public static Document getPage() {
        return webpage;
    }

    public static String getSector() {
        return sector;
    }

    //Set the sector to search
    public static void setSector(String b) {
        b = sector;
    }

    //Set the industry to search
    public static void setIndustry(String c) {
        c = industry;
    }


    /*
    This code parses the table and returns an array of strings. It will only grab the parameters the user specifies or
    else it will grab all the stocks on the table.
     */
    public static ArrayList getUserSelectedExchangesandSectors( String Exchange, String Industry,String Business) {
        ArrayList allStocks = new ArrayList<String>();
        exchange = Exchange;
try{
    webpage= Jsoup.connect("http://www.stockcharts.com/def/servlet/SC.scan?s=I.Y|TSAL[t.t_eq_s]![as0,20,tv_gt_40000]![tv0_gt_as1,20,tv*4]![tc0_gt_tc1]&report=predefalli").userAgent("Mozilla").get();

        Element table = webpage.select("table").get(0);
        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            HashMap aMap = new HashMap();/*
            System.out.println(cols.get(1).text());
            System.out.println(cols.get(2).text());

            System.out.println();
            */
            System.out.println(cols.get(5).text());
            System.out.println(Exchange);
          //  aAr[i - 1] = (cols.get(2).text());
            if (cols.get(3).text().equalsIgnoreCase(Exchange) && (cols.get(5).text().equalsIgnoreCase(Industry))) {

                aMap.put("business", cols.get(2).text());
                aMap.put("Industry", cols.get(5).text());
                aMap.put("exchange", cols.get(3).text());
                aMap.put("noMatch", "business that match on that exchange");
                allStocks.add(aMap);
               // saveIt(aMap);
                saveSomething(aMap.toString());

            } else {
               if(cols.get(2).text().equalsIgnoreCase(Business)||cols.get(3).text().equalsIgnoreCase(exchange)||cols.get(5).text().equalsIgnoreCase(Industry))
               {
                   aMap.put("business", cols.get(2).text());

                   aMap.put("exchange", cols.get(3).text());
                   aMap.put("Industry", cols.get(5).text());
                   aMap.put("noMatch", "No match on Industry. are smiliar businesses");
                   allStocks.add(aMap);
                   //saveIt(aMap);
               }else{


                if (cols.get(5).text().equalsIgnoreCase(Industry) || (cols.get(3).text().equalsIgnoreCase(exchange))) {
                    if (cols.get(3).text().equalsIgnoreCase(exchange) && (!cols.get(5).text().equalsIgnoreCase(Industry))) {
                        //    aAr[i - 1] = (cols.get(2).text());
                        aMap.put("business", cols.get(2).text());

                        aMap.put("exchange", cols.get(3).text());
                        aMap.put("Industry", cols.get(5).text());
                        aMap.put("noMatch", "No match on Industry. are smiliar businesses");
                        allStocks.add(aMap);
                        //saveIt((HashMap) aMap);
                    }
                    if (!cols.get(3).text().equalsIgnoreCase(exchange) && (cols.get(5).text().equalsIgnoreCase(Industry))) {
                        //   aAr[i - 1] = (cols.get(2).text());
                        aMap.put("business", cols.get(2).text());
                        aMap.put("Industry", "Industry:" + cols.get(5).text());
                        aMap.put("exchange", cols.get(3).text());
                        aMap.put("noMatch", "No business that match on that exchange");
                        allStocks.add(aMap);
                        //saveIt((HashMap) aMap);
                    }else{
                        if (!cols.get(3).text().equalsIgnoreCase(exchange) && (!cols.get(5).text().equalsIgnoreCase(Industry)))

                        aMap.put("business", "none");
                        aMap.put("Industry", "none");
                        aMap.put("exchange","none");
                        aMap.put("noMatch", "No business that match on that exchange");
                        saveSomething(aMap.toString());

                       // saveIt((HashMap) aMap);

                    }
                }
            }}
           // allStocks.add(aMap);
        }
        return allStocks;  }catch(IOException e){} return allStocks;  }

public static void saveIt(HashMap _aMap){

    ParseObject _aList=new ParseObject("aMap");
    _aList.put("aMap",_aMap);
    _aList.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
            Log.i("saved","amap");

        }
    });
}

    public static void saveSomething(Object _aObject){

        ParseObject _aList=new ParseObject("aMap2");
        _aList.put("aMaps",_aObject);
        _aList.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.i("saveSomething","amap");

            }
        });
    }


    public static ArrayList googleResults (String URL){
        ArrayList gresults=new ArrayList();
        int results=10;
        //Replace space by + in the keyword as in the google search url
        //  keyword = keyword.replace(" ", "+");

       // String url = "https://www.google.com/search?q="+Industry+"&ie=utf-8&oe=utf-8";//+String.valueOf(results);
        // https://www.google.com/search?q=quantum+break&ie=utf-8&oe=utf-8
        //Connect to the url and obain HTML response
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(URL)
                    .userAgent("Mozilla")
                    .followRedirects(true)
                    .ignoreHttpErrors(true)
                    .timeout(5000).get();

            String title = doc.title();
            System.out.println("title : " + title);

            Elements links = doc.select("a[href]");
            for (Element link : links) {
                HashMap aMap=new HashMap();
                System.out.println("\nlink : " + link.absUrl("href"));
                System.out.println("text : " + link.text());
                if(link.text().equalsIgnoreCase("News")
                        ||link.text().equalsIgnoreCase("Search")
                        ||(link.text().equalsIgnoreCase("Finance")
                        ||link.text().equalsIgnoreCase("Past Month"))
                        ||link.text().equalsIgnoreCase("Past Week")
                        ||link.text().equalsIgnoreCase("Verbatim"))
                {

                aMap.put("Industry",link.absUrl("href"));
                aMap.put("exchange",link.text());
                gresults.add(aMap);

                    saveSomething(aMap.toString());

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return gresults;
    }










    public static ArrayList googleResults (String Industry,String Exchange){
        ArrayList gresults=new ArrayList();
int results=10;
            //Replace space by + in the keyword as in the google search url
          //  keyword = keyword.replace(" ", "+");

            String url = "https://www.google.com/search?q="+Industry+"&ie=utf-8&oe=utf-8";//+String.valueOf(results);
       // https://www.google.com/search?q=quantum+break&ie=utf-8&oe=utf-8
            //Connect to the url and obain HTML response
            Document doc = null;
            try {
                doc = Jsoup
                        .connect(url)
                        .userAgent("Mozilla")
                        .followRedirects(true)
                        .ignoreHttpErrors(true)
                        .timeout(5000).get();

                String title = doc.title();
                System.out.println("title : " + title);
               String[] aList=new String[]{"Books","Wallet","Shopping","Blogger","Google Home","Photos","Maps"};

                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    HashMap aMap=new HashMap();

                        System.out.println("\nlink : " + link.absUrl("href"));
                        System.out.println("text : " + link.text());
if(link.text().equalsIgnoreCase("News")
        ||link.text().equalsIgnoreCase("Search")
        ||(link.text().equalsIgnoreCase("Finance")
        ||link.text().equalsIgnoreCase("Past Month"))
        ||link.text().equalsIgnoreCase("Past Week")
        ||link.text().equalsIgnoreCase("Verbatim"))
        {

                    aMap.put("Industry",link.absUrl("href"));
                    aMap.put("exchange",link.text());
                    gresults.add(aMap);} }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return gresults;
        }







    /*
    This is a deprecated method, it only got the Nasdaq methods
     */
    public static ArrayList getNasdaqOnly(ArrayList<String> names) {
        ArrayList allnas = new ArrayList<String>();
        Element table = webpage.select("table").get(0);
        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");

            if (cols.get(3).text().equals("NASD"))
                names.add(cols.get(2).text());

        }
        return allnas;
    }

    public static ArrayList getAllExchanges(Document webpage, String[] tickersAndnames) {
        ArrayList allStocks = new ArrayList<String>();
        Element table = webpage.select("table").get(0);
        Elements rows = table.select("tr");


        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            HashMap aMap=new HashMap();
            /*
            System.out.println(cols.get(1).text());
            System.out.println(cols.get(2).text());
            System.out.println(cols.get(3).text());
            System.out.println();
            */
            aMap.put("business", "none");

            aMap.put("exchange","none");
//            tickersAndnames[i - 1] = (cols.get(2).text());
            aMap.put("Industry", cols.get(2).text());
            allStocks.add(aMap);
            saveSomething(aMap.toString());

//            System.out.println(tickersAndnames[i - 1]);

        }

        return allStocks;
    }


}



