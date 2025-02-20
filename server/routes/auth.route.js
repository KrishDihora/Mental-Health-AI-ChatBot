import express, { urlencoded } from "express";
import {
  login,
  signup,
  getUser,
  logout,
  refreshAccessToken,
} from "../controllers/auth.controller.js";
import validateSchema from "../middlewares/validateSchema.middleware.js";
import { signupSchema, loginSchema } from "../utils/validation.js";
import authValidation from "../middlewares/authValidation.middleware.js";
import multer from "multer";

const upload = multer();

const router = express.Router();

router.post("/login", upload.none(), validateSchema(loginSchema), login);
router.post("/signup", upload.none(), validateSchema(signupSchema), signup);
router.post("/logout", authValidation, logout);
router.get("/refresh", authValidation, refreshAccessToken);
router.get("/profile", authValidation, getUser);

export default router;
