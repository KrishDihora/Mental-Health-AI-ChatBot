import mongoose from "mongoose";

const chatSchema = new mongoose.Schema(
  {
    conversationId: {
      type: String,
      required: true,
    },
    message: {
      type: String,
      required: true,
    },
    result: {
      type: String,
    },
    sender: {
      type: String,
      required: true,
    },
    resultId: {
      type: String,
    },
    contentType: {
      type: String,
      default: "text",
    },
    imageUrl: {
      type: String,
    },
  },
  {
    timestamps: true,
  }
);

const Chat = mongoose.model("Chat", chatSchema);

export default Chat;
