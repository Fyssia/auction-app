import { useEffect, useState } from "react";

const API_URL = import.meta.env.VITE_API_URL;

import './App.css'


function App() {
  const [ping, setPing] = useState("...");

  useEffect(() => {
    fetch(`${API_URL}/api/ping`)
      .then((response) => response.text())
      .then(setPing)
      .catch(() => setPing("error"));
  }, []);

  return (
    <div style={{ padding: 24 }}>
      <h1>Auction App</h1>
      <p>Backend ping: {ping}</p>
    </div>
  );
}

export default App
