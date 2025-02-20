import mongoose from "mongoose";

const subscriptionSchema = new mongoose.Schema(
  {
    plan1: {
      type: String,
      default: "99",
    },
    plan2: {
      type: String,
      default: "199",
    },
    plan3: {
      type: String,
      default: "299",
    },
  },
  {
    timestamps: true,
  }
);

const Subscription = mongoose.model("Subscription", subscriptionSchema);

export default Subscription;
