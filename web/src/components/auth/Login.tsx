import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useAuth } from "@/hooks/useAuth";
import { useApi } from "@/hooks/useApi";
import { handleChange } from "@/lib/utils";
import { ApiError, LoginError, LoginProps } from "@/types";
import { useMutation } from "@tanstack/react-query";
import { Loader2 } from "lucide-react";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export function Login() {
  const api = useApi();
  const navigate = useNavigate();
  const { login, token: authToken, loading: authLoading } = useAuth();
  const [formData, setFormData] = useState<LoginProps>({
    email: "",
    password: "",
  });

  useEffect(() => {
    if (authToken?.accessToken && !authLoading) {
      navigate("/home");
    }
  }, [authLoading, authToken?.accessToken, navigate]);

  const mutation = useMutation({
    mutationFn: (data: LoginProps) => {
      return api.post(`/auth/login`, data);
    },
    onSuccess: (data) => {
      login({
        user: data.data.user,
        token: {
          accessToken: data.data.accessToken,
          refreshToken: data.data.refreshToken,
        },
      });

      navigate("/home");
      console.log("Success logging in");
    },
    onError: (error: ApiError<LoginError>) => {
      console.error("Error logging in:", error);
    },
  });

  const { mutate, isPending: isLoading, isError, error, isSuccess } = mutation;

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    mutate(formData);
  };

  return (
    <div className="space-y-8 w-[450px] mx-auto my-10">
      <div className="text-center">
        <h2 className="text-3xl font-bold text-gray-900">Welcome back</h2>
        <p className="mt-2 text-sm text-gray-600">
          Enter your credentials to access your account
        </p>
      </div>
      <form className="space-y-6" onSubmit={handleSubmit}>
        <div className="space-y-4">
          <div>
            <Label
              htmlFor="email"
              className="block text-sm font-medium text-gray-700"
            >
              Email
            </Label>
            <Input
              id="email"
              name="email"
              type="email"
              autoComplete="email"
              required
              className="mt-1 w-full"
              placeholder="you@example.com"
              disabled={isLoading}
              value={formData.email}
              onChange={(e) =>
                handleChange({ e, data: formData, setData: setFormData })
              }
            />
          </div>
          <div>
            <Label
              htmlFor="password"
              className="block text-sm font-medium text-gray-700"
            >
              Password
            </Label>
            <Input
              id="password"
              name="password"
              type="password"
              autoComplete="current-password"
              required
              className="mt-1 w-full"
              placeholder="••••••••"
              disabled={isLoading}
              value={formData.password}
              onChange={(e) =>
                handleChange({ e, data: formData, setData: setFormData })
              }
            />
          </div>
        </div>

        <div>
          <Button
            type="submit"
            className="w-full bg-gradient-to-r from-purple-600 to-indigo-600 hover:from-purple-700 hover:to-indigo-700 text-white font-semibold py-2 px-4 rounded-md transition duration-300 ease-in-out transform hover:-translate-y-1 hover:shadow-lg"
            disabled={isLoading}
          >
            {isLoading ? (
              <>
                <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                Loging in...
              </>
            ) : (
              "Log in"
            )}
          </Button>
        </div>
      </form>

      <div className="flex justify-center items-center">
        {isError && (
          <p style={{ color: "red" }}>
            Error:{" "}
            {error.response?.data.error || "An unexpected error occurred"}
          </p>
        )}
        {isSuccess && <p style={{ color: "green" }}>Login successful!</p>}
      </div>

      <p className="mt-2 text-center text-sm text-gray-600">
        Don't have an account?{" "}
        <Link to={"/auth/signup"}>
          <Button
            variant="link"
            className="p-0 h-auto font-semibold text-indigo-600 hover:text-indigo-500"
            disabled={isLoading}
          >
            Sign up
          </Button>
        </Link>
      </p>
    </div>
  );
}
