package com.una.modus.repository;

import com.una.modus.model.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    void shouldCreateAndFindDocument() {

        Document document = new Document(
                1L,
                1L,
                1L,
                "BOARD_PHOTO",
                "PRIVATE"
        );

        Document savedDocument = documentRepository.save(document);

        Optional<Document> foundDocument =
                documentRepository.findById(savedDocument.getId());

        assertTrue(foundDocument.isPresent());
        assertEquals("BOARD_PHOTO", foundDocument.get().getSourceType());
        assertEquals("PRIVATE", foundDocument.get().getVisibility());
    }

    @Test
    void shouldUpdateDocument() {

        Document document = new Document(
                1L,
                1L,
                1L,
                "BOARD_PHOTO",
                "PRIVATE"
        );

        Document savedDocument = documentRepository.save(document);

        savedDocument.setVisibility("PUBLIC");
        documentRepository.save(savedDocument);

        Document updatedDocument = documentRepository
                .findById(savedDocument.getId())
                .orElseThrow();

        assertEquals("PUBLIC", updatedDocument.getVisibility());
    }

    @Test
    void shouldDeleteDocument() {

        Document document = new Document(
                1L,
                1L,
                1L,
                "SLIDE",
                "PUBLIC"
        );

        Document savedDocument = documentRepository.save(document);

        Long documentId = savedDocument.getId();

        documentRepository.deleteById(documentId);

        assertFalse(documentRepository.findById(documentId).isPresent());
    }
}