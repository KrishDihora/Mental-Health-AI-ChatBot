import { motion } from "framer-motion";
import { Button } from "@/components/ui/button";
import { AlertCircle } from "lucide-react";
import { Link } from "react-router-dom";

export default function UnauthorizedPage() {
  return (
    <div className="min-h-screen bg-gray-100 flex flex-col justify-center items-center px-4">
      <motion.div
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="max-w-md w-full bg-white shadow-lg rounded-lg p-8 text-center"
      >
        <AlertCircle className="mx-auto h-16 w-16 text-red-500 mb-6" />
        <h1 className="text-3xl font-bold text-gray-900 mb-4">
          Unauthorized Access
        </h1>
        <p className="text-gray-600 mb-8">
          Sorry, you don't have permission to access this page. Please choose an
          option below:
        </p>
        <div className="space-y-4">
          <Button asChild className="w-full">
            <Link to="/">Go to Home</Link>
          </Button>
          <Button asChild variant="outline" className="w-full">
            <Link to="/auth/login">Go to Login</Link>
          </Button>
          <Button asChild variant="secondary" className="w-full">
            <Link to="/auth/signup">Go to Sign Up</Link>
          </Button>
        </div>
      </motion.div>
    </div>
  );
}
