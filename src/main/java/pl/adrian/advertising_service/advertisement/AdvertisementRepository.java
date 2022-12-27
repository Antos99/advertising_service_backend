package pl.adrian.advertising_service.advertisement;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query("SELECT a FROM Advertisement a LEFT JOIN FETCH a.address ad LEFT JOIN FETCH a.category c WHERE" +
            " a.category.name = ?1")
    List<Advertisement> findAdvertisementsByCategoryName(String categoryName, Pageable pageable);

    @Query("SELECT a FROM Advertisement a LEFT JOIN FETCH a.address ad LEFT JOIN FETCH a.category c WHERE" +
            " a.id = ?1")
    Optional<Advertisement> findAdvertisementsById(Long id);

    @Query("SELECT a FROM Advertisement a LEFT JOIN FETCH a.address ad LEFT JOIN FETCH a.category c")
    List<Advertisement> findAllAdvertisements(Pageable pageable);

    @Modifying
    @Query("UPDATE Advertisement a SET a.category = null WHERE a.category.name = ?1")
    void setNullForAdvertisementsCategoryNameByCategoryName(String name);

    @Query("SELECT COUNT(a) FROM Advertisement a WHERE a.category.name = ?1")
    Long countAdvertisementsByCategoryName(String name);

    @Query("SELECT a FROM Advertisement a LEFT JOIN FETCH a.address ad LEFT JOIN FETCH a.category c WHERE" +
            " a.user.username = ?1")
    List<Advertisement> findAdvertisementsByUsername(String username, Pageable pageable);
}
