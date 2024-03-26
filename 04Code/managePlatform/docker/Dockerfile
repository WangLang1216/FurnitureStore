FROM nginx:latest

ADD nginx.conf /etc/nginx/nginx.conf

RUN rm /etc/nginx/conf.d/default.conf

ADD nginx.conf /etc/nginx/conf.d/

COPY dist/ /usr/share/nginx/html/

EXPOSE 8080