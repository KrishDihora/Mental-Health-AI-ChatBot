import { createContext, useEffect, useState } from "react";
import Cookies from "js-cookie";
import { User } from "@/types";

interface LoginType {
  user: User;
  token: { accessToken: string | null; refreshToken: string | null };
}

interface AuthContextType {
  user: User | null;
  token: {
    accessToken: string | null;
    refreshToken: string | null;
  };
  setToken: React.Dispatch<
    React.SetStateAction<{
      accessToken: string | null;
      refreshToken: string | null;
    }>
  >;
  loading: boolean;
  error: string | null;
  login: (params: LoginType) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<{
    accessToken: string | null;
    refreshToken: string | null;
  }>({
    accessToken: null,
    refreshToken: null,
  });
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchUser = () => {
      try {
        const currentUser = Cookies.get("user")
          ? JSON.parse(Cookies.get("user")!)
          : null;
        const accessToken = Cookies.get("accessToken") || null;
        const refreshToken = Cookies.get("refreshToken") || null;

        const token = { accessToken, refreshToken };

        setUser(currentUser);
        setToken(token);
        setLoading(false);
      } catch (error) {
        if (error instanceof Error) {
          setError(error.message);
        } else {
          setError("An unknown error occurred");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, []);

  const login = ({ user, token }: LoginType) => {
    setUser(user);
    setToken(token);

    Cookies.set("user", JSON.stringify(user));
    Cookies.set("accessToken", token.accessToken!, { expires: 1 });
    Cookies.set("refreshToken", token.refreshToken!, { expires: 15 });
  };

  const logout = () => {
    setUser(null);
    setToken({ accessToken: null, refreshToken: null });
    Cookies.remove("user");
    Cookies.remove("accessToken");
    Cookies.remove("refreshToken");
  };

  return (
    <AuthContext.Provider
      value={{ user, token, setToken, loading, error, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
