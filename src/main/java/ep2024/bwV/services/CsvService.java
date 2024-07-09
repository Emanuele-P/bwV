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
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
                    case "Pesaro-Urbino":
                        provinciaCsv.setNome("Pesaro e Urbino");
                        break;
                    case "Ascoli-Piceno":
                        provinciaCsv.setNome("Ascoli Piceno");
                        break;
                    case "Forli-Cesena":
                        provinciaCsv.setNome("Forlì-Cesena");
                        break;
                    case "Verbania":
                        provinciaCsv.setNome("Verbano-Cusio-Ossola");
                        break;
                    case "Reggio-Calabria":
                        provinciaCsv.setNome("Reggio Calabria");
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
                Provincia p = new Provincia("Monza e della Brianza", "MB");
                provinciaRepository.save(p);
            });
            provinciaRepository.findByNome("Vibo-Valentia").ifPresent(provincia ->{
                provinciaRepository.delete(provincia);
                Provincia p = new Provincia("Vibo Valentia", "VV");
                provinciaRepository.save(p);
            });
            provinciaRepository.findByNome("La-Spezia").ifPresent(provincia ->{
                provinciaRepository.delete(provincia);
                Provincia p = new Provincia("La Spezia", "SP");
                provinciaRepository.save(p);
            });
            provinciaRepository.findByNome("Reggio-Emilia").ifPresent(provincia ->{
                provinciaRepository.delete(provincia);
                Provincia p = new Provincia("Reggio Emilia", "RE");
                provinciaRepository.save(p);
            });
            provinciaRepository.findByNome("Carbonia Iglesias").ifPresent(provincia ->{
                provinciaRepository.delete(provincia);
                Provincia p = new Provincia("Sud Sardegna", "SU");
                provinciaRepository.save(p);
            });
            provinciaRepository.findByNome("Medio Campidano").ifPresent(provincia ->{
                provinciaRepository.delete(provincia);
            });

            List<ComuneCsv> comuneCsvList = csvToBean.parse();
            for (ComuneCsv comuneCsv : comuneCsvList) {
                switch (comuneCsv.getNomeProvincia()) {
                    case "Bolzano/Bozen":
                        comuneCsv.setNomeProvincia("Bolzano");
                        break;
                    case "Valle d'Aosta/Vallée d'Aoste":
                        comuneCsv.setNomeProvincia("Aosta");
                        break;
                    case "Reggio nell'Emilia":
                        comuneCsv.setNomeProvincia("Reggio Emilia");
                        break;
                    default:
                        break;
                }

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
        String normalizedNome = normalizeString(nome);
        List<Provincia> province = provinciaRepository.findAll();

        for (Provincia provincia : province) {
            if (normalizeString(provincia.getNome()).equals(normalizedNome)) {
                return provincia;
            }
        }

        throw new NotFoundException("Province with name " + nome + " not found");
    }

    public String normalizeString(String input) {
        if (input == null) {
            return null;
        }
        Pattern diacritical = Pattern.compile("\\p{M}");
        Pattern unwantedChar = Pattern.compile("[\\s'-]");
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFKD);
        String noAccents = diacritical.matcher(normalized).replaceAll("");

        return unwantedChar.matcher(noAccents).replaceAll("").toLowerCase();
    }
}