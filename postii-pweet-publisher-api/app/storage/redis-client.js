const redis = require('redis');
const { Temporal } = require('temporal-polyfill');

const client = redis.createClient(6379, "redis-server");
client.connect(); // TOOD extract to a common client

const addPweetTime = async (userId) => {
    let times = await getPweetTimes(userId)

    if (!times) {
        times = [Temporal.Now.instant()]
    }
    else {
        times = JSON.parse(times)
        if (times.length == 5) {
            times.shift()
        }
        times.push(Temporal.Now.instant())
    }
     
    await client.set(getKey(userId), JSON.stringify(times))
}

const getPweetTimes = async (userId) => {
    return await client.get(getKey(userId))
}

const getKey = (userId) => {
    return "UserId_" + userId
}

module.exports = {
    addPweetTime,
    getPweetTimes
}