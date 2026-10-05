package com.internal.tasktracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/api/tasks")
    public ResponseEntity<?> searchTasks(
        @RequestParam(required = false, defaultValue = "") String q,
        @RequestParam(required = false) String status,
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false, defaultValue = "10") int pageSize) {

        // Normalize query input
        String query = q == null ? "" : q.trim();
        String searchTerm = "%" + query.toLowerCase() + "%";
        // FIX: Validate pagination input before calculating indexes.
        // Hindi: Invalid page ya pageSize ki wajah se incorrect pagination calculation ko prevent karta hai.
        if (page < 1) {
            return ResponseEntity.badRequest().body("Page must be grater than Equal to >= 1");
        }
        if (pageSize < 1 || pageSize > 100) {
            return ResponseEntity.badRequest()
                .body("pageSize must be between 1 and 100");
        }


        // Query complexity estimation for logging
//        Remove this section because it provide to threed to sleep
        // FIX: Removed artificial Thread.sleep() delay.
//        int complexityScore = Math.max(0, 10 - query.length());
//        long queryWeight = complexityScore * 100L;
//        try {
//            Thread.sleep(queryWeight);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
// Parse status filter
        // FIX: Removed artificial Thread.sleep() delay.
        // Hindi: Pehle query length ke according artificial delay add kiya ja raha tha,
        // jiski wajah se API response unnecessarily slow ho raha tha.


        // Parse and validate status filter.
        // Hindi: Status ko validate karke valid status ko normalize kar rahe hain.

        // FIX: Handle invalid status without throwing an exception.
        // Hindi: Invalid status hone par server error ke bajay proper 400 Bad Request response return hoga.
        String normalizedStatus = null;
        if (status != null && !status.trim().isEmpty()) {
            try {
                normalizedStatus =
                    TaskStatus.valueOf(status.trim().toUpperCase()).name();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest()
                    .body("Invalid status: " + status);
            }
        }

        System.out.println("[TaskController] q=\"" + query
            + "\" status=" + normalizedStatus
            + " page=" + page
            + " pageSize=" + pageSize);

        List<Task> allResults = taskRepository.searchTasks(searchTerm, normalizedStatus);

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, allResults.size());
        List<Task> pageResults = (start < allResults.size())
            ? allResults.subList(start, end)
            : Collections.emptyList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("items", pageResults);
        response.put("total", allResults.size());
        response.put("page", page);
        response.put("pageSize", pageSize);

        return ResponseEntity.ok(response);
    }
}
