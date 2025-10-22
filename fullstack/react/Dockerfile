# Stage 1: Build Stage (Uses a Node image to build static assets)
FROM node:20-alpine AS build

WORKDIR /app

COPY package.json ./

RUN npm install 
# Copy the rest of the source code
COPY . .
# Build the application (Vite default command is npm run build, creates 'dist' folder)
RUN npm run build

# Stage 2: Runtime Stage (Uses a lightweight Nginx image to serve the files)
FROM nginx:alpine
# Copy the build output from the build stage into Nginx's default folder
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
