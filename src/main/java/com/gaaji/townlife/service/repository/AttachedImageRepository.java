package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.townlife.AttachedImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachedImageRepository extends JpaRepository<AttachedImage, String> {
}
