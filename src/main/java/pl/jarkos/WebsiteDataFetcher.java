package pl.jarkos;

import lombok.extern.log4j.Log4j2;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
class WebsiteDataFetcher {

    private OfferHistoryRepository offerHistoryRepository = new OfferHistoryRepository();

    void getOlxFlatsOffersData(String url, String cityId) {
        try {
            Document doc = Jsoup.connect(url + cityId).get();
            String rentOffers = doc.getElementsByClass("counter nowrap").first().childNode(0).toString();
            String sellOffers = doc.getElementsByClass("counter nowrap").get(3).childNode(0).toString();
            log.info("Rent flat" + cityId + " " + rentOffers);
            log.info("Sell flat" + cityId + " " + sellOffers);
            createNewEntry(cityId, rentOffers.replace(" ",""), sellOffers.replace(" ",""));
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
            AtomicInteger sumRoomOffers = new AtomicInteger();
            doc.getElementsByClass("locationlinks margintop10").first()
                    .childNodes().stream().filter(node -> !node.toString().equals(" ")).forEach(node -> node.childNodes()
                    .stream().filter(subnode -> !subnode.toString().equals(" ")).forEach(subnode -> {
                        var resultOfDistrict = subnode.childNode(1).toString()
                                .replace("&nbsp;(", "").replace(")", "")
                                .replace("&nbsp;", "");
                        sumRoomOffers.addAndGet(Integer.valueOf(resultOfDistrict));
                    }));
            log.info("ROOMS " + cityId + " " + sumRoomOffers);
            updateOlxRoomParams(cityId, sumRoomOffers);
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
            updateOtodomFlatParams(cityId, sellOffers.replace(" ",""), rentOffers.replace(" ",""));

        } catch (SocketTimeoutException | HttpStatusException ste) {
            ste.printStackTrace();
            log.info("Connection/timeout exception happened. Retry.");
            getOtodomFlatsOffersData(url, cityId);
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
            updateOtodomRoomParams(cityId, rentOffers.replace(" ",""));
        } catch (IOException ioe) {
            log.info("IOException happened");
            ioe.printStackTrace();
        } catch (Exception e) {
            log.info("Some exception happened!");
            e.printStackTrace();
        }
    }

    private void createNewEntry(String cityId, String rentOffers, String sellOffers) {
        OfferHistory oh = new OfferHistory();
        oh.setCity(cityId);
        oh.setDate(LocalDate.now());
        oh.setOlxRentFlatOfferNumber(Integer.valueOf(rentOffers));
        oh.setOlxSellFlatOfferNumber(Integer.valueOf(sellOffers));
        offerHistoryRepository.saveOrUpdate(oh);
    }

    private void updateOtodomFlatParams(String cityId, String sellOffers, String rentOffers) {
        OfferHistory oh = offerHistoryRepository.get(cityId, LocalDate.now());
        oh.setOtodomRentFlatOfferNumber(Integer.valueOf(rentOffers));
        oh.setOtodomSellFlatOfferNumber(Integer.valueOf(sellOffers));
        offerHistoryRepository.saveOrUpdate(oh);
    }

    private void updateOlxRoomParams(String cityId, AtomicInteger sumRoomOffers) {
        OfferHistory oh = offerHistoryRepository.get(cityId, LocalDate.now());
        oh.setOlxRoomOfferNumber(Integer.valueOf(sumRoomOffers.toString()));
        offerHistoryRepository.saveOrUpdate(oh);
    }

    private void updateOtodomRoomParams(String cityId, String rentOffers) {
        OfferHistory oh = offerHistoryRepository.get(cityId, LocalDate.now());
        oh.setOtodomRoomOfferNumber(Integer.valueOf(rentOffers));
        offerHistoryRepository.saveOrUpdate(oh);
    }

}
