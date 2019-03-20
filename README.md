# How to evaluate and test the project

----------------------------------------

# How to develop the project in docker-compose
## Web Services
1. Go to **ws** folder
2. Run *docker-compose up --build --detach*
3. Run *docker attach ws_client_1*
4. Input 1, 2, 3, X to interact
### For develop the server
1. Change the server side code
2. Run *docker-compose restart server*, it will save you tons of time

## Micro Services
1. Go to **ms** folder
2. Run *docker-compose up --build --detach*
3. Run *docker attach ms_client_1*
4. Input 1, 2, 3, X to interact
