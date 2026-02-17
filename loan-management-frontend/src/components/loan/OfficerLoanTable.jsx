import { useEffect, useState } from "react";
import API from "../../services/api";

function OfficerLoanTable() {

  const [loans, setLoans] = useState([]);
  const [message, setMessage] = useState("");

  const loadLoans = async () => {
    try {
      const res = await API.get("/loans/officer");
      setLoans(res.data);
    } catch (err) {
      setMessage("Failed to load loans");
    }
  };

  useEffect(() => {
    loadLoans();
  }, []);

  const approveLoan = async (loanId) => {
    try {
      await API.put(`/loans/${loanId}/approve`);
      setMessage("Loan approved");
      loadLoans();
    } catch {
      setMessage("Failed to approve loan");
    }
  };

  const rejectLoan = async (loanId) => {
    try {
      await API.put(`/loans/${loanId}/reject`);
      setMessage("Loan rejected");
      loadLoans();
    } catch {
      setMessage("Failed to reject loan");
    }
  };

  return (
    <div className="card">
      <h3>Loan Applications</h3>

      {loans.length > 0 ? (
        <table>
          <thead>
            <tr>
              <th>Loan ID</th>
              <th>Amount</th>
              <th>Duration</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {loans.map(loan => (
              <tr key={loan.loanId}>
                <td>{loan.loanId}</td>
                <td>{loan.loanAmount}</td>
                <td>{loan.durationMonths} months</td>
                {/* <td>
                  <span style={{
                    color:
                      loan.status === "PENDING" ? "orange" :
                      loan.status === "ACTIVE" ? "green" :
                      "gray"
                  }}>
                    {loan.status}
                  </span>
                </td> */}
                <td>
                    <span className={`badge ${loan.status.toLowerCase()}`}>
                        {loan.status}
                    </span>
                </td>
                <td>
                  {loan.status === "PENDING" && (
                    <>
                      <button onClick={() => approveLoan(loan.loanId)}>
                        Approve
                      </button>
                      <button
                        className="secondary"
                        onClick={() => rejectLoan(loan.loanId)}
                        style={{ marginLeft: 8 }}
                      >
                        Reject
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No loans available</p>
      )}

      {message && <p style={{ marginTop: 10 }}>{message}</p>}
    </div>
  );
}

export default OfficerLoanTable;
