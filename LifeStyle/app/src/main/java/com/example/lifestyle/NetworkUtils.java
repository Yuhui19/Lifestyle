package com.example.lifestyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

    private static String apiKey = "99ea8382701bd7481e5ea568772f739a";
    private static String baseUrl = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String apiKeyQuery = "&appid=" + apiKey;

    public static URL buildURLFromString(String location) throws MalformedURLException {
        String urlStr = baseUrl + location + apiKeyQuery;
        return new URL(urlStr);
    }

    public static String getDataFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            //The scanner trick: search for the next "beginning" of the input stream
            //No need to user BufferedReader
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                String res = scanner.next();
                System.out.println("JSON DATA: " + res);
                return res;
            }
            else{
                return null;
            }

        }finally {
            urlConnection.disconnect();
        }
    }
}
