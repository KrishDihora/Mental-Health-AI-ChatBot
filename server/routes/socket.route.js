import express from "express";

import multer from "multer";
import { initChat, messageChat } from "../controllers/socket.controller.js";
import { getChat } from "../controllers/chat.controller.js";

const upload = multer();

const router = express.Router();

router.post("/init-chat", upload.none(), initChat);
router.post("/message-chat", upload.none(), messageChat);
router.post("/get-chat", upload.none(), getChat);

export default router;
