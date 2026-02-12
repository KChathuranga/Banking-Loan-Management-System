import { useState } from "react";
import API from "../services/api";

function LoanApply() {

  const [form, setForm] = useState({
    userId: "",
    loanAmount: "",
    interestRate: "",
    durationMonths: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const submitLoan = async () => {
    try {
      const response = await API.post("/loans", form);
      alert("Loan Applied Successfully. ID: " + response.data.loanId);
    } catch (error) {
      alert("Error applying loan");
    }
  };

  return (
    <div>
      <h2>Apply Loan</h2>

      <input name="userId" placeholder="User ID" onChange={handleChange} />
      <input name="loanAmount" placeholder="Loan Amount" onChange={handleChange} />
      <input name="interestRate" placeholder="Interest Rate" onChange={handleChange} />
      <input name="durationMonths" placeholder="Duration (Months)" onChange={handleChange} />

      <button onClick={submitLoan}>Apply</button>
    </div>
  );
}

export default LoanApply;
