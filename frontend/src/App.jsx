import { useState } from "react";
import SearchBar from "./components/SearchBar";
import StatusFilter from "./components/StatusFilter";
import TaskTable from "./components/TaskTable";
import { useTasks } from "./hooks/useTasks";

export default function App() {
  const [query, setQuery] = useState("");
  const [status, setStatus] = useState("");
  const [page, setPage] = useState(1);

  const { tasks, total, loading, error } = useTasks(query, status, page, 10);

  const totalPages = Math.ceil(total / 10);

  return (
    <div className="app">
      <header className="app-header">
        <h1>Task Tracker</h1>
        <p className="subtitle">Internal task management</p>
      </header>

      <div className="controls">
        <SearchBar
          value={query}
          onChange={(value) => {
            setQuery(value);
            // FIX: Reset to page 1 when search changes,
            // so matching results are not missed on later pages.
            // Hindi: Agar user kisi later page par hai aur search karta hai,
            // to matching results ke liye page 1 se search start hoga.

            setPage(1);
          }}
        />
        <StatusFilter
          value={status}
          onChange={(value) => {
            setStatus(value);
            // FIX: Reset to page 1 when status filter changes,
            // so matching results are not missed on later pages.
            // Hindi: Agar user kisi later page par hai aur status filter change karta hai,
            // to filtered results ke liye page 1 se start hoga.
            setPage(1);
          }}
        />
      </div>

      <TaskTable tasks={tasks} loading={loading} error={error} />

      {totalPages > 1 && (
        <div className="pagination">
          <button disabled={page <= 1} onClick={() => setPage((p) => p - 1)}>
            Previous
          </button>
          <span>
            Page {page} of {totalPages}
          </span>
          <button
            disabled={page >= totalPages}
            onClick={() => setPage((p) => p + 1)}
          >
            Next
          </button>
        </div>
      )}
    </div>
  );
}
