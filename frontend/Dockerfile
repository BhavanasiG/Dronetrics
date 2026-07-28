FROM node:lts-alpine AS build-stage

WORKDIR /app/frontend
COPY frontend/package*.json ./
COPY frontend/vite.config.ts ./

RUN npm install

RUN ln -s /app/frontend/node_modules /app/node_modules

WORKDIR /app
COPY src/main/frontend ./src/main/frontend

ARG VITE_DRONE_ECASE_API_URL
ARG VITE_DRONE_DIGITAL_API_URL
ARG VITE_JIRA_TICKET_BROWSE_URL
ENV VITE_DRONE_ECASE_API_URL=$VITE_DRONE_ECASE_API_URL
ENV VITE_DRONE_DIGITAL_API_URL=$VITE_DRONE_DIGITAL_API_URL
ENV VITE_JIRA_TICKET_BROWSE_URL=$VITE_JIRA_TICKET_BROWSE_URL

WORKDIR /app/frontend
RUN npm run build

FROM nginx:stable-alpine AS production-stage

RUN rm /etc/nginx/conf.d/default.conf

COPY --from=build-stage /app/src/main/frontend/dist /usr/share/nginx/html
COPY frontend/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]