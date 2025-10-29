# Dev mode container for Vite
FROM node:20
WORKDIR /app
COPY package*.json ./

# Стабильные сетевые настройки npm
RUN npm config set registry "https://registry.npmjs.org/" \
 && npm config set fetch-retries 5 \
 && npm config set fetch-retry-maxtimeout 180000 \
 && npm config set fetch-timeout 180000 \
 && npm config set audit false \
 && npm config set fund false \
 && npm config set progress false

# Ключ: отключаем optional, чтобы npm не проходил по всем @rollup/* платформам
ENV npm_config_optional=false

# Установка зависимостей (ci, если есть lockfile)
RUN node -v && npm -v \
 && if [ -f package-lock.json ]; then \
      npm ci --no-audit --no-fund --legacy-peer-deps --verbose; \
    else \
      npm install --no-audit --no-fund --legacy-peer-deps --verbose; \
    fi

COPY . .
EXPOSE 5173
CMD ["npm","run","dev","--","--host","0.0.0.0"]