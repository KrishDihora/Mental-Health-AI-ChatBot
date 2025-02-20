import mongoose from "mongoose";

const socketMessageSchema = new mongoose.Schema(
  {
    conversationId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Conversation",
      required: true,
    },
    message: {
      type: String,
      required: true,
    },
    sender: {
      type: String,
      required: true,
    },
  },
  { timestamps: true }
);

const SocketChat = mongoose.model("SocketChat", socketMessageSchema);

export default SocketChat;
