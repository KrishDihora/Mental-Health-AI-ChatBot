import express from "express";
import {
  createSubscription,
  getSubscription,
  updateSubscription,
} from "../controllers/subscription.controller.js";
import multer from "multer";

const upload = multer();
const router = express.Router();

router.get("/get-subscription", getSubscription);
router.post("/create-subscription", upload.none(), createSubscription);
router.patch("/update-subscription", upload.none(), updateSubscription);

export default router;
