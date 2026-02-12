import LoanApply from "../components/LoanApply";
import LoanList from "../components/LoanList";
import EmiList from "../components/EmiList";

function Dashboard() {
  return (
    <div>
      <h1>Loan Management Dashboard</h1>
      <LoanApply />
      <hr />
      <LoanList />
      <hr />
      <EmiList />
    </div>
  );
}

export default Dashboard;
