import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "offer_history", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "city"})})
public class OfferHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate date;
    private String city;
    private int olxRentFlatOfferNumber;
    private int olxSellFlatOfferNumber;
    private int otodomRentFlatOfferNumber;
    private int otodomSellFlatOfferNumber;
    private int olxRoomOfferNumber;
    private int otodomRoomOfferNumber;

}
