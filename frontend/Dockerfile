### STAGE 1: Build ###
FROM node:12.7-alpine AS build
ENV APP_HOME=/usr/breddit-frontend/
WORKDIR $APP_HOME
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
COPY --from=build /usr/breddit-frontend/dist/frontend /usr/share/nginx/html
COPY default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
