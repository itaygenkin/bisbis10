package com.att.tdp.bisbis10.Reposiroty;

import com.att.tdp.bisbis10.Models.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineRepo extends JpaRepository<Cuisine, Long> {
}
