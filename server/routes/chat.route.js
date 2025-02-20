import express from "express";
import {
  getChat,
  //   chat,
  getConversations,
  newConversation,
  startChat,
} from "../controllers/chat.controller.js";

import multer from "multer";

const upload = multer();

const router = express.Router();

router.post("/new-conversation", upload.none(), newConversation);
router.post("/get-conversations", upload.none(), getConversations);
router.post("/start-chat", upload.none(), startChat);
router.post("/get-chats", upload.none(), getChat);
// router.post("/get-chat", upload.none(), getChat);

export default router;
