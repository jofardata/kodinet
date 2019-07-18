package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Collection<Asset>findByAssetCategoryId(Long id);
    Collection<Asset>findByPersonId(Long id);

}
