import { useAuth } from "@/hooks/useAuth";
import { Loader2 } from "lucide-react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

interface ProtectedRouteProps {
  children: React.ReactNode;
}

function ProtectedRoute({ children }: ProtectedRouteProps) {
  const navigate = useNavigate();
  const { token, loading } = useAuth();

  useEffect(() => {
    if (!token?.accessToken && !loading) navigate("/unauthorized");
  }, [loading, navigate, token?.accessToken]);

  if (loading) {
    return (
      <div className="fixed top-10 left-10">
        <Loader2 className="animate-spin" />
      </div>
    );
  }

  if (token?.accessToken) {
    return children;
  }

  return null;
}

export default ProtectedRoute;
