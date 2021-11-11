package mdane.myapplication;

import android.os.Environment;
import android.util.Log;

import net.minidev.json.parser.JSONParser;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

//import org.json.simple.parser.JSONParser;

/**
 * Created by - on 4/30/2017.
 */

public class StaticUtil {

    public static File openFileFor(String a) {
        File imageDirectory = null;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            imageDirectory = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    ".search/.cache");
            if (!imageDirectory.exists() && !imageDirectory.mkdirs()) {
                imageDirectory = null;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss",
                        Locale.getDefault());

                return new File(imageDirectory.getPath() +
                        File.separator + a+".txt");
            }
        }
        return null;
    }

    public Thread newThread(Runnable r){
  return new Thread(r);
    }
    public static void maketheFile(ArrayList aList, String a)  {
        //PrintWriter pw= null;
        try{
            String content = "This is my content which would be appended " +
                    "at the end of the specified file";
            //Specify the file name and path here
            File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    ".search/.cache/"+a);

    	/* This logic is to create the file if the
    	 * file is not already present
    	 */
            if(!file.exists()){
                file.createNewFile();
            }

            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(aList.toString());
            //Closing BufferedWriter Stream
            bw.close();

            System.out.println("Data successfully appended at the end of file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

