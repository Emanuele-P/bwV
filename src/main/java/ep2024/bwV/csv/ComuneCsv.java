package ep2024.bwV.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComuneCsv {

    @CsvBindByPosition(position = 0)
    private String codiceProvincia;

    @CsvBindByPosition(position = 1)
    private String codiceComune;

    @CsvBindByPosition(position = 2)
    private String nomeComune;

    @CsvBindByPosition(position = 3)
    private String nomeProvincia;
}
