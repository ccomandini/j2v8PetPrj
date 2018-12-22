"use strict";
var module = 'f';
module = module + 's';
const fs = require(module); //'fs'
fs.writeFile("/Users/ccomandini/Desktop/foo/cucu.txt", "Hey there! "+new Date().getTime(), function(err) {
    if(err) {
        return console.log(err);
    }
    console.log("The file was saved!");
});