let amqpConn = null;

module.exports = {
    InitConnection: (fnFinish) => {
        amqp.connect(process.env.CLOUDAMQP_URL, (err, conn) => {
            if (err) {
                console.error("[AMQP]", err.message);
                return setTimeout(this, 1000);
            }

            conn.on("error", function (err) {
                console.log("ERROR", err);
                if (err.message !== "Connection closing") {
                    console.error("[AMQP] conn error", err.message);
                }
            });

            conn.on("close", function () {
                console.error("[AMQP] reconnecting");
                return setTimeout(() => { module.exports.InitConnection(fnFinish) }, 1000);
            });

            console.log("[AMQP] connected");
            amqpConn = conn;
            fnFinish();
        });
    }
};