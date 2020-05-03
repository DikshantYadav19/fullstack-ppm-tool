package com.practice.restservices.ppmtoolservices.repositories;

import com.practice.restservices.ppmtoolservices.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    public Backlog findByProjectIdentifier(String identifier);
}
