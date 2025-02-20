import zod from "zod";

const validateSchema = (schema) => (req, res, next) => {
  try {
    const data = req.body;
    const allowedFields = Object.keys(schema.shape);
    const extraFields = Object.keys(data).filter(
      (field) => !allowedFields.includes(field)
    );
    if (extraFields.length > 0) {
      return res.status(400).json({
        message: "Invalid Request",
        error: extraFields.map((field) => ({
          field,
          message: `${field} is not allowed`,
        })),
      });
    }
    schema.parse(data);
    next();
  } catch (error) {
    if (error instanceof zod.ZodError) {
      return res.status(400).json({
        message: "Validation Failed",
        error: error.errors.map((e) => ({
          field: e.path[0],
          message: e.message,
        })),
      });
    }

    next(error);
  }
};

export default validateSchema;
