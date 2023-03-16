module.exports = {
    networks: {
        development: {
            host: "127.0.0.1",
            port: 7545,
            network_id: "5777"
        },
        develop: {
            host: "127.0.0.1",
            port: 7545,
            network_id: "5777"
        }
    },
    compilers: {
        solc: {
            version: "^0.8.17",
            settings: {
                optimizer: {
                    enabled: true,
                    runs: 1500
                }
            }
        }
    }
};
