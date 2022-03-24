package com.server.whaledone.country;

import com.server.whaledone.country.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByCountryName(String name);

    Optional<Country> findByCountryCode(String code);
}
