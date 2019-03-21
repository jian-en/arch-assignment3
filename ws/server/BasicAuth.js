//This function is from http://jasonwatmore.com/post/2018/09/24/nodejs-basic-authentication-tutorial-with-example-api

const userService = require('./UserService');

module.exports = basicAuth;

function basicAuth(req, res, next) {
    // if it is to authenticate, go to the authenticate service
    if (req.path === '/api/authenticate') {
        return next();
    }

    // check for basic auth header
    if (!req.headers.authorization || req.headers.authorization.indexOf('Basic ') === -1) {
        return res.status(401).json({ message: 'Missing Authorization Header' });
    }

    // verify auth credentials
    const base64Credentials =  req.headers.authorization.split(' ')[1];
    const credentials = Buffer.from(base64Credentials, 'base64').toString('ascii');
    const [username, password] = credentials.split(':');
    const user = userService.authenticate({ username, password });
    if (!user) {
        return res.status(401).json({ message: 'Invalid Authentication Credentials' });
    }

    // attach user to request object
    req.user = user;

    next();
}