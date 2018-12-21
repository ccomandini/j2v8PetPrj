const qr = require( modulePath() ); //the function modulePath is a java bind  that provides something like '/Users/ccomandini/testNodeJsModule/node_modules/qr-image'
//https://medium.freecodecamp.org/requiring-modules-in-node-js-everything-you-need-to-know-e7fbd119be8
//https://www.npmjs.com/package/qr-image

var svgString = qr.imageSync('QR code created using a lot of stuff in the middle', { type: 'pdf' });

returnBack(svgString);