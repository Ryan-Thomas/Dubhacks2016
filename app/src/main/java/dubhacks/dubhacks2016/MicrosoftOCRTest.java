package dubhacks.dubhacks2016;

/**
 * Created by andrewzhaoluo on 10/15/16.
 *
 * THis file contains snuppets and method snippets for using microsoft's ocr
 */



// // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
import java.net.URI;
import java.net.*;
import java.io.*;

import android.content.ContentValues;
import android.net.Uri;
import android.renderscript.ScriptGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

public class MicrosoftOCRTest
{
    /**
     * Given input string, returns the contents of the entire input stream as string
     */
    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Given jsonstring, prints it purty
     */
    private static String toPrettyFormat(String jsonString)
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    /**
     * given image url, returns json string describing text parsing.
     * returns null if there is an error
     */
    public String runOCR(String img){
        try {
            URL url = new URL("https://api.projectoxford.ai/vision/v1.0/ocr");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setDoOutput(true);

            //header of method sent
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key",
                    "7c24cf51af8041acadf92f02616ce96e");

            //set some body parameters
            connection.setRequestProperty("language", "unk");
            connection.setRequestProperty("detectOrientation", "True");

            //post of query parameters
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            //write body of image
            String str =  "{\"url\":\"" + img + "\"}";
            byte[] outputInBytes = str.getBytes("UTF-8");
            os.write(outputInBytes);

            //read response
            InputStream in = connection.getInputStream();

            String jsonString = convertStreamToString(in);
            os.close();

            return jsonString;
        }
        catch(Exception e){
            System.out.println("Failed");
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args)
    {
        try {
            URL url = new URL("https://api.projectoxford.ai/vision/v1.0/ocr");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setDoOutput(true);

            //header of method sent
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key",
                                                    "7c24cf51af8041acadf92f02616ce96e");

            //parameters of HTTP POST
            //ContentValues values = new ContentValues();
            //values.put("language", "unk");
            //values.put("detectOrientation", "true");

            connection.setRequestProperty("language", "unk");
            connection.setRequestProperty("detectOrientation", "True");

            //post of query parameters
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            //writer.write(query);

            //write body of image
            String img = "http://searchengineland.com/figz/wp-content/" +
                    "seloads/2011/08/sel-inm-200-600x256.png";
            String str =  "{\"url\":\"" + img + "\"}";
            byte[] outputInBytes = str.getBytes("UTF-8");
            os.write(outputInBytes);

            //read response
            InputStream in = connection.getInputStream();

            String jsonString = convertStreamToString(in);

            System.out.println(toPrettyFormat(jsonString));
            os.close();
        }
        catch(Exception e){
            System.out.println("Failed");
            e.printStackTrace();
        }
    }
}

