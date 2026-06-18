import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String url = "https://books.toscrape.com/";

        try {
            Document doc = Jsoup.connect(url).get();

            Elements products = doc.select("article.product_pod");

            FileWriter writer = new FileWriter("products.csv");
            writer.append("Name,Price,Rating\n");

            for (Element product : products) {

                String name = product.select("h3 a").attr("title");

                String price = product.select(".price_color").text();

                String rating = product.select("p.star-rating")
                        .attr("class")
                        .replace("star-rating", "")
                        .trim();

                writer.append("\"")
                        .append(name)
                        .append("\",")
                        .append(price)
                        .append(",")
                        .append(rating)
                        .append("\n");

                System.out.println(
                        "Name: " + name +
                        " | Price: " + price +
                        " | Rating: " + rating
                );
            }

            writer.close();

            System.out.println("\nData successfully saved to products.csv");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
