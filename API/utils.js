var crypto = require('crypto');
function crypt(string){
    return crypto.createHash('md5').update(string).digest('hex');
}
exports.crypt = crypt;