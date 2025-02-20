import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { AuthProvider } from "./context/AuthProvider";

import ProtectedRoute from "./routes/ProtectedRoute";
import UnauthorizedPage from "./components/Unauthorized";
import NotFoundPage from "./components/NotFoundPage";
import Layout from "./components/dashboard/Layout";
import Home from "./components/dashboard/Home";
import ChatPage from "./components/chat/Chat";
import { Login } from "./components/auth/Login";
import { Signup } from "./components/auth/Signup";

const DashboardRoute = ({ element }: { element: React.ReactNode }) => (
  <ProtectedRoute>
    <Layout>{element}</Layout>
  </ProtectedRoute>
);

function App() {
  const router = createBrowserRouter([
    {
      path: "/auth/login",
      element: <Login />,
    },
    {
      path: "/auth/signup",
      element: <Signup />,
    },
    {
      path: "/home",
      element: <DashboardRoute element={<Home />} />,
    },
    // {
    //   path: "/patients",
    //   element: <DashboardRoute element={<PatientsPage />} />,
    // },
    {
      path: "/chat",
      element: <DashboardRoute element={<ChatPage />} />,
    },
    // {
    //   path: "/settings",
    //   element: <DashboardRoute element={<SettingsPage />} />,
    // },
    {
      path: "/unauthorized",
      element: <UnauthorizedPage />,
    },
    {
      path: "*",
      element: <NotFoundPage />,
    },
  ]);

  return (
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  );
}

export default App;
