import Layout from "../components/layout/Layout";
import AdminLoanTable from "../components/loan/AdminLoanTable";

function AdminDashboard() {
  return (
    <Layout>
      <h2>Admin Dashboard</h2>
      <AdminLoanTable />
    </Layout>
  );
}

export default AdminDashboard;
