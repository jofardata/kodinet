package net.kodinet.kodinet.entities;

import lombok.Data;
import net.kodinet.kodinet.models.AssetsCategoriesTaxesId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@Data
@IdClass(AssetsCategoriesTaxesId.class)
public class AssetsCategoriesTaxes {
    @Id
    private Long IdAssetCat;
    @Id
    private String IdTax;

    @ManyToOne
    private AssetCategory assetCategory;

    @ManyToOne
    private Tax tax;
    private String rate;
    private double fixedAmount;
}
