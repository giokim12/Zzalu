// local vue api axios instance
import axios from 'axios';
const token = window.localStorage.getItem('token');

function apiInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  return instance;
}

function authApiInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Authorization': `Bearer ${token}`,
      // 'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaW8iLCJhdXRoIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJnaW8iLCJleHAiOjE2NzYwMTAwNzd9.kb6wOjYwAX8lfTN2unJI8j0U-vuQeQZdr-hV1hJE_uo`,
    },
  });
  return instance;
}

function formApiInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    headers: {
      'Content-Type': 'multipart/form-data',
      'Authorization': `Bearer ${token}`,
      // 'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzc2FmeSIsImF1dGgiOiJST0xFX01BTkFHRVIsUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJzc2FmeSIsImV4cCI6MTY3NjMwOTQ5Nn0.K-lFgvLFXfDi0oiTNH8iwuFY9o3HZyqam62_sQQS6V4`,
    },
  });
  return instance;
}

export { apiInstance, authApiInstance, formApiInstance };
