const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
var utils = require("./utils");
const { Users } = require('./model');
process.env['NODE_TLS_REJECT_UNAUTHORIZED'] = 0;
const app = express();
const API_url = "/API";
const DB_url = 'mongodb+srv://learning:learning@clusterlearningapp.ajxgy.mongodb.net/?retryWrites=true&w=majority';
const DB_urlPROD = 'mongodb+srv://learning:learning@clusterlearningapp.ajxgy.mongodb.net/?retryWrites=true&w=majority';
app.listen(process.env.PORT, function(){
    console.log("listening on " + process.env.PORT);
});

app.use(bodyParser.urlencoded({extended: true}));
app.use(express.static('public'));
app.use(bodyParser.json());
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
    res.header('Access-Control-Allow-Methods', 'PUT, POST, GET, DELETE, OPTIONS');
    //res.header('Access-Control-Allow-Credentials', true);
    next();
});

mongoose.connect(DB_url, { useNewUrlParser: true, useUnifiedTopology: true }).then(client=>{
    console.log('Connected to Database Mongoose');

    app.post(API_url+'/login', (req, res) => {
        console.log(req.body.email +"/"+req.body.mdp);
        console.log("ORIGIN: "+req.body.origin);
        Users.findOne({
            $and:[
                {
                    email: req.body.email
                },

                {
                    mdp: utils.crypt(req.body.mdp)
                }
            ]
        })
        .then(result => {
            console.log(result);
            /*var send = {
                status: 200,
                message: "success",
                data: result
            };*/
            if(result != null){
                res.status(200);
            }else{
                res.statusMessage = "Connexion echouee";
                res.status(201);
            }
            res.send(JSON.stringify(result));

            //res.json(send);
        })
        .catch(error => console.error(error))
    });

    app.post(API_url+'/signup', (req, res) => {
        console.log(req.body.email +"/"+req.body.mdp);
        console.log("ORIGIN: "+req.body.origin);
        var newUser = new Users();
        newUser.email = req.body.email;
        newUser.mdp = utils.crypt(req.body.mdp);
        newUser.save()
        .then(result => {
            console.log(result);
            console.log(result.id);
            var send = {
                status: 200,
                message: "success",
                data: result
            };
            res.json(send);
        })
        .catch(error => console.error(error))
    });

    app.put(API_url+'/save-score', (req, res) => {
        var userUpdate = {};
            var addScore = {};
            addScore = {
                scores:{
                    "idtheme": req.body.idtheme,
                    "score": parseInt(req.body.score),
                    "daty": new Date()
                }
            };
            userUpdate['$push'] = addScore;
        Users.findByIdAndUpdate(req.body.id,
            userUpdate)
            .then(results =>{
                console.log(results);
                console.log("score enregistrer");
                if(results != null){
                    res.status(200);
                }else{
                    res.statusMessage = "Connexion echouee";
                    res.status(201);
                }
                res.send(JSON.stringify(results));
                //res.json(send);
            })
            .catch(console.error);
    });

    app.get(API_url+'/historique/:id', (req, res) =>{
        Users.findById(req.params.id)
        .then(result => {
            console.log(result);
            var send = {
                status: 200,
                message: "success",
                data: result
            };
            if(result != null){
                res.status(200);
            }else{
                res.statusMessage = "Connexion echouee";
                res.status(201);                
            }
            res.send(JSON.stringify(result));
        })
        .catch(error => console.error(error))
    });

});
