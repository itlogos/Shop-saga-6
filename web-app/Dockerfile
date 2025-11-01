## Dev mode container for Vite
#FROM node:20
#WORKDIR /app
#COPY package*.json ./
#
## Стабильные сетевые настройки npm
#RUN npm config set registry "https://registry.npmjs.org/" \
# && npm config set fetch-retries 5 \
# && npm config set fetch-retry-maxtimeout 180000 \
# && npm config set fetch-timeout 180000 \
# && npm config set audit false \
# && npm config set fund false \
# && npm config set progress false
#
## Ключ: отключаем optional, чтобы npm не проходил по всем @rollup/* платформам
#ENV npm_config_optional=false
#
## Установка зависимостей (ci, если есть lockfile)
#RUN node -v && npm -v \
# && if [ -f package-lock.json ]; then \
#      npm ci --no-audit --no-fund --legacy-peer-deps --verbose; \
#    else \
#      npm install --no-audit --no-fund --legacy-peer-deps --verbose; \
#    fi
#
#COPY . .
#EXPOSE 5173
#CMD ["npm","run","dev","--","--host","0.0.0.0"]

## web-app/Dockerfile (фрагмент)
#FROM node:20-alpine
#
#WORKDIR /app
#COPY package*.json ./
#
## КЛЮЧЕВОЕ: чистим кэш, удаляем lock (workaround бага), ставим с optional
#RUN rm -rf node_modules package-lock.json \
# && npm cache clean --force \
# && npm i --include=optional --no-audit --no-fund
#
## Копируем исходники после установки зависимостей
#COPY . .
#
#EXPOSE 5173
#CMD ["npm","run","dev","--","--host","0.0.0.0"]


# syntax=docker/dockerfile:1.7
FROM node:20-bookworm-slim

WORKDIR /app

# не игнорируй package*.json в .dockerignore!
COPY package*.json ./

# кэш npm + optional deps
#RUN --mount=type=cache,target=/root/.npm \
#    npm ci --include=optional --no-audit --no-fund
RUN npm install
# теперь весь код
COPY . .

EXPOSE 5173
CMD ["npm","run","dev","--","--host","0.0.0.0"]

