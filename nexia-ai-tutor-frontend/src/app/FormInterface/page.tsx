// FormInterface.tsx
"use client";
import React, { useState } from "react";
import { TextField, Button, Container, Typography } from "@mui/material";

interface FormData {
  record: string;
}

const FormInterface: React.FC = () => {
  const [formData, setFormData] = useState<FormData>({
    record: "",
  });

  const handleInputChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ): void => {
    setFormData({
      ...formData,
      record: event.target.value,
    });
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>): void => {
    event.preventDefault();
    console.log("Form submitted:", formData.record);
    // You can perform further actions here
  };

  return (
    <Container component="main" maxWidth="xs">
      <Typography component="h1" variant="h5">
        Get prediction and insert user data to report service for later use
      </Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          fullWidth
          margin="normal"
          label="Record"
          variant="outlined"
          id="record"
          name="record"
          value={formData.record}
          onChange={handleInputChange}
          InputProps={{ style: { color: "black", backgroundColor: "beige" } }}
        />
        <Button type="submit" variant="contained" color="primary" fullWidth>
          Submit
        </Button>
      </form>
    </Container>
  );
};

export default FormInterface;
