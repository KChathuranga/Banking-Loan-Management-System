import { useState } from "react";
import API from "../services/api";

function Login({ onLogin }) {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const login = async () => {
  try {
    // 1. Call login API with JSON body (NO auth header)
    await API.post("/auth/login", {
      email,
      password
    });

    // 2. Create Basic Auth token AFTER successful login
    const token = btoa(email + ":" + password);

    // 3. Store token
    localStorage.setItem("auth", token);
    localStorage.setItem("email", email);

    onLogin();

  } catch (err) {
    alert("Invalid credentials");
  }
  };

  return (
    <div style={{
      display: "flex",
      height: "100vh",
      justifyContent: "center",
      alignItems: "center"
    }}>
      <div className="card" style={{ width: 300 }}>
        <h2>Login</h2>
        <input placeholder="Email" onChange={e => setEmail(e.target.value)} />
        <br /><br />
        <input type="password" placeholder="Password"
               onChange={e => setPassword(e.target.value)} />
        <br /><br />
        <button onClick={login}>Login</button>
      </div>
    </div>
  );
}

export default Login;
