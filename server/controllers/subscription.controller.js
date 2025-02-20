import Subscription from "../schemas/subscription.schema.js";

export const createSubscription = async (req, res) => {
  const { plan1, plan2, plan3 } = req.body;
  try {
    const newSubscription = await Subscription.create({
      plan1,
      plan2,
      plan3,
    });
    return res.status(201).json({ newSubscription });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

export const getSubscription = async (req, res) => {
  try {
    const subscription = await Subscription.find({});
    return res.status(200).json({ subscription });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

export const updateSubscription = async (req, res) => {
  const { plan1, plan2, plan3 } = req.body;
  try {
    const updatedSubscription = await Subscription.findOneAndUpdate(
      { _id: req.params.id },
      { plan1, plan2, plan3 },
      { new: true }
    );
    if (!updatedSubscription) {
      return res.status(404).json({ error: "Subscription not found" });
    }
    return res.status(200).json({ updatedSubscription });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
