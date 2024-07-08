package ep2024.bwV;

import ep2024.bwV.services.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CsvRunner implements CommandLineRunner {

    @Autowired
    private CsvService csvService;

    @Override
    public void run(String... args) throws Exception {
        csvService.readProvince("src/main/resources/data/province-italiane.csv");
        csvService.readComuni("src/main/resources/data/comuni-italiani.csv");
    }
}
