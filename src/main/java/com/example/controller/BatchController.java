package com.example.controller;

import com.example.entity.Batch;
import com.example.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private BatchRepository batchRepository;

    // Create a new Batch
    @PostMapping
    public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
        Batch savedBatch = batchRepository.save(batch);
        return new ResponseEntity<>(savedBatch, HttpStatus.CREATED);
    }

    // Get all batches
    @GetMapping
    public ResponseEntity<List<Batch>> getAllBatches() {
        List<Batch> batches = batchRepository.findAll();
        return new ResponseEntity<>(batches, HttpStatus.OK);
    }

    // Get a specific batch by ID
    @GetMapping("/{batchId}")
    public ResponseEntity<Batch> getBatchById(@PathVariable String batchId) {
        Optional<Batch> batch = batchRepository.findById(batchId);
        return batch.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update a batch
    @PutMapping("/{batchId}")
    public ResponseEntity<Batch> updateBatch(@PathVariable String batchId, @RequestBody Batch batchDetails) {
        Optional<Batch> existingBatch = batchRepository.findById(batchId);
        if (existingBatch.isPresent()) {
            Batch batch = existingBatch.get();
            batch.setVenue(batchDetails.getVenue());
            Batch updatedBatch = batchRepository.save(batch);
            return new ResponseEntity<>(updatedBatch, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a batch
    @DeleteMapping("/{batchId}")
    public ResponseEntity<Void> deleteBatch(@PathVariable String batchId) {
        Optional<Batch> batch = batchRepository.findById(batchId);
        if (batch.isPresent()) {
            batchRepository.delete(batch.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
