import Layout from "../components/layout/Layout";
import OfficerLoanTable from "../components/loan/OfficerLoanTable";

function OfficerDashboard() {
  return (
    <Layout>
      <h2>Loan Officer Dashboard</h2>
      <OfficerLoanTable />
    </Layout>
  );
}

export default OfficerDashboard;
