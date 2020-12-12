var mysql = require('mysql');
var bodyParser = require('body-parser')

const express        = require('express');
const port = 8000;
const app            = express();
app.use( bodyParser.json() );       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
  extended: true
})); 
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database:"mydb"
});

con.connect(function(err) {
 console.log("Connected");

  
});


app.post('/checkUser', (req, res) => {   
    res.send("ASds");
  });
app.post('/createUser', (req, res) => {   
    
      let data = {fullname: req.body.fullname, phonenumber: req.body.phonenumber,email: req.body.email, password: req.body.password,address: req.body.address};

    let sql = "INSERT INTO users SET ?";
    let query = con.query(sql, data,(err, results) => {
      if(err){
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({ "message": err,"res":"failed" }));
      }else{
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({ "message": "user added","res":"success" }));
      }
       
      
    });
        
  });

  app.post('/authenticateUser', (req, res) => {   
    let sql = "SELECT * FROM users WHERE email='"+req.body.email+"' AND password='"+req.body.password+"'";
    console.log(req);
  let query = con.query(sql, (err, results) => {
    numRows = results.length;
    if(numRows==1){
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({ "message": "User authenticated","res":"success" }));
    }else{
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({ "message": "User Authentication Failed","res":"failed" }));
    }
  });
        
  });
  
  
  
  app.post('/userFetch', (req, res) => {   
    let sql = "SELECT * FROM users WHERE email='"+req.body.email+"'";
  let query = con.query(sql, (err, results) => {
      console.log(results.length);
      res.setHeader('Content-Type', 'application/json');
      if(results.length !=0){

        var json = JSON.stringify({ 
            "res": "success", 
            "data": results, 
            "message": "data Received"
          });
          res.end(json);
      }else{
        var json = JSON.stringify({ 
            "res": "failed", 
            "data": results, 
            "message": "No data Received"
          });
          res.end(json);
      }
    
  });
        
  });
  
  
app.post('/updateUser', (req, res) => {   
    
   var sql = "UPDATE users SET address = '"+req.body.address+"', fullname = '"+req.body.fullname+"', phonenumber = '"+req.body.phonenumber+"' WHERE email = '"+req.body.email+"'";
   con.query(sql, function (err, result) {
    if(err){
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({ "message": err,"res":"failed" }));
      }else{
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({ "message": "user updated","res":"success" }));
      }
  });
        
  });




  
app.listen(port, () => {  console.log('We are live on ' + port);});
