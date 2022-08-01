package pl.adrian.advertising_service.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query("SELECT a FROM Advertisement a LEFT JOIN FETCH a.address ad LEFT JOIN FETCH a.category c WHERE" +
            " category_id = ?1")
    List<Advertisement> findAdvertisementsByCategoryId(Long id);

    @Query("SELECT a FROM Advertisement a LEFT JOIN FETCH a.address ad LEFT JOIN FETCH a.category c WHERE" +
            " category_id = ?1")
    List<Advertisement> findAdvertisementsByCategoryId(Long id, Pageable pageable);

    @Query("SELECT a FROM Advertisement a LEFT JOIN FETCH a.address ad LEFT JOIN FETCH a.category c WHERE" +
            " a.id = ?1")
    Advertisement findAdvertisementsById(Long id);

    @Query("SELECT a FROM Advertisement a LEFT JOIN FETCH a.address ad LEFT JOIN FETCH a.category c")
    List<Advertisement> findAllAdvertisements();

    @Modifying
    @Query("UPDATE Advertisement a SET a.category = null WHERE category_id = ?1")
    void setNullForAdvertisementsCategoryIdByCategoryId(Long id);

    Long countAdvertisementsByCategoryId(Long id);
}
