package ep2024.bwV.services;

import com.opencsv.CSVReader;
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

}
