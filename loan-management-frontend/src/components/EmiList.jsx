import { useState } from "react";
import API from "../services/api";

function EmiList() {

  const [loanId, setLoanId] = useState("");
  const [emis, setEmis] = useState([]);

  const loadEmis = async () => {
    try {
      const res = await API.get(`/loans/${loanId}/emis`);
      setEmis(res.data);
    } catch {
      alert("Error loading EMI schedule");
    }
  };

  return (
    <div>
      <h2>View EMI Schedule</h2>

      <input
        placeholder="Loan ID"
        value={loanId}
        onChange={(e) => setLoanId(e.target.value)}
      />

      <button onClick={loadEmis}>Load EMI</button>

      <table border="1">
        <thead>
          <tr>
            <th>Installment</th>
            <th>Due Date</th>
            <th>Amount</th>
            <th>Paid</th>
          </tr>
        </thead>
        <tbody>
          {emis.map(emi => (
            <tr key={emi.id}>
              <td>{emi.installmentNumber}</td>
              <td>{emi.dueDate}</td>
              <td>{emi.amount}</td>
              <td>{emi.paid ? "Yes" : "No"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default EmiList;
