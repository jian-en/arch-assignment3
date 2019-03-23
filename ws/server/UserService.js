
const users = [{ id: 1,
                 username: 'test',
                 password: 'test'}];

module.exports.authenticate = authenticate;

function authenticate({ username, password }) {
    const user = users.find(u => u.username === username && u.password === password);
    if (user) {
    	console.log("Authenticated good.");
        return user.username;
    } else {
        return null;
    }
}
