import { useAuth } from "./useAuth";
import axios from "axios";
import API_URL from "@/lib/API_URL";

export const useRefreshToken = () => {
  const { setToken } = useAuth();

  const refresh = async () => {
    const response = await axios.get(`${API_URL}/api/v1/auth/refresh`, {
      withCredentials: true,
    });

    setToken((prev) => {
      console.log(prev);
      console.log(response.data.accessToken);
      return {
        ...prev,
        accessToken: response.data.accessToken,
        refreshToken: response.data.refreshToken,
      };
    });

    return {
      accessToken: response.data.accessToken,
      refreshToken: response.data.refreshToken,
    };
  };

  return refresh;
};
