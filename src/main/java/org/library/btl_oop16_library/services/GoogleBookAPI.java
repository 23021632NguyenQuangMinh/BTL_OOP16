package org.library.btl_oop16_library.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.library.btl_oop16_library.model.Book;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GoogleBookAPI {
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = ENV.getInstance().get("GOOGLE_BOOK_APIKEY");

    public static List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        try {
            JSONArray items = getObjects(query);

            if (items != null) {
                for (int i = 0; i < items.length(); i++) {
                    JSONObject bookJson = items.getJSONObject(i).getJSONObject("volumeInfo");

                    String title = bookJson.optString("title");
                    String author = "Unknown";

                    if (bookJson.optJSONArray("authors") != null) {
                        JSONArray authorsArray = bookJson.getJSONArray("authors");
                        StringBuilder authorsBuilder = new StringBuilder();
                        for (int j = 0; j < authorsArray.length(); j++) {
                            if (j > 0) {
                                authorsBuilder.append(", ");
                            }
                            authorsBuilder.append(authorsArray.getString(j));
                        }
                        author = authorsBuilder.toString();
                    }

                    String category = bookJson.optJSONArray("categories") != null
                            ? bookJson.getJSONArray("categories").getString(0)
                            : "N/A";
                    String language = bookJson.optString("language", "N/A");

                    String imageUrl = "";
                    if (bookJson.has("imageLinks")) {
                        JSONObject imageLinks = bookJson.getJSONObject("imageLinks");
                        imageUrl = imageLinks.optString("large  ", imageLinks.optString("thumbnail", ""));
                    }

                    String rating = bookJson.has("averageRating")
                            ? String.valueOf(bookJson.getDouble("averageRating"))
                            : "Unknown rating";
                    String description = bookJson.has("description")
                            ? bookJson.getString("description")
                            : "Unknown description";
                    String previewURL = bookJson.has("previewLink")
                            ? bookJson.getString("previewLink")
                            : "";

                    Book book = new Book(title, author, category, language, imageUrl, rating, description, previewURL);
                    books.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }


    private static JSONArray getObjects(String query) throws IOException, InterruptedException {
        String urlString = GOOGLE_BOOKS_API_URL + query.replace(" ", "+") + "&key=" + API_KEY;
        URI uri = URI.create(urlString);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.err.println(API_KEY);
            System.err.println("Error Response Code: " + response.statusCode());
            System.err.println("Error Response Body: " + response.body());
            throw new RuntimeException("Failed to fetch data: HTTP Response Code " + response.statusCode());
        }

        JSONObject jsonObject = new JSONObject(response.body());
        return jsonObject.optJSONArray("items");
    }

}
