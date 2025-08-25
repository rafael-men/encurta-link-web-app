import axios from 'axios';

const API_URL = 'http://localhost:8090/encurta/short-urls';

const api = axios.create({
  baseURL: API_URL,
});



api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

const getAuthToken = () => {
  return localStorage.getItem('authToken') || 
         sessionStorage.getItem('authToken') ||
         localStorage.getItem('token') ||
         sessionStorage.getItem('token');
};

export const getAllShortUrls = async () => {
  try {
    const { data } = await api.get();

    const urls = (Array.isArray(data) ? data : []).map(item => ({
      id: item.id,
      originalUrl: item.originalUrl,
      shortCode: item.shortCode,
    }));

    return {
      success: true,
      data: urls,
    };
  } catch (error) {
    return {
      success: false,
      message:
        error.response?.data?.message ||
        `Erro ${error.response?.status || ""}: ${error.message}`,
    };
  }
};


export const generateShortUrl = async (shortUrlData) => {
  try {
    const { data } = await api.post('/generate', shortUrlData);
    return { success: true, data };
  } catch (error) {
    return {
      success: false,
      message: error.response?.data?.message || 'Erro ao gerar link curto.',
    };
  }
};



export const getShortUrlByCode = async (code) => {
  try {
    const { data } = await api.get(`/${code}`);
    return { success: true, data };
  } catch (error) {
    return {
      success: false,
      message: error.response?.data?.message || 'Erro ao buscar link curto.',
    };
  }
};
