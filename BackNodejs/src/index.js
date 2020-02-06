const express = require('express');
const morgan = require('morgan');
const mongoose = require('mongoose');
const app = express();

mongoose.connect('mongodb://localhost/mevn-database')
        .then(db => console.log('DB is connected'))
        .catch( err => console.log(err));

app.use(morgan('dev'));
app.use(express.json());

app.use(require('./routes/tasks'));

app.listen(3000, () => {
    console.log('Server on port 3000');
});