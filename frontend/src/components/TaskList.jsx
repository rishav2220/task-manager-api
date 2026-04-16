import React, { useState, useEffect } from 'react';
  import axios from 'axios';

  const api = axios.create({ baseURL: '/api' });

  function TaskList({ token, onLogout }) {
    const [tasks, setTasks] = useState([]);
    const [newTask, setNewTask] = useState({ title: '', description: '', priority: 'MEDIUM' });
    const [statusFilter, setStatusFilter] = useState('');
    const [error, setError] = useState('');

    const headers = { Authorization: `Bearer ${token}` };

    useEffect(() => {
      fetchTasks();
    }, [statusFilter]);

    const fetchTasks = async () => {
      try {
        const params = statusFilter ? { status: statusFilter } : {};
        const res = await api.get('/tasks', { headers, params });
        setTasks(res.data);
      } catch (err) {
        setError('Failed to load tasks');
      }
    };

    const createTask = async (e) => {
      e.preventDefault();
      try {
        const res = await api.post('/tasks', newTask, { headers });
        setTasks([...tasks, res.data]);
        setNewTask({ title: '', description: '', priority: 'MEDIUM' });
      } catch (err) {
        setError('Failed to create task');
      }
    };

    const updateStatus = async (id, status) => {
      const task = tasks.find(t => t.id === id);
      try {
        const res = await api.put(`/tasks/${id}`, { ...task, status }, { headers });
        setTasks(tasks.map(t => t.id === id ? res.data : t));
      } catch (err) {
        setError('Failed to update task');
      }
    };

    const deleteTask = async (id) => {
      try {
        await api.delete(`/tasks/${id}`, { headers });
        setTasks(tasks.filter(t => t.id !== id));
      } catch (err) {
        setError('Failed to delete task');
      }
    };

    return (
      <div style={{ maxWidth: 800, margin: '0 auto', padding: 20 }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: 20 }}>
          <h1>My Tasks</h1>
          <button onClick={onLogout}>Logout</button>
        </div>

        {error && <p style={{ color: 'red' }}>{error}</p>}

        <form onSubmit={createTask} style={{ marginBottom: 20 }}>
          <input
            placeholder="Task title"
            value={newTask.title}
            onChange={e => setNewTask({ ...newTask, title: e.target.value })}
            required
          />
          <input
            placeholder="Description"
            value={newTask.description}
            onChange={e => setNewTask({ ...newTask, description: e.target.value })}
          />
          <select value={newTask.priority} onChange={e => setNewTask({ ...newTask, priority: e.target.value })}>
            <option value="LOW">Low</option>
            <option value="MEDIUM">Medium</option>
            <option value="HIGH">High</option>
          </select>
          <button type="submit">Add Task</button>
        </form>

        <div style={{ marginBottom: 10 }}>
          <label>Filter: </label>
          <select value={statusFilter} onChange={e => setStatusFilter(e.target.value)}>
            <option value="">All</option>
            <option value="TODO">To Do</option>
            <option value="IN_PROGRESS">In Progress</option>
            <option value="DONE">Done</option>
          </select>
        </div>

        {tasks.map(task => (
          <div key={task.id} style={{ border: '1px solid #ddd', padding: 10, marginBottom: 10, borderRadius: 4 }}>
            <h3>{task.title}</h3>
            <p>{task.description}</p>
            <span>Priority: {task.priority} | </span>
            <select value={task.status} onChange={e => updateStatus(task.id, e.target.value)}>
              <option value="TODO">To Do</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="DONE">Done</option>
            </select>
            <button onClick={() => deleteTask(task.id)} style={{ marginLeft: 10 }}>Delete</button>
          </div>
        ))}
      </div>
    );
  }

  export default TaskList;
  