package io.munib.covid19impactanalysisapi.security.repository;


import org.springframework.data.repository.CrudRepository;

import io.munib.covid19impactanalysisapi.security.models.MyUserDetails;

public interface MyUserDetailsRepository extends CrudRepository<MyUserDetails, String>{
	MyUserDetails findByUsername(String username);

}
