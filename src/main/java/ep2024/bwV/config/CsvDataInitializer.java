package ep2024.bwV.config;

import ep2024.bwV.services.CsvService;
import ep2024.bwV.repositories.ProvinciaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvDataInitializer {

    @Autowired
    private CsvService csvService;
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @PostConstruct
    public void initializeCsvData() {
        try {
            csvService.readProvince("data/province-italiane.csv");
            csvService.readComuni("data/comuni-italiani.csv");
            System.out.println("CSV files loaded successfully");
        } catch (Exception e) {
            System.out.println("An error occurred during initialization: " + e.getMessage());
        }
    }
}
