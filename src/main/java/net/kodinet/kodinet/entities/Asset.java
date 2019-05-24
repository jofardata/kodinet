package net.kodinet.kodinet.entities;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity(name = "assets")
public class Asset {


    private Long id;
    private String name;
    private Long latitude;
    private Long longitude;
//    private
}
