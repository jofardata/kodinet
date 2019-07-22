package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Asset;
import net.kodinet.kodinet.entities.AssetCategory;
import net.kodinet.kodinet.entities.Person;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AssetCategoryRepository;
import net.kodinet.kodinet.repositories.AssetRepository;
import net.kodinet.kodinet.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assets")
@CrossOrigin(origins = "*")
public class AssetController {

    @Autowired
    AssetRepository assetRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    AssetCategoryRepository categoryRepository;
    ApiResponse apiResponse = new ApiResponse();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create/{bdnNumber}/{categoryId}")
    public ResponseEntity<?>create(@RequestBody Asset asset,
                                   @PathVariable String bdnNumber,
                                   @PathVariable Long categoryId){
        Person person = personRepository.findByBdnIdOrNationalIdOrPhoneOrRfid(bdnNumber,"","","");
        AssetCategory category = categoryRepository.getOne(categoryId);
        asset.setPerson(person);
        asset.setCreationDate(System.currentTimeMillis());
        asset.setCreatedOn(new Date());
        asset.setAssetCategory(category);
        Asset created= assetRepository.save(asset);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Asset created");
        apiResponse.setData(created);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @GetMapping("/find-one")
    public ResponseEntity<?>findOne(@PathVariable Long id){
        Asset asset=assetRepository.getOne(id);
        apiResponse.setResponseCode("00");
        apiResponse.setData(asset);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-category/{id}")
    public ResponseEntity<?>findByCategory(@PathVariable Long id){
        Collection<Asset> assets=assetRepository.findByAssetCategoryId(id);
        apiResponse.setResponseCode("00");
        apiResponse.setData(assets);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-person/{id}")
    public ResponseEntity<?>findByPerson(@PathVariable Long id){
        Collection<Asset> assets=assetRepository.findByPersonId(id);
        apiResponse.setResponseCode("00");
        apiResponse.setData(assets);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?>findAll(){
        List<Asset> assets=assetRepository.findAll();
        apiResponse.setResponseCode("00");
        apiResponse.setData(assets);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-vehicle/{chassis-or-numberplate}")
    public Map<String,Object> findVehicle(@PathVariable("chassis-or-numberplate") String number){
        return jdbcTemplate.queryForMap("select persons.bdn_id," +
                "case when type='Physique' then concat(persons.first_name, ' ',persons.middle_name,' ',persons.last_name) else persons.company_name end as name," +
                "persons.phone," +
                "persons.type," +
                "assets.number_plate," +
                "assets.chassis," +
                "assets.mark," +
                "assets.body," +
                "assets.vehicle_type," +
                "assets.vehicle_category," +
                "assets.capacity," +
                "assets.unity," +
                "case when assets.vignette=true then (select combination.vignette from combination where combination.person=persons.type and combination.category=assets.vehicle_category and (assets.capacity>=seriesfrom and assets.capacity<seriesto)  and combination.vignette!=0 limit 1) else 0 end as vignette," +
                "case when assets.tscr=true then (select combination.tscr from combination where combination.person=persons.type and combination.category=assets.vehicle_category and (assets.capacity>=seriesfrom and assets.capacity<seriesto)  and combination.tscr!=0 limit 1) else 0 end as tscr," +
                "case when assets.atbp=true then (select combination.atbp from combination where combination.person=persons.type and combination.category=assets.vehicle_category and (assets.capacity>=seriesfrom and assets.capacity<seriesto)  and combination.atbp!=0 limit 1) else 0 end as atbp," +
                "case when assets.cct=true then (select combination.cct from combination where combination.person=persons.type and combination.category=assets.vehicle_category and (assets.capacity>=seriesfrom and assets.capacity<seriesto)  and combination.cct!=0 limit 1) else 0 end as cct " +
                "from persons " +
                "inner join assets on persons.id = assets.person_id " +
                "inner join assets_categories on assets.asset_category_id = assets_categories.id " +
                "WHERE assets.number_plate='" + number + "' or assets.chassis='" + number + "'");
    }

}
