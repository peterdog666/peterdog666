# 从 0 到 1 的 AI 小应用（Spring Boot + 前端）

这是一个给新手准备的最小可用 AI 应用：
- 前端：原生 HTML/CSS/JS 聊天页面
- 后端：Spring Boot（Java 17）
- 模型：OpenAI API（未配置 Key 时自动进入演示模式）

## 快速开始

```bash
cp .env.example .env
# 填 OPENAI_API_KEY
mvn spring-boot:run
# 或者你本机有 mvn: mvn spring-boot:run
```

浏览器打开：`http://localhost:8080`

## 一键容器启动

```bash
docker compose up --build
```

浏览器打开：`http://localhost:8000`

## 上线教程

见：`docs/DEPLOY.md`
