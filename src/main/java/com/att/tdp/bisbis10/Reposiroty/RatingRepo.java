package com.att.tdp.bisbis10.Reposiroty;

import com.att.tdp.bisbis10.Models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepo extends JpaRepository<Rating, Long> {
}
