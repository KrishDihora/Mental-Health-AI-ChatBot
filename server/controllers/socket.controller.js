import SocketChat from "../schemas/socketMessage.schema.js";

export const initChat = async (req, res) => {
  const { userId, email, name, title, message } = req.body;
  try {
    if (!userId || !email || !name || !title || !message) {
      return res.status(400).json({ error: "All fields are required" });
    }
    const newChat = await Chat.create({
      userId,
      email,
      name,
      title,
      message,
    });
    if (!newChat) {
      return res.status(500).json({ error: "Failed to create a new chat." });
    }
    return res.status(201).json({ ...newChat._doc });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

export const messageChat = async (req, res) => {
  const { conversationId, message, sender } = req.body;
  try {
    if (!conversationId || !message || !sender) {
      return res.status(400).json({ error: "All fields are required" });
    }
    const newMessage = await SocketChat.create({
      conversationId,
      message,
      sender,
    });

    if (!newMessage) {
      return res.status(500).json({ error: "Failed to create a new message." });
    }

    return res.status(201).json({ ...newMessage._doc });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

export const getChat = async (req, res) => {
  const { conversationId } = req.body;
  try {
    if (!conversationId) {
      return res.status(400).json({ error: "All fields are required" });
    }
    const chat = await SocketChat.find({ conversationId });
    if (!chat) {
      return res.status(404).json({ error: "Chat not found" });
    }
    return res.status(200).json({ chat });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
