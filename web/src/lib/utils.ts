import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function handleChange<T>({
  e,
  data,
  setData,
}: {
  e: React.ChangeEvent<HTMLInputElement>;
  data: T;
  setData: React.Dispatch<React.SetStateAction<T>>;
}) {
  const { name, value } = e.target;
  setData({ ...data, [name]: value });
}
