import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.ResourceBundle;

@Log4j2
public class FlatsOffersNumberInitializer {

    private static ResourceBundle rb = ResourceBundle.getBundle("app");
    private static WebsiteDataFetcher websiteDataFetcher = new WebsiteDataFetcher();

    public static void main(String[] args) {
        if (Boolean.valueOf(rb.getString("aggregation.data.enabled"))) {
            String olxFlatsGenericUrl= rb.getString("olx.real.estate.flats.url");
            String otodomFlatsSellGenericUrl= rb.getString("otodom.real.estate.flats.url");

            websiteDataFetcher.getOlxOffersData(olxFlatsGenericUrl, "lodz");
            websiteDataFetcher.getOtodomOffersData(otodomFlatsSellGenericUrl, "lodz");
            websiteDataFetcher.getOlxOffersData(olxFlatsGenericUrl, "warszawa");
            websiteDataFetcher.getOtodomOffersData(otodomFlatsSellGenericUrl, "warszawa");
            websiteDataFetcher.getOlxOffersData(olxFlatsGenericUrl, "poznan");
            websiteDataFetcher.getOtodomOffersData(otodomFlatsSellGenericUrl, "poznan");
            websiteDataFetcher.getOlxOffersData(olxFlatsGenericUrl, "krakow");
            websiteDataFetcher.getOtodomOffersData(otodomFlatsSellGenericUrl, "krakow");
            websiteDataFetcher.getOlxOffersData(olxFlatsGenericUrl, "gdansk");
            websiteDataFetcher.getOtodomOffersData(otodomFlatsSellGenericUrl, "gdansk");
            websiteDataFetcher.getOlxOffersData(olxFlatsGenericUrl, "wroclaw");
            websiteDataFetcher.getOtodomOffersData(otodomFlatsSellGenericUrl, "wroclaw");
            websiteDataFetcher.getOlxOffersData(olxFlatsGenericUrl, "szczecin");
            websiteDataFetcher.getOtodomOffersData(otodomFlatsSellGenericUrl, "szczecin");
            websiteDataFetcher.getOlxOffersData(olxFlatsGenericUrl, "bydgoszcz");
            websiteDataFetcher.getOtodomOffersData(otodomFlatsSellGenericUrl, "bydgoszcz");
            websiteDataFetcher.getOlxOffersData(olxFlatsGenericUrl, "lublin");
            websiteDataFetcher.getOtodomOffersData(otodomFlatsSellGenericUrl, "lublin");
            websiteDataFetcher.getOlxOffersData(olxFlatsGenericUrl, "katowice");
            websiteDataFetcher.getOtodomOffersData(otodomFlatsSellGenericUrl, "katowice");




        }
        log.warn("Finished apartments stats fetch for: " + LocalDate.now());
    }

}
