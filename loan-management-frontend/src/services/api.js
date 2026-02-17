import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080/api"
});

API.interceptors.request.use(config => {
  const auth = localStorage.getItem("auth");
  if (auth) {
    config.headers.Authorization = "Basic " + auth;
  }
  return config;
});

export default API;
