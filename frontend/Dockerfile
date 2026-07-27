FROM node:lts-alpine AS build-stage
WORKDIR /app

COPY frontend/package*.json ./
RUN npm install
COPY src/main/frontend ./src/main/frontend
COPY .env ./
RUN npm run build

FROM nginx:stable-alpine AS production-stage
COPY --from=build-stage /app/src/main/frontend/dist /usr/share/nginx/html
COPY frontend/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]