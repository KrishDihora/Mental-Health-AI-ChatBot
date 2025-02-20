import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useApi } from "@/hooks/useApi";
import { useAuth } from "@/hooks/useAuth";
import { handleChange } from "@/lib/utils";
import { ApiError, SignupError, SignupProps } from "@/types";
import { useMutation } from "@tanstack/react-query";
import { Loader2 } from "lucide-react";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export function Signup() {
  const api = useApi();
  const { token: authToken, loading: authLoading } = useAuth();
  const navigate = useNavigate();
  const [formData, setFormData] = useState<SignupProps>({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  useEffect(() => {
    if (authToken?.accessToken && !authLoading) {
      navigate("/home");
    }
  }, [authLoading, authToken?.accessToken, navigate]);

  const mutation = useMutation({
    mutationFn: (data: SignupProps) => {
      return api.post(`/auth/signup`, data);
    },
    onSuccess: () => {
      navigate("/auth/login");
      console.log("Success signing up");
    },
    onError: (error: ApiError<SignupError>) => {
      console.error("Error signing up:", error);
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
        <h2 className="text-3xl font-bold text-gray-900">Create an account</h2>
        <p className="mt-2 text-sm text-gray-600">
          Enter your details to create your account
        </p>
      </div>
      <form className="space-y-6" onSubmit={handleSubmit}>
        <div className="space-y-4">
          <div className="flex flex-col md:flex-row justify-between items-center gap-3">
            <div className="w-full">
              <Label
                htmlFor="firstName"
                className="block text-sm font-medium text-gray-700"
              >
                First Name
              </Label>
              <Input
                id="firstName"
                name="firstName"
                type="text"
                autoComplete="firstName"
                required
                className="mt-1 w-full"
                placeholder="John"
                disabled={isLoading}
                value={formData.firstName}
                onChange={(e) =>
                  handleChange({ e, data: formData, setData: setFormData })
                }
              />
            </div>
            <div className="w-full">
              <Label
                htmlFor="lastName"
                className="block text-sm font-medium text-gray-700"
              >
                Last Name
              </Label>
              <Input
                id="lastName"
                name="lastName"
                type="text"
                autoComplete="lastName"
                required
                className="mt-1 w-full"
                placeholder="Doe"
                disabled={isLoading}
                value={formData.lastName}
                onChange={(e) =>
                  handleChange({ e, data: formData, setData: setFormData })
                }
              />
            </div>
          </div>

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
              autoComplete="new-password"
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
                Signing up...
              </>
            ) : (
              "Sign up"
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
        {isSuccess && <p style={{ color: "green" }}>Signup successful!</p>}
      </div>

      <p className="mt-2 text-center text-sm text-gray-600">
        Already have an account?{" "}
        <Link to={"/auth/login"}>
          <Button
            variant="link"
            className="p-0 h-auto font-semibold text-indigo-600 hover:text-indigo-500"
            disabled={isLoading}
          >
            Log in
          </Button>
        </Link>
      </p>
    </div>
  );
}

// {
//   import { Button } from "@/components/ui/button";
//   import { Input } from "@/components/ui/input";
//   import { Label } from "@/components/ui/label";
//   import API_URL from "@/lib/API_URL";
//   import { handleChange } from "@/lib/utils";
//   import { SignupProps } from "@/types";
//   import { useMutation } from "@tanstack/react-query";
//   import axios from "axios";
//   import { Github, Loader2, Mail } from "lucide-react";
//   import { useState } from "react";
//   import { motion } from "framer-motion";
//   import { ProductInfo } from "./ProductInfo";

//   export function Signup() {
//     const [formData, setFormData] = useState<SignupProps>({
//       firstName: "",
//       lastName: "",
//       userName: "",
//       email: "",
//       password: "",
//     });

//     const mutation = useMutation({
//       mutationFn: (data: SignupProps) => {
//         return axios.post(`${API_URL}/api/v1/auth/signup`, data);
//       },
//     });

//     const {
//       mutate,
//       isPending: isLoading,
//       isError,
//       error,
//       isSuccess,
//     } = mutation;

//     const handleSubmit = (e: React.FormEvent) => {
//       e.preventDefault();
//       mutate(formData);
//     };

//     return (
//       <div className="flex min-h-screen bg-gray-50 overflow-hidden">
//         <motion.div
//           initial={{ opacity: 0, x: -20 }}
//           animate={{ opacity: 1, x: 0 }}
//           transition={{ duration: 0.5 }}
//           className="w-full md:w-1/2 p-8 flex items-center justify-center"
//         >
//           <div className="w-full max-w-md">
//             <motion.div
//               initial={{ opacity: 0, y: 20 }}
//               animate={{ opacity: 1, y: 0 }}
//               exit={{ opacity: 0, y: -20 }}
//               transition={{ duration: 0.3 }}
//             >
//               <div className="space-y-8">
//                 <div className="text-center">
//                   <h2 className="text-3xl font-bold text-gray-900">
//                     Create an account
//                   </h2>
//                   <p className="mt-2 text-sm text-gray-600">
//                     Enter your details to create your account
//                   </p>
//                 </div>
//                 <form className="space-y-6" onSubmit={handleSubmit}>
//                   <div className="space-y-4">
//                     <div className="flex flex-col md:flex-row justify-between items-center gap-3">
//                       <div className="w-full">
//                         <Label
//                           htmlFor="firstName"
//                           className="block text-sm font-medium text-gray-700"
//                         >
//                           First Name
//                         </Label>
//                         <Input
//                           id="firstName"
//                           name="firstName"
//                           type="text"
//                           autoComplete="firstName"
//                           required
//                           className="mt-1 w-full"
//                           placeholder="John"
//                           disabled={isLoading}
//                           value={formData.firstName}
//                           onChange={(e) =>
//                             handleChange({
//                               e,
//                               data: formData,
//                               setData: setFormData,
//                             })
//                           }
//                         />
//                       </div>
//                       <div className="w-full">
//                         <Label
//                           htmlFor="lastName"
//                           className="block text-sm font-medium text-gray-700"
//                         >
//                           Last Name
//                         </Label>
//                         <Input
//                           id="lastName"
//                           name="lastName"
//                           type="text"
//                           autoComplete="lastName"
//                           required
//                           className="mt-1 w-full"
//                           placeholder="Doe"
//                           disabled={isLoading}
//                           value={formData.lastName}
//                           onChange={(e) =>
//                             handleChange({
//                               e,
//                               data: formData,
//                               setData: setFormData,
//                             })
//                           }
//                         />
//                       </div>
//                     </div>
//                     <div>
//                       <Label
//                         htmlFor="userName"
//                         className="block text-sm font-medium text-gray-700"
//                       >
//                         User Name
//                       </Label>
//                       <Input
//                         id="userName"
//                         name="userName"
//                         type="text"
//                         autoComplete="userName"
//                         required
//                         className="mt-1 w-full"
//                         placeholder="johndoe"
//                         disabled={isLoading}
//                         value={formData.userName}
//                         onChange={(e) =>
//                           handleChange({
//                             e,
//                             data: formData,
//                             setData: setFormData,
//                           })
//                         }
//                       />
//                     </div>
//                     <div>
//                       <Label
//                         htmlFor="email"
//                         className="block text-sm font-medium text-gray-700"
//                       >
//                         Email
//                       </Label>
//                       <Input
//                         id="email"
//                         name="email"
//                         type="email"
//                         autoComplete="email"
//                         required
//                         className="mt-1 w-full"
//                         placeholder="you@example.com"
//                         disabled={isLoading}
//                         value={formData.email}
//                         onChange={(e) =>
//                           handleChange({
//                             e,
//                             data: formData,
//                             setData: setFormData,
//                           })
//                         }
//                       />
//                     </div>
//                     <div>
//                       <Label
//                         htmlFor="password"
//                         className="block text-sm font-medium text-gray-700"
//                       >
//                         Password
//                       </Label>
//                       <Input
//                         id="password"
//                         name="password"
//                         type="password"
//                         autoComplete="new-password"
//                         required
//                         className="mt-1 w-full"
//                         placeholder="••••••••"
//                         disabled={isLoading}
//                         value={formData.password}
//                         onChange={(e) =>
//                           handleChange({
//                             e,
//                             data: formData,
//                             setData: setFormData,
//                           })
//                         }
//                       />
//                     </div>
//                   </div>

//                   <div>
//                     <Button
//                       type="submit"
//                       className="w-full bg-gradient-to-r from-purple-600 to-indigo-600 hover:from-purple-700 hover:to-indigo-700 text-white font-semibold py-2 px-4 rounded-md transition duration-300 ease-in-out transform hover:-translate-y-1 hover:shadow-lg"
//                       disabled={isLoading}
//                     >
//                       {isLoading ? (
//                         <>
//                           <Loader2 className="mr-2 h-4 w-4 animate-spin" />
//                           Signing up...
//                         </>
//                       ) : (
//                         "Sign up"
//                       )}
//                     </Button>
//                   </div>
//                 </form>

//                 {isError && (
//                   <p style={{ color: "red" }}>Error: {error.message}</p>
//                 )}
//                 {isSuccess && (
//                   <p style={{ color: "green" }}>Signup successful!</p>
//                 )}

//                 <div>
//                   <div className="relative">
//                     <div className="absolute inset-0 flex items-center">
//                       <div className="w-full border-t border-gray-300" />
//                     </div>
//                     <div className="relative flex justify-center text-sm">
//                       <span className="px-2 bg-white text-gray-500">
//                         Or continue with
//                       </span>
//                     </div>
//                   </div>

//                   <div className="mt-6 flex flex-col gap-3">
//                     <Button
//                       variant="outline"
//                       className="w-full hover:bg-gray-50 transition duration-300 ease-in-out"
//                       disabled={isLoading}
//                     >
//                       <Mail className="mr-2 h-4 w-4" /> Google
//                     </Button>
//                     <Button
//                       variant="outline"
//                       className="w-full hover:bg-gray-50 transition duration-300 ease-in-out"
//                       disabled={isLoading}
//                     >
//                       <Github className="mr-2 h-4 w-4" /> GitHub
//                     </Button>
//                   </div>
//                 </div>

//                 <p className="mt-2 text-center text-sm text-gray-600">
//                   Already have an account?{" "}
//                   <Button
//                     variant="link"
//                     className="p-0 h-auto font-semibold text-indigo-600 hover:text-indigo-500"
//                     disabled={isLoading}
//                   >
//                     Log in
//                   </Button>
//                 </p>
//               </div>
//             </motion.div>
//           </div>
//         </motion.div>
//         <motion.div
//           initial={{ opacity: 0, x: 20 }}
//           animate={{ opacity: 1, x: 0 }}
//           transition={{ duration: 0.5 }}
//           className="hidden md:block w-1/2 bg-gradient-to-br from-purple-600 via-blue-500 to-indigo-700 p-12 text-white relative overflow-hidden"
//         >
//           <div className="absolute inset-0 bg-grid-white/[0.2] bg-[size:20px_20px]" />
//           <div className="absolute inset-0 bg-gradient-to-t from-purple-600/50 to-transparent" />
//           <div className="relative z-10">
//             <ProductInfo />
//           </div>
//         </motion.div>
//       </div>
//     );
//   }

// }
