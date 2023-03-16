// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

import "@openzeppelin/contracts/token/ERC1155/ERC1155.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

contract CertificatoCorso is ERC1155 {

    // Costruttore del Certificato 
    constructor()ERC1155(""){}

    // Struttura che contiene le informazioni del singolo Certificato 
    struct Certificato {
        string nomeCorso; // Nome del Corso 
        string pubblicazioneData; // Data di Pubblicazione del Corso 
        string DateUtente;
        string DateTeacher;
        bytes32 firma; // Tale numero univoco viene prodotto dalla funzione di Hashing keccak256(abi.encodePackage(data));
    }

    // Questa struttura mi permette di associare ad ogni address un insieme di certificati
    mapping(address => mapping(uint => Certificato)) public _infoUtenteWithCertificati;

    // Questo evento mi permette di poter mandare un messaggio
    event CertificatoCreatoFatto(uint idCertificato,address wallet,string certificatoToString);

    function creaCertificato(
        uint idCertificato,
        string memory _nomeCorso,
        string memory _pubblicazioneData,
        string memory _infoUtente,
        string memory _infoTeacher
    ) public returns(bool){
        // Verifico che l'id del certificato sia sempre >0 altrimenti non posso inserirlo
        if(idCertificato<0){
            return false;
        }
        // Crittografo la firma in maniera da renderla univoca
        bytes32 firmaCertificato = keccak256(abi.encodePacked(_nomeCorso,_pubblicazioneData,_infoUtente,_infoTeacher,msg.sender));
        // Creo il certificato 
         Certificato memory newCertificate = Certificato({
            nomeCorso: _nomeCorso,
            pubblicazioneData: _pubblicazioneData,
            DateUtente: _infoUtente,
            DateTeacher: _infoTeacher,
            firma: firmaCertificato
        });
        // Inserisco il Certificato all'interno della mappa con quel determinato address
        _infoUtenteWithCertificati[_msgSender()][idCertificato] = newCertificate;
        // Costruisco la stringa da costruire
        string memory newCertificateToString = CertificatoToString(newCertificate);
        // Invia l'informazione all'address in questione evidenziando i dati costruiti nel certificato
        emit CertificatoCreatoFatto(idCertificato,_msgSender(),newCertificateToString);
        return true;
    }

    function CertificatoToString(Certificato memory certificato)private pure returns(string memory){
       return string.concat(
            certificato.nomeCorso, '-',
            certificato.pubblicazioneData,'-',
            certificato.DateUtente,'-',
            certificato.DateTeacher,'-');
    }

    // Questa funzione mi permette di poter recuperare un certificato in base a chi mi ha mandato la richiesta e all'id che mi sta passado se questo ovviamente ce l'ha nella sua struttura
    function getCertificato(uint idCertificato)public view returns(string memory){
        require(idCertificato >0,"L'id del certificato non e' valido ");
        return CertificatoToString(_infoUtenteWithCertificati[msg.sender][idCertificato]);
    }




}