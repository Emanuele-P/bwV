package ep2024.bwV.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import ep2024.bwV.csv.ComuneCsv;
import ep2024.bwV.csv.ProvinciaCsv;
import ep2024.bwV.entities.Comune;
import ep2024.bwV.entities.Provincia;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.repositories.ComuneRepository;
import ep2024.bwV.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class CsvService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ComuneRepository comuneRepository;

    public void readProvince(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(filePath).getInputStream(), StandardCharsets.UTF_8))) {
            CsvToBean<ProvinciaCsv> csvToBean = new CsvToBeanBuilder<ProvinciaCsv>(reader)
                    .withType(ProvinciaCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .build();

            List<ProvinciaCsv> provinciaCsvList = csvToBean.parse();
            for (ProvinciaCsv provinciaCsv : provinciaCsvList) {
                Optional<Provincia> existingProvincia = provinciaRepository.findByNome(provinciaCsv.getNome());

                switch (provinciaCsv.getNome()) {
                    case "Roma":
                        provinciaCsv.setSigla("RM");
                        break;
                    case "Pesaro e Urbino":
                        provinciaCsv.setSigla("PU");
                        break;
                    case "La Spezia":
                        provinciaCsv.setSigla("SP");
                        break;
                    case "Monza e della Brianza":
                        provinciaCsv.setSigla("MB");
                        break;
                    case "Bolzano/Bozen":
                        provinciaCsv.setNome("Bolzano");
                        provinciaCsv.setSigla("BZ");
                        break;
                    case "Verbano-Cusio-Ossola":
                        provinciaCsv.setSigla("VB");
                        break;
                    default:
                        break;
                }

                if (existingProvincia.isEmpty()) {
                    Provincia provincia = new Provincia(provinciaCsv.getNome(), provinciaCsv.getSigla());
                    provinciaRepository.save(provincia);
                    System.out.println("Saved province: " + provincia.getNome());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readComuni(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(filePath).getInputStream(), StandardCharsets.UTF_8))) {
            CsvToBean<ComuneCsv> csvToBean = new CsvToBeanBuilder<ComuneCsv>(reader)
                    .withType(ComuneCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .build();

            provinciaRepository.findByNome("Monza-Brianza").ifPresent(provincia -> {
                provinciaRepository.delete(provincia);
                System.out.println("Deleted province: Monza-Brianza");

                Provincia newProvincia = new Provincia("Monza e della Brianza", "MB");
                provinciaRepository.save(newProvincia);
                System.out.println("Saved new province: Monza e della Brianza with sigla MB");
            });

            List<ComuneCsv> comuneCsvList = csvToBean.parse();
            for (ComuneCsv comuneCsv : comuneCsvList) {
                Optional<Provincia> optionalProvincia = provinciaRepository.findByNome(comuneCsv.getNomeProvincia());
                Provincia provincia = optionalProvincia.orElseGet(() -> {
                    Provincia newProvincia = new Provincia(comuneCsv.getNomeProvincia(), comuneCsv.getCodiceProvincia());
                    provinciaRepository.save(newProvincia);
                    System.out.println("Saved new province for comune: " + newProvincia.getNome());
                    return newProvincia;
                });

                Comune comune = new Comune(comuneCsv.getNomeComune(), provincia);
                comuneRepository.save(comune);
                System.out.println("Saved comune: " + comune.getNome());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Provincia getProvinceByName(String nome) {
        return provinciaRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Province with name " + nome + " not found"));
    }
}