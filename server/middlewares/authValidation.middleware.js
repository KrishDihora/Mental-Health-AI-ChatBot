import User from "../schemas/user.schema.js";
import jwt from "jsonwebtoken";

const authValidation = async (req, res, next) => {
  try {
    const token =
      req.cookies?.accessToken ||
      req.headers?.authorization?.replace("Bearer ", "");

    if (!token) {
      return res.status(401).json({ error: "Unauthorized" });
    }

    const decodedToken = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET);

    const { _id } = decodedToken;

    const user = await User.findById(_id).select("-password -refreshToken");

    if (!user) {
      return res.status(401).json({ error: "Unauthorized" });
    }

    req.user = user;

    next();
  } catch (error) {
    return res.status(401).json({ error: error.message });
  }
};

export default authValidation;
