package com.ricky.savelogdiff.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ricky.savelogdiff.domain.LogAlert;

@Repository
public interface AlertRepository extends CrudRepository<LogAlert, String> {
}
