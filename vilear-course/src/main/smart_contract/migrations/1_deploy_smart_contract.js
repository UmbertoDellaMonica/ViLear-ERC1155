var CertificatoCorso = artifacts.require("./CertificatoCorso.sol");

module.exports = function (deployer) {
  deployer.deploy(CertificatoCorso);
};