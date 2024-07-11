package ep2024.bwV.controllers;

import ep2024.bwV.entities.Comune;
import ep2024.bwV.entities.Provincia;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.repositories.ProvinciaRepository;
import ep2024.bwV.services.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {
    @Autowired
    private CsvService csvService;

    @GetMapping("/{nome}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Comune> getCityByProvince(@PathVariable String nome) {
        Provincia provincia = csvService.getProvinceByName(nome);
        return provincia.getComuni();
    }

    @GetMapping("/{nome}/{nomeComune}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Comune getCityByProvinceAndName(@PathVariable String nome, @PathVariable String nomeComune) {
        String normalizedNomeProvincia = csvService.normalizeString(nome);
        String normalizedNomeComune = csvService.normalizeString(nomeComune);
        Provincia provincia = csvService.getProvinceByName(normalizedNomeProvincia);
        return provincia.getComuni().stream()
                .filter(comune -> csvService.normalizeString(comune.getNome()).equals(normalizedNomeComune))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Comune with name " + nomeComune + " not found"));
    }
}
