# Patch Exercise Notes

## Summary

Fixed the highest-value issues found while reviewing the task tracker frontend, backend, SQL query, and database reference artifact.

### Fixes Made

1. Fixed SQL AND/OR precedence in `TaskRepository.java` so archived tasks are excluded and status filtering applies correctly to both title and description searches.
2. Removed the artificial `Thread.sleep()` delay from `TaskController.java`, which was unnecessarily slowing API responses.
3. Added validation for `page` and `pageSize` to prevent invalid pagination requests.
4. Added validation for invalid task statuses and return a proper `400 Bad Request` response.
5. Fixed frontend error handling in `useTasks.js` so loading state stops when an API request fails.
6. Reset pagination to page 1 when the search query or status filter changes in `App.jsx`.
7. Updated the SQL/Oracle reference artifact to keep the search logic consistent with the corrected repository query.

## What I Did Not Change

I did not rewrite the application architecture, database model, API design, or frontend structure because they were outside the scope of the highest-value fixes and the exercise was time-boxed.

## Biggest Remaining Risk

The backend currently loads all matching tasks and performs pagination in Java. For a large production dataset, database-level pagination would be more efficient.

## Verification

Tested the backend using Postman and verified the frontend through the browser. Tested normal search, status filtering, pagination, invalid pagination values, invalid status, and archived-task filtering.

## Tools / AI Used

Used IntelliJ IDEA/VS Code, browser DevTools, Postman, Git, and AI assistance for code review, debugging, and explaining potential fixes. All changes were reviewed and tested manually.
