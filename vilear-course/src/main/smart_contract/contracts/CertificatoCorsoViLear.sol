// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

import "@openzeppelin/contracts/utils/Strings.sol";
import "./CertificatoCorso.sol";

contract CertificatoCorsoViLear  {

    // Construttore per l'indirizzo del Certificato 
    constructor(address certificatoAddress) {

        certificato = CertificatoCorso(certificatoAddress);
    }

    CertificatoCorso public certificato;

    // Questa struttura mi permette di associare ad ogni address un insieme di certificati
    mapping(address => mapping(uint => CertificatoCorso.Certificato)) public _infoUtenteWithCertificati;

    // Questo evento mi permette di poter mandare un messaggio
    event CertificatoCreatoFatto(uint idCertificato,address wallet,string certificatoToString);

    // Funzione Registra Certificato 
    function registraCertificato(uint idCertificato,
        string memory _nomeCorso,
        string memory _pubblicazioneData,
        string memory _infoUtente,
        string memory _infoTeacher
        ) public returns(bool){

        // Creo il Certificato tramite i dati che passo alla funzione dello smart contract 
        CertificatoCorso.Certificato memory newCertificate =  certificato.creaCertificato(
            idCertificato,
            _nomeCorso,
            _pubblicazioneData,
            _infoUtente,
            _infoTeacher
        );
        // Inserisco il Certificato all'interno della mappa con quel determinato address
        _infoUtenteWithCertificati[msg.sender][idCertificato] = newCertificate;
        // Costruisco la stringa da costruire
        string memory newCertificateToString = CertificatoToString(newCertificate);
        // Invia l'informazione all'address in questione evidenziando i dati costruiti nel certificato
        emit CertificatoCreatoFatto(idCertificato,msg.sender,newCertificateToString);
        return true;
    }

    // Funzione che va dall'Oggetto Certificato a String 
    function CertificatoToString(CertificatoCorso.Certificato memory _certificato)private pure returns(string memory){
       return string.concat(
            _certificato.nomeCorso, '-',
            _certificato.pubblicazioneData,'-',
            _certificato.DateUtente,'-',
            _certificato.DateTeacher);
    }

    // Questa funzione mi permette di poter recuperare un certificato in base 
    //a chi mi ha mandato la richiesta e all'id che mi sta passado 
    //se questo ovviamente ce l'ha nella sua struttura
    function getCertificato(uint idCertificato)public view returns(string memory){
        require(idCertificato >0,"L'id del certificato non e' valido ");
        return CertificatoToString(_infoUtenteWithCertificati[msg.sender][idCertificato]);
    }
}
