package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.AssetsCategoriesTaxes;
import net.kodinet.kodinet.models.AssetsCategoriesTaxesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetsCategoriesTaxesRepository extends JpaRepository<AssetsCategoriesTaxes, AssetsCategoriesTaxesId> {
}
