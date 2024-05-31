package com.unibuc.tripfinity.service;

import com.unibuc.tripfinity.model.Document;
import com.unibuc.tripfinity.repository.DocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document addDocument(Document document){
        return documentRepository.save(document);
    }

}
