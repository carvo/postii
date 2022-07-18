const express = require('express');
const { queuePweet } = require('../controller/publisher-controller');
const router = express.Router();

router.post('/postii/user/:userId/pweet', queuePweet);

module.exports = router;