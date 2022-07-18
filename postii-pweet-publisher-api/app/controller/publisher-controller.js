const pweetValidator = require('../validator/pweet-validator')
const service = require('../service/publisher-service.js')

const queuePweet = async (req, res) => {
    const pweet = req.body
    const userId = req.params.userId
    const errorMsg = await pweetValidator.validate(pweet, userId)

    if (errorMsg) {
        res.status(400).send({ errorMsg: errorMsg })
    }
    else {
        service.queuePweet(pweet, userId)
        res.sendStatus(201)
    }
}

module.exports =  {
    queuePweet
};