const got = require('got');

got('https://www.assi.st', { json: false }).then(response => {
    javaCallback(response.body);
    //console.log();
}).catch(error => {
    console.log(error.response.body);
});