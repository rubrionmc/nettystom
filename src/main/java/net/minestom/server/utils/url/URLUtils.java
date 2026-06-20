// Déclaration du paquet de ce fichier
package net.minestom.server.utils.url;

// Import d'une classe nécessaire
import java.io.BufferedReader;
// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.io.InputStream;
// Import d'une classe nécessaire
import java.io.InputStreamReader;
// Import d'une classe nécessaire
import java.net.HttpURLConnection;
// Import d'une classe nécessaire
import java.net.URI;

// Déclaration de type (classe/interface/enum/record)
public final class URLUtils {

    // Début d'une méthode/d'un bloc
    private URLUtils() {

    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static String getText(String url) throws IOException {
        // Appelle une méthode
        HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
        //add headers to the connection, or check the status if desired..

        // handle error response code it occurs
        // Appelle une méthode
        final int responseCode = connection.getResponseCode();
        // Instruction de code
        final InputStream inputStream;
        // Embranchement : vérifie une condition
        if (200 <= responseCode && responseCode <= 299) {
            // Appelle une méthode
            inputStream = connection.getInputStream();
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            inputStream = connection.getErrorStream();
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        BufferedReader in = new BufferedReader(
                // Crée un nouvel objet
                new InputStreamReader(
                        // Instruction de code
                        inputStream));

        // Appelle une méthode
        StringBuilder response = new StringBuilder();
        // Instruction de code
        String currentLine;

        // Boucle : répète un bloc
        while ((currentLine = in.readLine()) != null)
            // Appelle une méthode
            response.append(currentLine);

        // Appelle une méthode
        in.close();

        // Renvoie une valeur à l'appelant
        return response.toString();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
