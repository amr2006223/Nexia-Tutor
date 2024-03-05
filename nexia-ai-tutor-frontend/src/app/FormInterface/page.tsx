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
 const record2 =
   '{"id":"ed78e3ea-de90-4b54-a296-c6a9f8284b0f","record":{"Gender":"2","Nativelang":"0","Otherlang":"0","Age":"8","Clicks1":"0","Hits1":"0","Misses1":"0","Score1":"0","Accuracy1":"0","Missrate1":"0","Clicks2":"3","Hits2":"1","Misses2":"0","Score2":"1","Accuracy2":"0.333333","Missrate2":"0","Clicks3":"3","Hits3":"0","Misses3":"0","Score3":"0","Accuracy3":"0","Missrate3":"0","Clicks4":"6","Hits4":"4","Misses4":"2","Score4":"8","Accuracy4":"0.666667","Missrate4":"0.333333","Clicks5":"2","Hits5":"2","Misses5":"0","Score5":"2","Accuracy5":"1","Missrate5":"0","Clicks6":"2","Hits6":"0","Misses6":"2","Score6":"0","Accuracy6":"0","Missrate6":"1","Clicks7":"5","Hits7":"5","Misses7":"0","Score7":"5","Accuracy7":"1","Missrate7":"0","Clicks8":"3","Hits8":"2","Misses8":"1","Score8":"2","Accuracy8":"0.666667","Missrate8":"0.333333","Clicks9":"4","Hits9":"4","Misses9":"0","Score9":"4","Accuracy9":"1","Missrate9":"0","Clicks10":"6","Hits10":"6","Misses10":"0","Score10":"6","Accuracy10":"1","Missrate10":"0","Clicks11":"2","Hits11":"1","Misses11":"1","Score11":"1","Accuracy11":"0.5","Missrate11":"0.5","Clicks12":"0","Hits12":"0","Misses12":"0","Score12":"0","Accuracy12":"0","Missrate12":"0","Clicks13":"0","Hits13":"0","Misses13":"0","Score13":"0","Accuracy13":"0","Missrate13":"0","Clicks14":"2","Hits14":"0","Misses14":"2","Score14":"0","Accuracy14":"0","Missrate14":"1","Clicks15":"3","Hits15":"3","Misses15":"0","Score15":"3","Accuracy15":"1","Missrate15":"0","Clicks16":"5","Hits16":"4","Misses16":"1","Score16":"4","Accuracy16":"0.8","Missrate16":"0.2","Clicks17":"3","Hits17":"3","Misses17":"0","Score17":"3","Accuracy17":"1","Missrate17":"0","Clicks18":"2","Hits18":"2","Misses18":"0","Score18":"2","Accuracy18":"1","Missrate18":"0","Clicks19":"2","Hits19":"2","Misses19":"0","Score19":"2","Accuracy19":"1","Missrate19":"0","Clicks20":"4","Hits20":"1","Misses20":"3","Score20":"1","Accuracy20":"0.25","Missrate20":"0.75","Clicks21":"3","Hits21":"3","Misses21":"0","Score21":"3","Accuracy21":"1","Missrate21":"0","Clicks22":"3","Hits22":"3","Misses22":"0","Score22":"3","Accuracy22":"1","Missrate22":"0","Clicks23":"3","Hits23":"3","Misses23":"0","Score23":"3","Accuracy23":"1","Missrate23":"0","Clicks24":"1","Hits24":"1","Misses24":"0","Score24":"1","Accuracy24":"1","Missrate24":"0","Clicks25":"3","Hits25":"1","Misses25":"2","Score25":"1","Accuracy25":"0.333333","Missrate25":"0.666667","Clicks26":"2","Hits26":"2","Misses26":"0","Score26":"2","Accuracy26":"1","Missrate26":"0","Clicks27":"1","Hits27":"0","Misses27":"1","Score27":"0","Accuracy27":"0","Missrate27":"1","Clicks28":"0","Hits28":"0","Misses28":"0","Score28":"0","Accuracy28":"0","Missrate28":"0","Clicks29":"25","Hits29":"1","Misses29":"2","Score29":"1","Accuracy29":"0.04","Missrate29":"0.08","Clicks30":"2","Hits30":"2","Misses30":"0","Score30":"2","Accuracy30":"1","Missrate30":"0","Clicks31":"29","Hits31":"2","Misses31":"0","Score31":"2","Accuracy31":"0.0689655","Missrate31":"0","Clicks32":"12","Hits32":"1","Misses32":"2","Score32":"1","Accuracy32":"0.0833333","Missrate32":"0.166667"}}';
  const handleInputChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ): void => {
    setFormData({
      // ...formData,
      record: record2
      // record: event.target.value,
    });
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>): Promise<void> => {
    event.preventDefault();
    console.log("Form submitted:", record2);
    // You can perform further actions here
      try {
      const response = await fetch("http://127.0.0.1:5000/predict", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify( record2 ),
      });

      if (!response.ok) {
        throw new Error("API request failed");
      }

      const responseData = await response.json();
      console.log("API response:", responseData);
      //  router.push("/screening/report?prediction="+responseData);
       
    } catch (error) {
      console.error("Error making API request:", error);
    }
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
