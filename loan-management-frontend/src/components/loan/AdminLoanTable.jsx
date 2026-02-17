import { useEffect, useState } from "react";
import API from "../../services/api";

function AdminLoanTable() {

  const [loans, setLoans] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    loadLoans();
  }, []);

  const loadLoans = async () => {
    try {
      const res = await API.get("/loans");
      setLoans(res.data);
    } catch {
      setMessage("Failed to load loans");
    }
  };

  const statusColor = (status) => {
    switch (status) {
      case "PENDING": return "orange";
      case "ACTIVE": return "green";
      case "REJECTED": return "red";
      case "CLOSED": return "gray";
      default: return "black";
    }
  };

  return (
    <div className="card">
      <h3>All Loans</h3>

      {loans.length > 0 ? (
        <table>
          <thead>
            <tr>
              <th>Loan ID</th>
              <th>Amount</th>
              <th>Duration</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {loans.map(loan => (
              <tr key={loan.loanId}>
                <td>{loan.loanId}</td>
                <td>{loan.loanAmount}</td>
                <td>{loan.durationMonths} months</td>
                <td>
                  <span style={{ color: statusColor(loan.status) }}>
                    {loan.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No loans found</p>
      )}

      {message && <p style={{ marginTop: 10 }}>{message}</p>}
    </div>
  );
}

export default AdminLoanTable;
