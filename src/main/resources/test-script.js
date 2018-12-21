"use strict";
//https://www.twilio.com/blog/2017/08/http-requests-in-node-js.html
const https = require('https');

https.get('https://www.google.com', (resp) => {
    let data = '';
    // A chunk of data has been recieved... store it
    resp.on('data', (chunk) => {
        data += chunk;
    });

    // The whole response has been received. Send the result to the java code
    resp.on('end', () => {
        javaCallback(data); //this is the java function we added through the java code
    });

    }).on("error", (err) => {
        console.log("Error: " + err.message);
    });

