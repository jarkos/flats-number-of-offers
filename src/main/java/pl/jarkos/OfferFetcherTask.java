package pl.jarkos;

import java.util.List;
import java.util.ResourceBundle;

public class OfferFetcherTask implements Runnable {

    private static ResourceBundle rb = ResourceBundle.getBundle("app");
    private static WebsiteDataFetcher websiteDataFetcher = new WebsiteDataFetcher();
    static final List<String> cities = List.of("warszawa", "krakow", "lodz", "poznan", "gdansk", "wroclaw", "szczecin",
            "katowice");

    @Override
    public void run() {
        String olxFlatsOffersGenericUrl = rb.getString("olx.real.estate.flats.url");
        String olxRoomsOffersGenericUrl = rb.getString("olx.real.estate.rooms.url");
        String otodomFlatsOffersGenericUrl = rb.getString("otodom.real.estate.flats.url");
        String otodomRoomsOffersGenericUrl = rb.getString("otodom.real.estate.rooms.url");
        cities.forEach(city -> {
            websiteDataFetcher.getOlxFlatsOffersData(olxFlatsOffersGenericUrl, city);
            websiteDataFetcher.getOtodomFlatsOffersData(otodomFlatsOffersGenericUrl, city);
            websiteDataFetcher.getOlxRoomsOffersData(olxRoomsOffersGenericUrl, city);
            websiteDataFetcher.getOtodomRoomsOffersData(otodomRoomsOffersGenericUrl, city);
        });
    }
}
