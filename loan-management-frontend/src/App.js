import { Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import CustomerDashboard from "./pages/CustomerDashboard";
import OfficerDashboard from "./pages/OfficerDashboard";
import AdminDashboard from "./pages/AdminDashboard";

function App() {

  const loggedIn = !!localStorage.getItem("auth");
  const email = localStorage.getItem("email");

  if (!loggedIn) {
    return <Login onLogin={() => window.location.reload()} />;
  }

  if (email.includes("customer")) {
    return (
      <Routes>
        <Route path="/*" element={<CustomerDashboard />} />
      </Routes>
    );
  }

  if (email.includes("officer")) {
    return (
      <Routes>
        <Route path="/*" element={<OfficerDashboard />} />
      </Routes>
    );
  }

  if (email.includes("admin")) {
    return (
      <Routes>
        <Route path="/*" element={<AdminDashboard />} />
      </Routes>
    );
  }

  return <div>No role detected</div>;
}

export default App;
