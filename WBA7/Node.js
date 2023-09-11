var express = require('express');
var app = express();

app.use(express.urlencoded({ extended: false }));
app.use ( express.static ( __dirname ));

//Formular Seite
app.get('/', function (req, res) {
    res.sendFile( __dirname + "/" + "wba_5.2.html" );
})

//Submit Funktion
app.post('/submit', function (req, res){
    let fieldNames = Object.keys(req.body);
    let formValues = Object.values(req.body);
    //HTML String zusammenbauen für die Antwort
    var response =
        '<!DOCTYPE html>'+
            '<html lang="de">'+
                '<head>'+
                    '<meta charset="utf-8">'+
                    '<title>submit.response</title>'+
                '</head>'+
                '<body>'+
                    '<h3>Bestellung erfogreich!</h3>'+
                    '<p>Sie haben folgendes bestellt:</p><br>'+
                    '<table>';
    //tabelle mit den Werten erstellen
    for(let i = 0; i<fieldNames.length-1; i++){
        response+= '<tr> <td>'+fieldNames[i]+'</td><td>'+formValues[i]+'</td></tr>';
    }
        response+= '<tr> <th>'+fieldNames[fieldNames.length-1]+'</th><th>'+formValues[fieldNames.length-1]+'</th></tr>';
    response += '</table>'+
                '</body>'+
            '</html>';
    //Antwort abschicken
    res.send(response);
})

//server Starten
app.listen(3000)

