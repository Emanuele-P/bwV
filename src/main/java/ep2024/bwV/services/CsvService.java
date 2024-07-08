package ep2024.bwV.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ep2024.bwV.entities.Comune;
import ep2024.bwV.entities.Provincia;
import ep2024.bwV.repositories.ComuneRepository;
import ep2024.bwV.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class CsvService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ComuneRepository comuneRepository;

    public void readProvince(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 3) {
                    String sigla = nextLine[0];
                    String nome = nextLine[1];
                    String regione = nextLine[2];
                    Provincia provincia = new Provincia(nome, sigla);
                    provinciaRepository.save(provincia);
                    System.out.println("Saved province: " + provincia.getNome());
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void readComuni(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 4) {
                    String codiceProvincia = nextLine[0];
                    String codiceComune = nextLine[1];
                    String nomeComune = nextLine[2];
                    String nomeProvincia = nextLine[3];

                    Optional<Provincia> optionalProvincia = provinciaRepository.findBySigla(codiceProvincia);
                    Provincia provincia = optionalProvincia.orElseGet(() -> {
                        Provincia newProvincia = new Provincia(nomeProvincia, codiceProvincia);
                        provinciaRepository.save(newProvincia);
                        return newProvincia;
                    });

                    Comune comune = new Comune(nomeComune, provincia);
                    comuneRepository.save(comune);
                    System.out.println("Saved comune: " + comune.getNome());
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
