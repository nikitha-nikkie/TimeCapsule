import { useState } from "react";
import "./App.css";

function App() {
  const [title, setTitle] = useState("");
  const [message, setMessage] = useState("");
  const [recipientEmail, setRecipientEmail] = useState("");
  const [deliveryDateTime, setDeliveryDateTime] = useState("");
  const [photo, setPhoto] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (deliveryDateTime && new Date(deliveryDateTime) <= new Date()) {
      alert("Please select a future delivery date and time.");
      return;
    }

    const formData = new FormData();
    formData.append("title", title);
    formData.append("message", message);
    formData.append("recipientEmail", recipientEmail);
    formData.append("deliveryDateTime", deliveryDateTime);

    if (photo) {
      formData.append("photo", photo);
    }

    try {
      setLoading(true);

      const response = await fetch(
        "http://localhost:8080/api/capsules",
        {
          method: "POST",
          body: formData,
        }
      );

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || "Could not create capsule.");
      }

      alert("TimeCapsule created successfully! 💌");

      setTitle("");
      setMessage("");
      setRecipientEmail("");
      setDeliveryDateTime("");
      setPhoto(null);

      e.target.reset();
    } catch (error) {
      alert("Error: " + error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1>💌 TimeCapsule</h1>
      <p>Send a memory to the future ✨</p>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Capsule Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />

        <textarea
          placeholder="Write your message..."
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          required
        />

        <input
          type="email"
          placeholder="Recipient Email"
          value={recipientEmail}
          onChange={(e) => setRecipientEmail(e.target.value)}
          required
        />

        <label htmlFor="deliveryDateTime">
          Delivery Date and Time
        </label>
        <input
          id="deliveryDateTime"
          type="datetime-local"
          value={deliveryDateTime}
          onChange={(e) => setDeliveryDateTime(e.target.value)}
          required
        />

        <label htmlFor="photo">Attach a photo (optional)</label>
        <input
          id="photo"
          type="file"
          accept="image/*"
          onChange={(e) => setPhoto(e.target.files?.[0] || null)}
        />

        {photo && <p>Selected: {photo.name}</p>}

        <button type="submit" disabled={loading}>
          {loading ? "Creating..." : "Create TimeCapsule ❤️"}
        </button>
      </form>
    </div>
  );
}

export default App;