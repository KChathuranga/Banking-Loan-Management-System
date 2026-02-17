import { useState } from "react";
import API from "../../services/api";
import Input from "../common/Input";

function LoanForm() {

  const [form, setForm] = useState({
    userId: "",
    loanAmount: "",
    interestRate: "",
    durationMonths: ""
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const submitLoan = async () => {

    // simple frontend validation
    if (!form.userId || !form.loanAmount || !form.durationMonths) {
      setMessage("Please fill all required fields");
      return;
    }

    setLoading(true);
    setMessage("");

    try {
      const res = await API.post("/loans", {
        userId: Number(form.userId),
        loanAmount: Number(form.loanAmount),
        interestRate: Number(form.interestRate || 0),
        durationMonths: Number(form.durationMonths)
      });

      setMessage(`Loan applied successfully (ID: ${res.data.loanId})`);
      setForm({
        userId: "",
        loanAmount: "",
        interestRate: "",
        durationMonths: ""
      });

    } catch (err) {
      setMessage("Failed to apply loan");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card" style={{ maxWidth: 420 }}>
      <h3>Apply for a Loan</h3>

      <Input
        label="User ID"
        name="userId"
        value={form.userId}
        onChange={handleChange}
        placeholder="Enter your user ID"
      />

      <Input
        label="Loan Amount"
        name="loanAmount"
        value={form.loanAmount}
        onChange={handleChange}
        placeholder="e.g. 500000"
      />

      <Input
        label="Interest Rate (%)"
        name="interestRate"
        value={form.interestRate}
        onChange={handleChange}
        placeholder="e.g. 10"
      />

      <Input
        label="Duration (Months)"
        name="durationMonths"
        value={form.durationMonths}
        onChange={handleChange}
        placeholder="e.g. 24"
      />

      <button onClick={submitLoan} disabled={loading}>
        {loading ? "Submitting..." : "Apply Loan"}
      </button>

      {message && (
        <p style={{ marginTop: 10, fontSize: 14 }}>{message}</p>
      )}
    </div>
  );
}

export default LoanForm;
