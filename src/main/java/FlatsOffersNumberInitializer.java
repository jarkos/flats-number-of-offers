import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Set;

@Log4j2
public class FlatsOffersNumberInitializer {

    private static ResourceBundle rb = ResourceBundle.getBundle("app");
    private static WebsiteDataFetcher websiteDataFetcher = new WebsiteDataFetcher();
    private static OfferHistoryRepository offerHistoryRepository = new OfferHistoryRepository();
    private static Set<String> bigCities = Set.of("warszawa", "krakow", "lodz", "poznan", "gdansk", "wroclaw", "szczecin",
            "katowice");

    public static void main(String[] args) {
        if (Boolean.valueOf(rb.getString("aggregation.data.enabled"))) {
            String olxFlatsOffersGenericUrl = rb.getString("olx.real.estate.flats.url");
            String olxRoomsOffersGenericUrl = rb.getString("olx.real.estate.rooms.url");
            String otodomFlatsOffersGenericUrl = rb.getString("otodom.real.estate.flats.url");
            String otodomRoomsOffersGenericUrl = rb.getString("otodom.real.estate.rooms.url");
            bigCities.forEach(city -> {
                websiteDataFetcher.getOlxFlatsOffersData(olxFlatsOffersGenericUrl, city);
                websiteDataFetcher.getOtodomFlatsOffersData(otodomFlatsOffersGenericUrl, city);
                websiteDataFetcher.getOlxRoomsOffersData(olxRoomsOffersGenericUrl, city);
                websiteDataFetcher.getOtodomRoomsOffersData(otodomRoomsOffersGenericUrl, city);
            });
        }
        log.warn("Finished apartments stats fetch for: " + LocalDate.now());
    }
}
