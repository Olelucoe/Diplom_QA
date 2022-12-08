package Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Card {
    private String number;
    private String month;
    private String year;
    private String holder;
    private String cvc;
}
