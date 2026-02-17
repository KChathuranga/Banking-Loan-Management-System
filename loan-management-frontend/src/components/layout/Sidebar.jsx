import { NavLink } from "react-router-dom";

function Sidebar() {

  const linkStyle = {
    display: "block",
    padding: "8px 0",
    color: "white",
    textDecoration: "none"
  };

  const activeStyle = {
    fontWeight: "bold",
    color: "#38bdf8"
  };

  return (
    <div style={{
      width: 220,
      background: "#1e293b",
      color: "white",
      minHeight: "100vh",
      padding: 20
    }}>
      <h3>Bank System</h3>

      <NavLink to="/" style={linkStyle}
        className={({ isActive }) => isActive ? "active" : ""}
      >
        Dashboard
      </NavLink>

      <NavLink to="/loans" style={linkStyle}>
        Loans
      </NavLink>

      <NavLink to="/emis" style={linkStyle}>
        EMIs
      </NavLink>
    </div>
  );
}

export default Sidebar;
