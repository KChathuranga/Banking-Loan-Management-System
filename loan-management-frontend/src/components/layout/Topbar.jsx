function Topbar() {

  const logout = () => {
    localStorage.clear();
    window.location.reload();
  };

  return (
    <div style={{
      background: "#fff",
      padding: 12,
      borderBottom: "1px solid #ddd",
      display: "flex",
      justifyContent: "space-between"
    }}>
      <strong>Loan Management</strong>
      <button className="secondary" onClick={logout}>Logout</button>
    </div>
  );
}

export default Topbar;
