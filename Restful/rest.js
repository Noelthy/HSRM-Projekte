const express = require('express');
const app = express();
const mysql = require('mysql');
const bodyParser = require('body-parser');


const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'wb'
});

// Verbindung zur Datenbank herstellen
connection.connect();

// Middleware für das Parsen von JSON-Daten
app.use(bodyParser.json());


// Neue Nachricht hinzufügen
app.post('/news', (req, res) => {
    console.log(req);
    const sql = 'INSERT INTO news (message, time) VALUES (?, ?)';
    const values = [req.body.message, formatDateToMySQLTimestamp(new Date())];

    connection.query(sql, values, (error, results) => {
        if (error) {
            console.error('Error on POST:', error);
            res.sendStatus(500);
        } else {
            const insertedNewsId = results.insertId;

            connection.query('SELECT newsid FROM news WHERE newsid = ?', insertedNewsId, (e, r) => {
                if (e) {
                    console.error('Error adding new message:', e);
                    res.sendStatus(500);
                } else {
                    res.json(r);
                }
            });
        }
    });
});


app.get('/news', (req, res) => {
    let timestamp = req.query.timestamp;

    // If the timestamp parameter is not provided, set it to the current date
    if (!timestamp) {
        const today = new Date();
        timestamp = formatDateToMySQLDate(today);
    }

    const startOfDay = `${timestamp} 00:00:00`;
    const endOfDay = `${timestamp} 23:59:59`;

    const sql = 'SELECT message FROM news WHERE time >= ? AND time <= ?';
    connection.query(sql, [startOfDay, endOfDay], (error, results) => {
        if (error) {
            console.error('Error on GET:', error);
            res.sendStatus(500);
        } else {
            if (results.length === 0) {
                res.status(404).json({ error: 'No messages found for the specified date' });
            } else {
                const messages = results.map(result => result.message);
                res.json({ messages });
            }
        }
    });
});

app.listen(3000, () => {
    console.log('Server running on port 3000');
});

function formatDateToMySQLTimestamp(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}