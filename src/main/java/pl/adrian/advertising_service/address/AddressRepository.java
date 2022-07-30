package pl.adrian.advertising_service.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressesByIdIn(List<Long> ids);
}
