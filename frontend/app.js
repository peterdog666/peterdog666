const chatForm = document.getElementById("chatForm");
const messageInput = document.getElementById("message");
const chatBox = document.getElementById("chatBox");

function appendBubble(role, text) {
  const div = document.createElement("div");
  div.className = `bubble ${role}`;
  div.textContent = `${role === "user" ? "你" : "AI"}：${text}`;
  chatBox.appendChild(div);
  chatBox.scrollTop = chatBox.scrollHeight;
}

chatForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const message = messageInput.value.trim();
  if (!message) return;

  appendBubble("user", message);
  messageInput.value = "";

  const btn = chatForm.querySelector("button");
  btn.disabled = true;
  btn.textContent = "发送中...";

  try {
    const res = await fetch("/api/chat", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ message }),
    });

    const data = await res.json();
    if (!res.ok) {
      appendBubble("ai", `出错了：${data.detail || "未知错误"}`);
      return;
    }

    appendBubble("ai", `${data.reply}\n\n（模型：${data.model}）`);
  } catch (error) {
    appendBubble("ai", `网络错误：${error.message}`);
  } finally {
    btn.disabled = false;
    btn.textContent = "发送";
  }
});
