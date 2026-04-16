import React, { useState } from 'react';
  import axios from 'axios';

  function Login({ onLogin }) {
    const [mode, setMode] = useState('login');
    const [form, setForm] = useState({ username: '', email: '', password: '' });
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
      e.preventDefault();
      setError('');
      try {
        if (mode === 'register') {
          await axios.post('/api/auth/register', form);
          setMode('login');
        } else {
          const res = await axios.post('/api/auth/login', {
            username: form.username,
            password: form.password
          });
          onLogin(res.data.token);
        }
      } catch (err) {
        setError(err.response?.data?.message || 'Something went wrong');
      }
    };

    return (
      <div style={{ maxWidth: 400, margin: '80px auto', padding: 20 }}>
        <h2>{mode === 'login' ? 'Login' : 'Register'}</h2>
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <form onSubmit={handleSubmit}>
          <div>
            <input
              placeholder="Username"
              value={form.username}
              onChange={e => setForm({ ...form, username: e.target.value })}
              required
            />
          </div>
          {mode === 'register' && (
            <div>
              <input
                type="email"
                placeholder="Email"
                value={form.email}
                onChange={e => setForm({ ...form, email: e.target.value })}
                required
              />
            </div>
          )}
          <div>
            <input
              type="password"
              placeholder="Password"
              value={form.password}
              onChange={e => setForm({ ...form, password: e.target.value })}
              required
            />
          </div>
          <button type="submit">{mode === 'login' ? 'Login' : 'Register'}</button>
        </form>
        <p>
          {mode === 'login' ? "Don't have an account? " : "Already have an account? "}
          <button onClick={() => setMode(mode === 'login' ? 'register' : 'login')}>
            {mode === 'login' ? 'Register' : 'Login'}
          </button>
        </p>
      </div>
    );
  }

  export default Login;
  