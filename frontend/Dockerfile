FROM node:lts-alpine AS build-stage

WORKDIR /app
COPY package*.json ./
COPY vite.config.ts ./

RUN npm install --legacy-peer-deps

COPY . .

ARG VITE_DRONE_ECASE_API_URL
ARG VITE_DRONE_DIGITAL_API_URL
ARG VITE_JIRA_TICKET_BROWSE_URL
ENV VITE_DRONE_ECASE_API_URL=$VITE_DRONE_ECASE_API_URL
ENV VITE_DRONE_DIGITAL_API_URL=$VITE_DRONE_DIGITAL_API_URL
ENV VITE_JIRA_TICKET_BROWSE_URL=$VITE_JIRA_TICKET_BROWSE_URL

RUN npm run build

FROM nginx:stable-alpine AS production-stage

RUN rm /etc/nginx/conf.d/default.conf

COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]