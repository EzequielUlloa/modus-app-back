package com.una.modus.repository;

import com.una.modus.model.Document;
import com.una.modus.model.Evaluation;
import com.una.modus.model.Submission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EvaluationRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Test
    void shouldSaveEvaluationWithIndependentResultAndMethodScore() {

        // 1. Crear y guardar un documento
        Document document = new Document(
                1L,
                1L,
                1L,
                "BOARD_PHOTO",
                "PRIVATE"
        );

        Document savedDocument = documentRepository.save(document);

        // 2. Crear y guardar una entrega asociada al documento
        Submission submission = new Submission(
                1L,
                1L,
                savedDocument,
                LocalDateTime.now()
        );

        Submission savedSubmission = submissionRepository.save(submission);

        // 3. Crear una evaluación
        Evaluation evaluation = new Evaluation(
                savedSubmission,
                1L,
                true,
                0.40,
                true
        );

        Evaluation savedEvaluation =
                evaluationRepository.save(evaluation);

        // 4. Recuperarla de la base de datos
        Evaluation foundEvaluation = evaluationRepository
                .findById(savedEvaluation.getId())
                .orElseThrow();

        // 5. Comprobar los valores
        assertTrue(foundEvaluation.getResultCorrect());
        assertEquals(0.40, foundEvaluation.getMethodScore());
        assertTrue(foundEvaluation.getCurrent());
    }

    @Test
    void shouldKeepResultAndMethodScoreIndependent() {

        Document document = new Document(
                1L,
                1L,
                1L,
                "SOLVED_EXERCISE",
                "PRIVATE"
        );

        Document savedDocument = documentRepository.save(document);

        Submission submission = new Submission(
                1L,
                1L,
                savedDocument,
                LocalDateTime.now()
        );

        Submission savedSubmission =
                submissionRepository.save(submission);

        Evaluation evaluation = new Evaluation(
                savedSubmission,
                1L,
                false,
                0.90,
                true
        );

        Evaluation savedEvaluation =
                evaluationRepository.save(evaluation);

        Evaluation foundEvaluation = evaluationRepository
                .findById(savedEvaluation.getId())
                .orElseThrow();

        assertFalse(foundEvaluation.getResultCorrect());
        assertEquals(0.90, foundEvaluation.getMethodScore());
    }

    @Test
    void shouldUpdateEvaluation() {

        Document document = documentRepository.save(
                new Document(1L, 1L, 1L, "BOARD_PHOTO", "PRIVATE")
        );

        Submission submission = submissionRepository.save(
                new Submission(1L, 1L, document, LocalDateTime.now())
        );

        Evaluation evaluation = evaluationRepository.save(
                new Evaluation(submission, 1L, false, 0.50, true)
        );

        evaluation.setResultCorrect(true);
        evaluation.setMethodScore(0.85);

        evaluationRepository.save(evaluation);

        Evaluation updatedEvaluation = evaluationRepository
                .findById(evaluation.getId())
                .orElseThrow();

        assertTrue(updatedEvaluation.getResultCorrect());
        assertEquals(0.85, updatedEvaluation.getMethodScore());
    }

    @Test
    void shouldDeleteEvaluation() {

        Document document = documentRepository.save(
                new Document(1L, 1L, 1L, "BOARD_PHOTO", "PRIVATE")
        );

        Submission submission = submissionRepository.save(
                new Submission(1L, 1L, document, LocalDateTime.now())
        );

        Evaluation evaluation = evaluationRepository.save(
                new Evaluation(submission, 1L, true, 0.75, true)
        );

        Long evaluationId = evaluation.getId();

        evaluationRepository.deleteById(evaluationId);

        assertFalse(evaluationRepository.findById(evaluationId).isPresent());
    }
}