package net.kodinet.kodinet.repositories;

import net.kodinet.kodinet.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Collection<Asset>findByAssetCategoryId(Long id);
    Collection<Asset>findByPersonId(Long id);
    @Query(value = "select count(*) from assets", nativeQuery = true)
    int findCount();

}
