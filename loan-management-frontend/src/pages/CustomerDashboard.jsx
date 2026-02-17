import { Routes, Route } from "react-router-dom";
import Layout from "../components/layout/Layout";
import LoanForm from "../components/loan/LoanForm";
import EmiTable from "../components/loan/EmiTable";

function CustomerDashboard() {
  return (
    // <Layout>
    //   <h2>Customer Dashboard</h2>

    //   <LoanForm />

    //   <div style={{ marginTop: 30 }}>
    //     <EmiTable />
    //   </div>
    // </Layout>
    <Layout>
      <Routes>
        <Route path="/" element={<LoanForm />} />
        <Route path="/loans" element={<LoanForm />} />
        <Route path="/emis" element={<EmiTable />} />
      </Routes>
    </Layout>
  );
}


export default CustomerDashboard;
