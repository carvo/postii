const express = require('express');
const app = express();
const PORT = process.env.PORT || 9996;

require('dotenv').config()

//Configs
app.use(express.json()) 
app.use(express.urlencoded({ extended: true }))

//Routes
app.use('/', require('./app/route/publisher-router'));
 

app.listen(PORT, console.log("Server start in port: " + PORT))
