package net.kodinet.kodinet.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class FingerprintObject {


    private String personId;
    private String fingerprint;
}
