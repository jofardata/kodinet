package net.kodinet.kodinet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spatial4j.core.shape.jts.JtsGeometry;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "assets")
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private AssetCategory assetCategory;
    @ManyToOne
    private Person person;
    private String name;
    private double latitude;
    private double longitude;
    //private JtsGeometry geometry;
    private String avenue;
    private String compoundName;
    private String numberPlate;
    private String chassis;
    private String body;
    private String mark;
    private String vehicleType;
    private double capacity;
    private String image;
    private Date createdOn;
    private Long creationDate;
    private String vehicleCategory;
    private String unity;
    @JsonIgnore
    private boolean vignette;
    @JsonIgnore
    private boolean tscr;
    @JsonIgnore
    private boolean atbp;
    @JsonIgnore
    private boolean cct;
}
