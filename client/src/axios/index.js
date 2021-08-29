import axios from 'axios';

const CONTEXT_URL = window.contextUrl ? window.contextUrl : '/';
const API_URL = `${CONTEXT_URL}api`;

const securedAxios = axios.create({
  baseURL: API_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

const plainAxios = axios.create({
  baseURL: CONTEXT_URL,
  withCredentials: false,
  headers: {
    'Content-Type': 'application/json',
  },
});

securedAxios.interceptors.response.use(null, (error) => {
  if (error.response && error.response.status === 401) {
    delete localStorage.loggedIn;
    window.location.replace(`/login?redirect=${encodeURIComponent(window.location.pathname + window.location.search)}`);
  }
  return Promise.reject(error);
});

export { plainAxios, securedAxios };
