FROM node:7
COPY *.js /
RUN npm install express mysql body-parser --save
RUN npm init --yes
RUN npm install
CMD ["node", "Server.js"]
