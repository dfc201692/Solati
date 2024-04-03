// En un archivo de configuración axiosConfig.js
import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080/api/productos', // Reemplaza "puerto" con el puerto de tu backend
});

export default instance;
