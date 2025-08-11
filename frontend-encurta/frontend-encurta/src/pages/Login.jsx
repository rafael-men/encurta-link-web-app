import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import { loginUser } from '../services/authService';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async(e) => {
    e.preventDefault();

    const loginData = {
        username,
        password
    };

    const result = await loginUser(loginData);
    if(result.success) {
        localStorage.setItem('user',JSON.stringify(result.user));

        navigate('/home-encurtador');
    }
    else {
        setErrorMessage(result.message);
    }
  };
  return (
    <div className="min-h-screen bg-blue-900 flex justify-center items-center">
      <div className="bg-white p-8 shadow-md rounded-lg w-96">
        <h2 className="text-2xl font-semibold text-blue-600 mb-6">Login</h2>
        {errorMessage && <p className="text-red-500">{errorMessage}</p>}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <input
              type="text"
              placeholder="Nome de usuário"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div>
            <input
              type="password"
              placeholder="Senha"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <button
            type="submit"
            className="w-full py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
          >
            Entrar
          </button>
        </form>
        <p className="mt-4 text-center">
          Não tem uma conta?{' '}
          <a href="/register" className="text-blue-600 hover:underline">
            Registre-se
          </a>
        </p>
      </div>
    </div>
  );
};

export default Login