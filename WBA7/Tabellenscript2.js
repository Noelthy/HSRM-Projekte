//Gegebenes Array
let wein = [
    ["Marienthaler Stiftsberg Rotweincuvée", 3.60],
    ["Riesling Classic", 3.70],
    ["Silvaner Selection Rheinhessen", 6.90],
    ["Domäne Avelsbach Riesling Sekt", 6.15],
    ["Domänes Avelsbach Riesling Sekt", 6.15]
];
wein.sort(function (a, b) {
    if (a[0].toUpperCase() == b[0].toUpperCase()) {
        return (0);
    } else if (a[0].toUpperCase() < b[0].toUpperCase()) {
        return (-1);
    } else {
        return (1);
    }
});

//Zeilen pro Weinflasche aus Array wein hinzufügen bei der Tabelle
let weinTable = document.getElementById("weinTable");
for(let i=0;i<wein.length; i++){
    let row = weinTable.insertRow(i+1);
    let cell1 = row.insertCell(0);
    let cell2 = row.insertCell(1);
    let cell3 = row.insertCell(2);
    let cell4 = row.insertCell(3);
    let inputFlasche = document.createElement("input");
    //input type="text" value="0" id ="in1" aus vorheriger Aufgabe
    inputFlasche.type="text";
    inputFlasche.name=wein[i][0];
    inputFlasche.value="0";
    inputFlasche.id="tableIn"+(i+1).toString();
    //Anzahl-input an Zelle von Zeile hinzugefügt
    cell1.appendChild(inputFlasche);

    //Weinnamen hinzufügen
    cell2.innerText = wein[i][0];
    //Weinpreis hinzufügen
    cell3.innerText = wein[i][1];

    let inputPreis = document.createElement("input");
    //Erneut den input aus vorheriger Aufgabe
    inputPreis.type="text";
    inputPreis.value="0.00";
    inputPreis.id="tableOut"+(i+1).toString();
    inputPreis.readOnly;
    //Preis-input an Zelle von Zeile hinzugefügt
    cell4.appendChild(inputPreis);
    //onkeyup funktion hinzufügen
    inputFlasche.onkeyup = function() {
            fixNum(this);
            document.getElementById("tableOut" + (i+1)).value = (this.value * wein[i][1]).toFixed(2);
        refresh();
    }
}

//sicherstellen, das es sich um Zahlen handelt
function fixNum(k){
    k.value = k.value.replace(/[^0-9]/g, "");
    if(isNaN(k.value)|| k.value.toString() === ""){
        k.value = 0
    }
    k.value=parseInt(k.value);
}
//Statischen Elemente der Seite
//@param in_1,2: RadioButtons für den Vesand, werden automatisch gesetzt
//@param out_1,2: Versandkosten
let in_1 = document.getElementById("in1");
let out_1 = document.getElementById("out1");
let in_2 = document.getElementById("in2");
let out_2 = document.getElementById("out2");
//@param out_3,4,5: Zwischensumme, Mwst., Gesamtsumme
let out_3 = document.getElementById("out3");
let out_4 = document.getElementById("out4");
let out_5 = document.getElementById("out5");
//Zwichen- und Endpreise neu berechnen
function refresh(){
    let amount = 0;
    let price = 0;
    for (let i = 0; i < wein.length; i++) {
        let k = parseFloat(document.getElementById("tableIn" + (i + 1)).value);
        amount += k;
        price += k * wein[i][1];
    }
    if(amount>12){
        in_2.checked = true;
        in_1.checked = false;
    }
    if(amount<=12){
        in_1.checked = true;
        in_2.checked = false;
    }
    out_1.value = (in_1.checked ? 10 : 0).toFixed(2);
    out_2.value = (in_2.checked ? 15 : 0).toFixed(2);
    price  += parseFloat(out_1.value) + parseFloat(out_2.value);
    out_3.value = price.toFixed(2);
    out_4.value = (out_3.value *0.19).toFixed(2);
    out_5.value = (parseFloat(out_3.value) + parseFloat(out_4.value)).toFixed(2);

}