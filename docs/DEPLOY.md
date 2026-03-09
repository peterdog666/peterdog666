# AI 应用部署上线指南（Spring Boot 新手版）

## 1. 本地启动

```bash
cp .env.example .env
# 编辑 .env，填入 OPENAI_API_KEY
export $(grep -v '^#' .env | xargs)
mvn spring-boot:run
```

打开浏览器访问：`http://localhost:8080`

---

## 2. Docker 启动（推荐）

```bash
cp .env.example .env
# 编辑 .env
docker compose up --build -d
```

查看日志：
```bash
docker compose logs -f
```

---

## 3. 上线方案 A：Railway（最简单）

1. 注册 Railway。
2. 新建 Project，选择你的 GitHub 仓库。
3. 配置启动命令：
   `java -jar app.jar --server.port=$PORT`
4. 配置环境变量：
   - `OPENAI_API_KEY`
   - `OPENAI_MODEL`（可选）
5. 点击 Deploy，等待完成后获得公网链接。

---

## 4. 上线方案 B：云服务器（Ubuntu + Docker）

```bash
git clone <你的仓库地址>
cd <仓库目录>
cp .env.example .env
# 编辑 .env

docker compose up --build -d
```

如果有 Nginx + 域名，反向代理到 `127.0.0.1:8000` 即可。

---

## 5. 生产建议

- 不要把 `.env` 提交到 Git。
- API Key 仅放在部署平台环境变量。
- 建议开启请求限流，避免 Key 被滥用。
- 想支持多轮记忆时，可增加数据库（PostgreSQL/Redis）。
