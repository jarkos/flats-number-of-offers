import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Log4j2
class WebsiteDataFetcher {

    void getOlxOffersData(String url, String cityId) {
        try {
            Document doc = Jsoup.connect(url + cityId).get();
            String rentOffers = doc.getElementsByClass("counter nowrap").first().childNode(0).toString();
            String sellOffers = doc.getElementsByClass("counter nowrap").get(3).childNode(0).toString();
            log.info("Rent " + cityId + " " + rentOffers);
            log.info("Sell " + cityId + " " + sellOffers);
        } catch (IOException ioe) {
            log.info("IOException happened");
            ioe.printStackTrace();
        } catch (Exception e) {
            log.info("Some exception happened!");
            e.printStackTrace();
        }
    }

    void getOtodomOffersData(String url, String cityId) {
        try {
            var sellUrl = url.replace("PLACEHOLDER_TO_REPLACE", "sprzedaz");
            var rentUrl = url.replace("PLACEHOLDER_TO_REPLACE", "wynajem");
            Document docSell = Jsoup.connect(sellUrl + cityId).get();
            Document docRent = Jsoup.connect(rentUrl + cityId).get();
            String sellOffers = docSell.getElementsByClass("offers-index pull-left text-nowrap").first()
                    .childNode(1).childNode(0).toString();
            String rentOffers = docRent.getElementsByClass("offers-index pull-left text-nowrap").first()
                    .childNode(1).childNode(0).toString();
            log.info("Rent " + cityId + " " + rentOffers);
            log.info("Sell " + cityId + " " + sellOffers);
        } catch (IOException ioe) {
            log.info("IOException happened");
            ioe.printStackTrace();
        } catch (Exception e) {
            log.info("Some exception happened!");
            e.printStackTrace();
        }
    }

}
