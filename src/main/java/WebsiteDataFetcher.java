import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
class WebsiteDataFetcher {

    void getOlxFlatsOffersData(String url, String cityId) {
        try {
            Document doc = Jsoup.connect(url + cityId).get();
            String rentOffers = doc.getElementsByClass("counter nowrap").first().childNode(0).toString();
            String sellOffers = doc.getElementsByClass("counter nowrap").get(3).childNode(0).toString();
            log.info("Rent flat" + cityId + " " + rentOffers);
            log.info("Sell flat" + cityId + " " + sellOffers);
        } catch (IOException ioe) {
            log.info("IOException happened");
            ioe.printStackTrace();
        } catch (Exception e) {
            log.info("Some exception happened!");
            e.printStackTrace();
        }
    }

    void getOlxRoomsOffersData(String url, String cityId) {
        try {
            Document doc = Jsoup.connect(url + cityId).get();
            AtomicInteger sum = new AtomicInteger();
            doc.getElementsByClass("locationlinks margintop10").first()
                    .childNodes().stream().filter(node -> !node.toString().equals(" ")).forEach(node -> node.childNodes()
                    .stream().filter(subnode -> !subnode.toString().equals(" ")).forEach(subnode -> {
                        var resultOfDistrict = subnode.childNode(1).toString()
                                .replace("&nbsp;(", "").replace(")", "");
                        sum.addAndGet(Integer.valueOf(resultOfDistrict));
                    }));
            log.info("ROOMS " + cityId + " " + sum);
        } catch (IOException ioe) {
            log.info("IOException happened");
            ioe.printStackTrace();
        } catch (Exception e) {
            log.info("Some exception happened!");
            e.printStackTrace();
        }
    }

    void getOtodomFlatsOffersData(String url, String cityId) {
        try {
            var sellUrl = url.replace("PLACEHOLDER_TO_REPLACE", "sprzedaz");
            var rentUrl = url.replace("PLACEHOLDER_TO_REPLACE", "wynajem");
            Document docSell = Jsoup.connect(sellUrl + cityId).get();
            Document docRent = Jsoup.connect(rentUrl + cityId).get();
            String sellOffers = docSell.getElementsByClass("offers-index pull-left text-nowrap").first()
                    .childNode(1).childNode(0).toString();
            String rentOffers = docRent.getElementsByClass("offers-index pull-left text-nowrap").first()
                    .childNode(1).childNode(0).toString();
            log.info("Rent flat otodom " + cityId + rentOffers);
            log.info("Sell flat otodom " + cityId + sellOffers);
        } catch (IOException ioe) {
            log.info("IOException happened");
            ioe.printStackTrace();
        } catch (Exception e) {
            log.info("Some exception happened!");
            e.printStackTrace();
        }
    }

    void getOtodomRoomsOffersData(String url, String cityId) {
        try {
            Document docRent = Jsoup.connect(url + cityId).get();
            String rentOffers = docRent.getElementsByClass("offers-index pull-left text-nowrap").first()
                    .childNode(1).childNode(0).toString();
            log.info("Rent room otodom " + cityId + " " + rentOffers);
        } catch (IOException ioe) {
            log.info("IOException happened");
            ioe.printStackTrace();
        } catch (Exception e) {
            log.info("Some exception happened!");
            e.printStackTrace();
        }
    }

}
