import React, { useState } from 'react';
  import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
  import Login from './components/Login';
  import TaskList from './components/TaskList';
  import './App.css';

  function App() {
    const [token, setToken] = useState(localStorage.getItem('token'));

    const handleLogin = (newToken) => {
      localStorage.setItem('token', newToken);
      setToken(newToken);
    };

    const handleLogout = () => {
      localStorage.removeItem('token');
      setToken(null);
    };

    return (
      <Router>
        <div className="App">
          <Routes>
            <Route path="/login" element={
              token ? <Navigate to="/" /> : <Login onLogin={handleLogin} />
            } />
            <Route path="/" element={
              token ? <TaskList token={token} onLogout={handleLogout} /> : <Navigate to="/login" />
            } />
          </Routes>
        </div>
      </Router>
    );
  }

  export default App;
  