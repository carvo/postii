const redisClient = require('../storage/redis-client')
const amqp = require('amqplib/callback_api');

const queuePweet = (pweet, userId) => {
    pweet.userId = userId

    amqp.connect(process.env.RABBIT_URL, function (err, conn) {// TODO extract to common client
        conn.createChannel(function (err, ch) {
            ch.sendToQueue(
                process.env.RABBIT_PWEET_QUEUE, 
                Buffer.from(JSON.stringify(pweet))
            )
            redisClient.addPweetTime(userId)
        })
    })
}


module.exports = {
    queuePweet
}