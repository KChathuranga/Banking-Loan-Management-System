import CustomerDashboard from "./CustomerDashboard";
import OfficerDashboard from "./OfficerDashboard";
import AdminDashboard from "./AdminDashboard";

function Dashboard() {

  const email = localStorage.getItem("email");

  if (email.includes("customer")) return <CustomerDashboard />;
  if (email.includes("officer")) return <OfficerDashboard />;
  if (email.includes("admin")) return <AdminDashboard />;

  return <div>No role detected</div>;
}

export default Dashboard;
