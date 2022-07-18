const axios = require('axios')
const { Temporal } = require('temporal-polyfill')
const redisClient = require('../storage/redis-client')

const MAX_POSTS_DAY = 5

const validate = async (pweet, userId) => {
    let msg = undefined

    if (pweet.text.length < 1 || pweet.text.length > 777) {
        msg = "Text must be between 1 and 777 characters"
    }
    else {
        try {
            await axios.get(process.env.USER_BASE_URL + `/users/${userId}/exists`)
            msg = await validatePweetTimeRange(userId)
        } 
        catch (error) {
            if (error.response && error.response.status == 404) {
                msg = `User ${userId} not found`
            }
            else {
                msg = `Error consulting User exists: ${error.message}`
            }
        }
    }

    return msg
}

const validatePweetTimeRange = async (userId) => {
    const pweetsTimes = JSON.parse(await redisClient.getPweetTimes(userId))
    
    return pweetsTimes 
                && pweetsTimes.length == MAX_POSTS_DAY 
                && differenceFromFirst(pweetsTimes[0]) < 24
        ? "No more pweets allowed. Max post per 24h: " + MAX_POSTS_DAY
        : undefined;
}

const differenceFromFirst = (firstTime) => {
    return (Temporal.Now.instant().epochSeconds - Temporal.Instant.from(firstTime).epochSeconds) / (60 * 60)
}

module.exports = {
    validate
}