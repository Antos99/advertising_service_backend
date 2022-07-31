package pl.adrian.advertising_service.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAdvertisementsByCategoryId(Long id, Pageable pageable);
    List<Advertisement> findAdvertisementsByCategoryId(Long id);
    Long countAdvertisementsByCategoryId(Long id);
}
