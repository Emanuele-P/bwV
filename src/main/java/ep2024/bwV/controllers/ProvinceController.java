package ep2024.bwV.controllers;

import ep2024.bwV.entities.Comune;
import ep2024.bwV.entities.Provincia;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.repositories.ProvinciaRepository;
import ep2024.bwV.services.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/province")
public class ProvinceController {
    @Autowired
    private CsvService csvService;

    @GetMapping("/{nome}/cities")
    public List<Comune> getCityByProvince(@PathVariable String nome) {
        Provincia provincia = csvService.getProvinceByName(nome);
        return provincia.getComuni();
    }
}
