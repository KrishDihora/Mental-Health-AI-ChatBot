import dotenv from "dotenv";
dotenv.config();
import express from "express";
import cors from "cors";
import bodyParser from "body-parser";

import cookieParser from "cookie-parser";
import connectDB from "./config/DBConnect.js";
import rootRoute from "./routes/root.route.js";

const PORT = process.env.PORT || 5000;

const app = express();

app.use(cors());
app.use(cookieParser());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use("/api/v1", rootRoute);

connectDB()
  .then(() => {
    console.log("Connected to Database");
    app.listen(PORT, () => {
      console.log(`Server running on port ${PORT}`);
    });
  })
  .catch((err) => {
    console.log(err);
  });
