package pl.jarkos;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
@SpringBootApplication
public class FlatsOffersNumberInitializer {

    private static ResourceBundle rb = ResourceBundle.getBundle("app");
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        SpringApplication.run(FlatsOffersNumberInitializer.class, args);
        if (Boolean.valueOf(rb.getString("aggregation.data.enabled"))) {
            scheduler.scheduleAtFixedRate(new OfferFetcherTask(), 0, 12, TimeUnit.HOURS);
        }
        log.warn("Started at: " + LocalDate.now());
    }

}
