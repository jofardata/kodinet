package net.kodinet.kodinet.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "PERSONS")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String bdnId;
    private String nationalId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private Date dob;
    private String email;
    @Column(unique = true)
    private String phone;
    private String phone2;
    private String phone3;
    private String image;
    private byte[] fingerprint;
    private String companyName;
    private String initials;
    private String rccm;
    private String pobox;
    private String type;
    private String registrationType;

}
