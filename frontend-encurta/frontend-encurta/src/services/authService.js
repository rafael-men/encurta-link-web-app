import axios from 'axios';
import { jwtDecode } from 'jwt-decode';


const API_URL = 'http://localhost:8090/encurta/auth';

export const registerUser = async (registerData) => {
  try {
    const response = await axios.post(`${API_URL}/register`, registerData);
    return response.data; 
  } catch (error) {
    if (error.response) {
      return { success: false, message: error.response.data.message };
    } else if (error.request) {
      return { success: false, message: 'Erro ao se comunicar com o servidor.' };
    } else {
      return { success: false, message: error.message };
    }
  }
};

export const loginUser = async (loginData) => {
  try {
    const { data } = await axios.post(`${API_URL}/login`, loginData);

    const token = data?.token || data?.jwt;
    const userFromDto = data?.user || data?.userDto; 

    if (!token) {
      return { success: false, message: 'Token não recebido, login falhou.' };
    }

    const decoded = jwtDecode(token);

    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(userFromDto || decoded));

    return {
      success: true,
      token,
      user: userFromDto || decoded,
    };
  } catch (error) {
    if (error.response) {
      return { success: false, message: error.response.data?.message || 'Falha no login.' };
    } else if (error.request) {
      return { success: false, message: 'Erro ao se comunicar com o servidor.' };
    } else {
      return { success: false, message: error.message };
    }
  }
};

