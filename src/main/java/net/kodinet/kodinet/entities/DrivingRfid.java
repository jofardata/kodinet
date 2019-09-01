package net.kodinet.kodinet.entities;


import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "drivingrfid")
public class DrivingRfid
{

    @Id
    private String rfid;
    private Date dateLivraison;
}
