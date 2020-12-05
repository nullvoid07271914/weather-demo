package com.src.weather.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepositories<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	// universal repositories for all entities
	// customizable transactional queries
}
