// pages/api/index.js

export default function handler(req, res) {
    // Set CORS headers
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);
  
    // Handle different HTTP methods
    if (req.method === 'GET') {
      // Handle GET request
      res.status(200).json({ message: 'GET request received' });
    } else if (req.method === 'POST') {
      // Handle POST request
      res.status(200).json({ message: 'POST request received' });
    } else {
      // Handle other HTTP methods
      res.status(200).json({ message: 'Request received' });
    }
  }
  