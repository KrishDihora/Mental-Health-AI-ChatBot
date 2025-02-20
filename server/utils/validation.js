import zod from "zod";

export const signupSchema = zod.object({
  firstName: zod
    .string({ required_error: "First Name is required" })
    .min(3, "First Name must be atleast 3 characters")
    .max(56, "First Name must be less than 56 characters"),
  lastName: zod
    .string()
    .min(3, "Last Name must be atleast 3 characters")
    .max(56, "Last Name must be less than 56 characters")
    .optional(),
  email: zod
    .string({ required_error: "Email is required" })
    .email()
    .min(1, "Email is required"),
  password: zod
    .string()
    .min(8, "Password must be atleast 8 characters")
    .max(56, "Password must be less than 56 characters"),
  notificationToken: zod.string().optional(),
  userType: zod.string().optional(),
  keyWords: zod.array(zod.string()).optional(),
});

export const loginSchema = zod.object({
  email: zod
    .string({ required_error: "Email is required" })
    .email()
    .min(1, "Email is required"),
  password: zod
    .string({ required_error: "Password is required" })
    .min(8, "Password must be atleast 8 characters")
    .max(56, "Password must be less than 56 characters"),
});
