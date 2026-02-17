import { useState } from "react";
import API from "../../services/api";

function EmiTable() {

  const [loanId, setLoanId] = useState("");
  const [emis, setEmis] = useState([]);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const loadEmis = async () => {
    if (!loanId) {
      setMessage("Please enter a Loan ID");
      return;
    }

    setLoading(true);
    setMessage("");

    try {
      const res = await API.get(`/loans/${loanId}/emis`);
      setEmis(res.data);
      if (res.data.length === 0) {
        setMessage("No EMIs found for this loan");
      }
    } catch {
      setMessage("Failed to load EMI schedule");
    } finally {
      setLoading(false);
    }
  };

  const payEmi = async (emiId) => {
    try {
      await API.put(`/loans/emis/${emiId}/pay`);
      setMessage("EMI paid successfully");
      loadEmis(); // refresh list
    } catch {
      setMessage("Failed to pay EMI");
    }
  };

  return (
    <div className="card">
      <h3>EMI Schedule</h3>

      <div style={{ display: "flex", gap: 10, marginBottom: 14 }}>
        <input
          placeholder="Enter Loan ID"
          value={loanId}
          onChange={e => setLoanId(e.target.value)}
          style={{ padding: 8, borderRadius: 6, border: "1px solid #ccc" }}
        />
        <button onClick={loadEmis} disabled={loading}>
          {loading ? "Loading..." : "Load EMIs"}
        </button>
      </div>

      {emis.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>#</th>
              <th>Due Date</th>
              <th>Amount</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {emis.map(emi => (
              <tr key={emi.id}>
                <td>{emi.installmentNumber}</td>
                <td>{emi.dueDate}</td>
                <td>{emi.amount}</td>
                <td>
                  {emi.paid
                    ? <span style={{ color: "green" }}>PAID</span>
                    : <span style={{ color: "orange" }}>PENDING</span>}
                </td>
                <td>
                  <button
                    disabled={emi.paid}
                    onClick={() => payEmi(emi.id)}
                  >
                    Pay EMI
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {message && <p style={{ marginTop: 10 }}>{message}</p>}
    </div>
  );
}

export default EmiTable;
