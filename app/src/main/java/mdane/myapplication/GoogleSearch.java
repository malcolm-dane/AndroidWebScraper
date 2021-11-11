package mdane.myapplication;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Created by - on 6/14/2017.
 */

public interface GoogleSearch {
  ArrayList gResults (String Industry, String Exchange);
ArrayList googleResults (String URL);
    ArrayList getUserSelectedExchangesandSectors(String Business, String Exchange, String Industry);
    ArrayList getAllExchanges(Document webpage, String[] tickersAndnames);
 void BingSearch();
    void updateUI();
}
