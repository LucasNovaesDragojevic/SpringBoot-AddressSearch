package br.com.app.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.app.model.Address;
import br.com.app.repository.AddressRepository;

@Transactional
@RestController
@RequestMapping("address")
public class AppController 
{
	@Autowired
	private AddressRepository addressRepository;
	
	@GetMapping
	public Page<Address> list(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC) Pageable pagination)
	{
		return addressRepository.findAll(pagination);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Address> detail(@PathVariable Long id)
	{
		Optional<Address> address = addressRepository.findById(id);
		if (address.isPresent())
			return ResponseEntity.ok(address.get());
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/latitude/{latitude}/longitude/{longitude}")
	public ResponseEntity<Address> getByLatitudeLongitude(@PathVariable BigDecimal latitude, @PathVariable BigDecimal longitude)
	{
		Optional<Address> address = addressRepository.findByLatitude(latitude);
		if (address.isPresent())
			return ResponseEntity.ok(address.get());
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Address> add(@RequestBody Address address, UriComponentsBuilder uriComponentsBuilder)
	{
		
		addressRepository.save(address);
		URI uri = uriComponentsBuilder.path("address/{id}").buildAndExpand(address.getId()).toUri(); 
		return ResponseEntity.created(uri).body(address);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Address> update(@PathVariable Long id, @RequestBody Address addressNew)
	{
		Optional<Address> addressStored = addressRepository.findById(id);
		if (addressStored.isPresent())
		{
			Address addressUpdated = addressStored.get();
			addressUpdated.setStreetName(addressNew.getStreetName());
			addressUpdated.setNumber(addressNew.getNumber());
			addressUpdated.setComplement(addressNew.getComplement());
			addressUpdated.setNeighbourhood(addressNew.getNeighbourhood());
			addressUpdated.setCity(addressNew.getCity());
			addressUpdated.setState(addressNew.getState());
			addressUpdated.setCountry(addressNew.getCountry());
			addressUpdated.setZipcode(addressNew.getZipcode());
			addressUpdated.setLatitude(addressNew.getLatitude());
			addressUpdated.setLongitude(addressNew.getLongitude());
			return ResponseEntity.ok(addressUpdated);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> remove(@PathVariable Long id)
	{
		Optional<Address> address = addressRepository.findById(id);
		if (address.isPresent())
		{
			addressRepository.delete(address.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
