const mongoose = require('mongoose');
const { Schema } = mongoose;

const Task = Schema({
    title: String,
    description: String,
});

module.exports = mongoose.model('Task', Task);