package pl.jarkos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OfferController {

    @Autowired
    private
    OfferHistoryRepository offerHistoryRepository;
    private static final List labeles = List.of("OlxSellFlats", "OtodomSellFlats","OlxRentFlats", "OtodomRentFlats",
            "OlxRooms", "OtodomRooms");

    @RequestMapping("/stats")
    public String getAllOfferStatsByCity(@RequestParam(value = "size", required = false, defaultValue = "30") int requestedSizeList,
                                         @RequestParam(value = "city", required = false, defaultValue = "lodz") String city,
                                         Model model) {

        List<OfferHistory> allByCity = offerHistoryRepository.getByCity(city);
        List<List<String>> dataTableToPresent = new ArrayList<>();
        allByCity.forEach(record -> {
            var values = List.of(String.valueOf(record.getDate().toEpochSecond(LocalTime.now(), ZoneOffset.UTC)*1000),
                    String.valueOf(record.getOlxSellFlatOfferNumber()),
                    String.valueOf(record.getOtodomSellFlatOfferNumber()),
                    String.valueOf(record.getOlxRentFlatOfferNumber()),
                    String.valueOf(record.getOtodomRentFlatOfferNumber()),
                    String.valueOf(record.getOlxRoomOfferNumber()),
                    String.valueOf(record.getOtodomRoomOfferNumber()));
            dataTableToPresent.add(values);
        });

        List<List<String>> limitedList = dataTableToPresent.subList(dataTableToPresent.size() - requestedSizeList, dataTableToPresent.size());
        model.addAttribute("indicators", limitedList);
        model.addAttribute("labels", labeles);
        return "stats";
    }

}
