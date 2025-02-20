import { AxiosError, AxiosResponse } from "axios";

export interface ApiError<T> extends AxiosError {
  response?: AxiosResponse<T>;
}

export interface SignupError {
  error: string;
}

export interface LoginError {
  error: string;
}

export interface User {
  _id?: string;
  firstName: string;
  lastName?: string;
  email: string;
  firebaseUid?: string;
  age?: number;
  gender?: string;
  location?: string;
  bio?: string;
  profilePic?: string;
  skills?: string[];
  createdAt?: Date;
  updatedAt?: Date;
}

export interface SignupProps {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface LoginProps {
  email: string;
  password: string;
}
