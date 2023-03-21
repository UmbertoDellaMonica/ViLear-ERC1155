const CertificatoCorsoViLear = artifacts.require("./CertificatoCorsoViLear.sol");
var addr1= '0xd7E4533C48E2D5Eb4c70Ba1Ae94DB92bA5aB6C33'
module.exports = async function(deployer) {
	
	deployer.deploy(CertificatoCorsoViLear,addr1);
};