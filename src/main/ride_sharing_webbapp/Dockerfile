FROM node:18-alpine AS builder
WORKDIR /app
COPY . .
RUN npm install && npm run build

FROM nginx:alpine
EXPOSE 4200
#ENV PORT 3000
COPY --from=builder /app/dist/* /usr/share/nginx/html/
COPY /nginx.conf /etc/nginx/conf.d/default.conf
#CMD ["npm", "run", "ng serve"]
# base image
# stage 1

#FROM node:18-alpine AS my-app-build
#WORKDIR /app
#COPY . .
#RUN npm ci && npm run build
#
## stage 2
#
#FROM nginx:alpine
#COPY --from=my-app-build /app/dist/* /usr/share/nginx/html
#EXPOSE 4200
##ENV PORT 4200
