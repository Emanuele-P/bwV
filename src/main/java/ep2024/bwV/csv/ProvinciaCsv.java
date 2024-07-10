package ep2024.bwV.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProvinciaCsv {

    @CsvBindByPosition(position = 0)
    private String sigla;

    @CsvBindByPosition(position = 1)
    private String nome;

    @CsvBindByPosition(position = 2)
    private String regione;
}
