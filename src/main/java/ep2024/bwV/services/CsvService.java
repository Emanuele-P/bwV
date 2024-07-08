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
                if (nextLine.length >= 2) {
                    String sigla = nextLine[0];
                    String nome = nextLine[1];
                    Provincia provincia = new Provincia(nome, sigla);
                    provinciaRepository.save(provincia);
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

                    Provincia provincia = provinciaRepository.findBySigla(codiceProvincia);
                    if (provincia == null) {
                        provincia = new Provincia(nomeProvincia, codiceProvincia);
                        provinciaRepository.save(provincia);
                    }

                    Comune comune = new Comune(nomeComune, provincia);
                    comuneRepository.save(comune);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
