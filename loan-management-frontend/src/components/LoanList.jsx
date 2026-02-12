import { useEffect, useState } from "react";
import API from "../services/api";

function LoanList() {

  const [loans, setLoans] = useState([]);

  useEffect(() => {
    API.get("/loans")
      .then(res => setLoans(res.data))
      .catch(() => alert("Error loading loans"));
  }, []);

  return (
    <div>
      <h2>All Loans</h2>

      <table border="1">
        <thead>
          <tr>
            <th>ID</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Duration</th>
          </tr>
        </thead>
        <tbody>
          {loans.map(loan => (
            <tr key={loan.loanId}>
              <td>{loan.loanId}</td>
              <td>{loan.loanAmount}</td>
              <td>{loan.status}</td>
              <td>{loan.durationMonths}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default LoanList;
