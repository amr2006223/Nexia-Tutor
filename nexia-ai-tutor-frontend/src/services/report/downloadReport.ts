import axios from "axios";

export const getReport = async (
  user_id: string
) => {
  try {
    const response = await axios.get(`${process.env.REPORT_API}pdf/down/${user_id}`, {
      responseType: 'blob' // Set responseType to 'blob' for binary data
    });
    
    const blob = new Blob([response.data]);
    const filename = response.headers['content-disposition'].split('filename=')[1];
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    document.body.appendChild(link); // Append before setting href
    link.href = url;
    link.setAttribute('download', filename);
    link.click();
    document.body.removeChild(link); // Remove after clicking
    
    return response; // You may not need to return response here
  } catch (error) {
    console.error(error);
    throw error; // Re-throw the error to handle it outside this function
  }
};
