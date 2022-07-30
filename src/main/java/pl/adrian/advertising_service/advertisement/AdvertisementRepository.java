package pl.adrian.advertising_service.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

//    public List<Advertisement> findAdvertisementsByCategoryIdIn(List<Long> categoryIds);

    List<Advertisement> findAdvertisementsByCategoryId(Long id);
}
