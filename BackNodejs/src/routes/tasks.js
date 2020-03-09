const express = require('express');
const router = express.Router();

const Task = require('../models/Task');


test = ['max-age=30,public'];
router.get('/tasks', async (req,res)=>{
    const tasks = await Task.find();
    console.log(req.headers);
    res.set('Cache-Control',test);
    res.json(tasks);
});

router.post('/tasks', async (req,res) => {
    const task = new Task(req.body);
    await task.save();
    res.json({
        status: 'Tarea Guardada'
    })
});

router.put('/tasks/:id', async (req,res) => {
    
    await Task.findByIdAndUpdate(req.params.id, res.body);
    res.json({
        status: "Tarea Actualizada"
    })
    
});

router.delete('/tasks/:id', async (req, res) => {
    await Task.findByIdAndRemove(req.params.id);
    res.json({
        status:"Tarea eliminada"
    })
});


module.exports = router;