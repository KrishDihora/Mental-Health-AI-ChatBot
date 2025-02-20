import { motion } from "framer-motion";
import { Button } from "@/components/ui/button";
import { FileQuestion } from "lucide-react";
import { Link } from "react-router-dom";

export default function NotFoundPage() {
  return (
    <div className="min-h-screen bg-gray-100 flex flex-col justify-center items-center px-4">
      <motion.div
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="max-w-md w-full bg-white shadow-lg rounded-lg p-8 text-center"
      >
        <FileQuestion className="mx-auto h-24 w-24 text-indigo-500 mb-6" />
        <h1 className="text-4xl font-bold text-gray-900 mb-2">404</h1>
        <h2 className="text-2xl font-semibold text-gray-700 mb-4">
          Page Not Found
        </h2>
        <p className="text-gray-600 mb-8">
          Oops! The page you're looking for doesn't exist. It might have been
          moved or deleted.
        </p>
        <div className="space-y-4">
          <Button asChild className="w-full">
            <Link to="/home">Return to Home</Link>
          </Button>
          <Button asChild variant="outline" className="w-full">
            <Link to="#">Contact Support</Link>
          </Button>
        </div>
      </motion.div>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.5, duration: 0.5 }}
        className="mt-8 text-center"
      >
        <p className="text-gray-600">
          Lost? You might want to check out these popular pages:
        </p>
        <div className="mt-4 space-x-4">
          <Link to="#" className="text-indigo-600 hover:underline">
            Features
          </Link>
          <Link to="#" className="text-indigo-600 hover:underline">
            Pricing
          </Link>
          <Link to="#" className="text-indigo-600 hover:underline">
            About Us
          </Link>
        </div>
      </motion.div>
    </div>
  );
}
