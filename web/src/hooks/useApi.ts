import axios from "axios";
import Cookies from "js-cookie";
import API_URL from "@/lib/API_URL";
import { useAuth } from "@/hooks/useAuth";

export const useApi = () => {
  const { logout } = useAuth();

  const api = axios.create({
    baseURL: API_URL,
  });

  const refreshToken = async () => {
    try {
      const refreshToken = Cookies.get("refreshToken");
      const response = await axios.post(`${API_URL}/api/v1/auth/refresh`, {
        refreshToken,
      });

      const { accessToken, refreshToken: newRefreshToken } = response.data;
      Cookies.set("accessToken", accessToken, { expires: 1 });
      Cookies.set("refreshToken", newRefreshToken, { expires: 15 });

      return accessToken;
    } catch (error) {
      console.error("Refresh token failed:", error);
      logout();
      throw error;
    }
  };

  api.interceptors.request.use(
    async (config) => {
      const accessToken = Cookies.get("accessToken");

      if (accessToken) {
        config.headers["Authorization"] = `Bearer ${accessToken}`;
      }

      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  api.interceptors.response.use(
    (response) => response,
    async (error) => {
      const originalRequest = error.config;

      if (error.response?.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;

        try {
          const newAccessToken = await refreshToken();
          originalRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;

          return api(originalRequest);
        } catch (refreshError) {
          return Promise.reject(refreshError);
        }
      }

      return Promise.reject(error);
    }
  );

  return api;
};
