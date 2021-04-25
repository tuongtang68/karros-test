package com.example.demo.core.repository;

import com.example.demo.core.model.GPS;
import com.example.demo.core.repository.projection.GPSSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GPSRepository extends CrudRepository<GPS, Integer>, PagingAndSortingRepository<GPS, Integer> {
    Page<GPSSummary> findAllByUserNotNull(Pageable pageable);
}
