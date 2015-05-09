/**
 * Created by Harrison on 4/20/2015.
 */

/**
 IIIII       GGGGGGGGGGGGGGGG             NNNNNNNNNN         NNNNNN
 IIIII       GGGGGGGGGGGGGGGG             NNNNNN NNNN        NNNNNN
 IIIII       GGGGGGGGGGGGGGGG             NNNNNN  NNNN       NNNNNN
 IIIII       GGGGGGG                      NNNNNN   NNNN      NNNNNN
 IIIII       GGGGGGG                      NNNNNN    NNNN     NNNNNN
 IIIII       GGGGGGG     GGGGGGGGG        NNNNNN     NNNN    NNNNNN
 IIIII       GGGGGGG     GGGGGGGGG        NNNNNN      NNNN   NNNNNN
 IIIII       GGGGGGG       GGGGGGG        NNNNNN       NNNN  NNNNNN
 IIIII       GGGGGGGGGGGGGGGGGGGGG        NNNNNN        NNNN NNNNNN
 IIIII       GGGGGGGGGGGGGGGGGGGGG        NNNNNN         NNNNNNNNNN

 */



import org.json.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class IGNRequest {

    public static void main(String[] args) throws IOException, JSONException {
        parseJson("http://ign-apis.herokuapp.com/videos");
        parseJson("http://ign-apis.herokuapp.com/articles");
    }

    //read through the data and convert to string for parsing
    private static String readUrl(Reader link) throws IOException {
        StringBuilder builder = new StringBuilder();
        int character;
        while ((character = link.read()) != -1) {
            builder.append((char) character);
        }
        return builder.toString();
    }

    //parse through JSON data.
    public static JSONObject parseJson(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();

        try {
            BufferedReader link = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String strObj = readUrl(link);
            JSONObject response = new JSONObject(strObj);
            JSONArray data = response.getJSONArray("data");

            if(url.equals("http://ign-apis.herokuapp.com/videos")) {
                System.out.println("VIDEOS\n");
            }

            if(url.equals("http://ign-apis.herokuapp.com/articles")) {
                System.out.println("ARTICLES\n");
            }

            for (int i = 0; i < data.length(); i++) {
                String title;
                String description;
                String duration;
                String headline;
                String subheadline;
                int seconds;

                JSONObject obj = data.getJSONObject(i).getJSONObject("metadata");

                if (obj.has("title")) {
                    title = obj.getString("title");
                } else {
                    title = "";
                }

                if (obj.has("description")) {
                    description = obj.getString("description");
                } else {
                    description = "";
                }

                if(obj.has("duration")) {
                    seconds = obj.getInt("duration");
                    convertTime(seconds);
                    duration = convertTime(seconds);
                } else {
                    duration = "";
                }

                if (obj.has("headline")) {
                    headline = obj.getString("headline");
                } else {
                    headline = "";
                }

                if (obj.has("subHeadline")) {
                    subheadline = obj.getString("subHeadline");
                } else {
                    subheadline = "";
                }


                if(url.equals("http://ign-apis.herokuapp.com/videos")) {
                    System.out.println(i+1 + " " + title + "\n" + "  " + description + "\n" + "  " + duration + "\n");
                }

                if(url.equals("http://ign-apis.herokuapp.com/articles")) {
                    System.out.println(i+1 + " " + headline + "\n" + "  " + subheadline + "\n");
                }

            }

            return response;
        } finally {
            is.close();
        }
    }


    //convert seconds to minutes and seconds. On purpose I left out hours.
    private static String convertTime(int totalSeconds) {

        final int sixtySeconds = 60;

        int seconds = totalSeconds % sixtySeconds;
        int totalMinutes = totalSeconds / sixtySeconds;

        if(seconds < 10){
            return totalMinutes + ":" + "0" + seconds;
        } else {
            return totalMinutes + ":" + seconds;
        }
    }
}

