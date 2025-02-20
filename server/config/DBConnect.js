import mongoose from "mongoose";

const connectDB = async () => {
  await mongoose.connect(process.env.MONGO_URI, {
    dbName: "chatbot",
  });
};

export default connectDB;
