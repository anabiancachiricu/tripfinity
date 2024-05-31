package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
