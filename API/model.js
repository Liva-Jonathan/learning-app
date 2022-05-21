const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    email: String,
    mdp: String,
    scores: []    
});

const Users = new mongoose.model('users', userSchema);
module.exports = { Users};