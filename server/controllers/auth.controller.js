import User from "../schemas/user.schema.js";
import jwt from "jsonwebtoken";

// import passport from "../config/passport.js";
const generateAccessAndRefreshToken = async (userId) => {
  const user = await User.findById(userId);

  const accessToken = user.createAccessToken();

  const refreshToken = user.createRefreshToken();

  user.refreshToken = refreshToken;

  await user.save({
    validateBeforeSave: false,
  });

  return { accessToken, refreshToken };
};

const cookieOptions = {
  httpOnly: true,
  secure: true,
};

export const login = async (req, res) => {
  try {
    const { email, password } = req.body;

    if (!email || !password) {
      return res.status(400).json({ error: "All fields are required" });
    }

    const user = await User.findOne({ email });

    if (!user) {
      return res.status(400).json({ error: "Invalid Credentials" });
    }

    const isPasswordValid = await user.validatePassword(password);

    if (!isPasswordValid) {
      return res.status(400).json({ error: "Invalid Credentials" });
    }

    const loogedInUser = await User.findById(user._id).select(
      "-password -refreshToken -__v"
    );

    const { accessToken, refreshToken } = await generateAccessAndRefreshToken(
      user._id
    );

    return res
      .status(200)
      .cookie("refreshToken", refreshToken, cookieOptions)
      .cookie("accessToken", accessToken, cookieOptions)
      .json({
        message: "User authenticated successfully",
        user: loogedInUser,
        accessToken,
        refreshToken,
      });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

export const signup = async (req, res) => {
  try {
    const {
      firstName,
      lastName,
      email,
      password,
      notificationToken,
      userType,
    } = req.body;

    const existingUser = await User.findOne({ email });

    if (existingUser) {
      return res.status(400).json({ error: "User Already Exists" });
    }

    const newUser = await User.create({
      firstName,
      lastName,
      email,
      password,
      notificationToken,
      userType,
    });

    return res.status(201).json({ message: "User created successfully" });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

export const logout = async (req, res) => {
  try {
    await User.findByIdAndUpdate(
      req.user._id,
      {
        $unset: { refreshToken: 1 },
      },
      { new: true }
    );

    return res
      .status(200)
      .clearCookie("refreshToken", cookieOptions)
      .clearCookie("accessToken", cookieOptions)
      .json({ message: "User logged out successfully" });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

export const refreshAccessToken = async (req, res) => {
  try {
    const incomingRefreshToken =
      req.cookies.refreshToken || req.body.refreshToken;

    if (!incomingRefreshToken) {
      return res.status(400).json({ error: "Refresh token is required" });
    }

    const decodedToken = jwt.verify(
      incomingRefreshToken,
      process.env.REFRESH_TOKEN_SECRET
    );

    const { _id } = decodedToken;

    const user = await User.findById(_id);

    if (!user) {
      return res.status(401).json({ error: "Invalid refresh token" });
    }

    if (user?.refreshToken !== incomingRefreshToken) {
      return res.status(401).json({ error: "Invalid refresh token" });
    }

    const { accessToken, refreshToken } = await generateAccessAndRefreshToken(
      user._id
    );

    return res
      .status(201)
      .cookie("refreshToken", refreshToken, cookieOptions)
      .cookie("accessToken", accessToken, cookieOptions)
      .json({
        message: "Access token refreshed successfully",
        accessToken,
        refreshToken,
      });
  } catch (error) {
    // console.log(error);
    return res.status(500).json({ error: error.message });
  }
};

export const getUser = async (req, res) => {
  try {
    const user = req.user;

    res.status(200).json({ user });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
