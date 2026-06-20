// Package declaration for this file
package net.minestom.server.utils.url;

// Import of a required class
import java.io.BufferedReader;
// Import of a required class
import java.io.IOException;
// Import of a required class
import java.io.InputStream;
// Import of a required class
import java.io.InputStreamReader;
// Import of a required class
import java.net.HttpURLConnection;
// Import of a required class
import java.net.URI;

// Type declaration (class/interface/enum/record)
public final class URLUtils {

    // Start of a method/block
    private URLUtils() {

    // End of a block/expression
    }

    // Start of a method/block
    public static String getText(String url) throws IOException {
        // Calls a method
        HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
        //add headers to the connection, or check the status if desired..

        // handle error response code it occurs
        // Calls a method
        final int responseCode = connection.getResponseCode();
        // Code statement
        final InputStream inputStream;
        // Branch: checks a condition
        if (200 <= responseCode && responseCode <= 299) {
            // Calls a method
            inputStream = connection.getInputStream();
        // Alternative branch of the condition
        } else {
            // Calls a method
            inputStream = connection.getErrorStream();
        // End of a block/expression
        }

        // Assigns a value
        BufferedReader in = new BufferedReader(
                // Creates a new object
                new InputStreamReader(
                        // Code statement
                        inputStream));

        // Calls a method
        StringBuilder response = new StringBuilder();
        // Code statement
        String currentLine;

        // Loop: repeats a block
        while ((currentLine = in.readLine()) != null)
            // Calls a method
            response.append(currentLine);

        // Calls a method
        in.close();

        // Returns a value to the caller
        return response.toString();
    // End of a block/expression
    }
// End of a block/expression
}
