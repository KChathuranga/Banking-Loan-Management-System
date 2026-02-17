function Input({ label, ...props }) {
  return (
    <div style={{ marginBottom: 14 }}>
      <label style={{ display: "block", marginBottom: 6, fontSize: 14 }}>
        {label}
      </label>
      <input
        {...props}
        style={{
          width: "100%",
          padding: 8,
          borderRadius: 6,
          border: "1px solid #ccc"
        }}
      />
    </div>
  );
}

export default Input;
