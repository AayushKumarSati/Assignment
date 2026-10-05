import { useState, useEffect } from "react";
import { fetchTasks } from "../api";

export function useTasks(query, status, page, pageSize) {
  const [tasks, setTasks] = useState([]);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    // FIX: Clear previous error before starting a new request.

    setError(null);

    fetchTasks({ query, status, page, pageSize })
      .then((data) => {
        setTasks(data.items);
        setTotal(data.total);
        setLoading(false);
      })
      .catch((err) => {
        // FIX: Stop loading when the API request fails.
        setLoading(false);
        // FIX: Store the API error so the UI can display it.
        setError(err.message);
      });
  }, [query, status, page, pageSize]);

  return { tasks, total, loading, error };
}
