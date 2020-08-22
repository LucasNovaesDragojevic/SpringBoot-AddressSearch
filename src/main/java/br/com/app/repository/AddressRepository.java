package br.com.app.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.app.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>
{
	Optional<Address> findByLatitude(BigDecimal latitude);
}
