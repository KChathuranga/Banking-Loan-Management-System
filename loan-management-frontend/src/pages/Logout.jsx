function Logout({ onLogout }) {

  const logout = () => {
    localStorage.clear();
    onLogout();
  };

  return <button onClick={logout}>Logout</button>;
}

export default Logout;
