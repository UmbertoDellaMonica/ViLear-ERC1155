// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

import "@openzeppelin/contracts/token/ERC1155/ERC1155.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

contract CertificatoCorso is ERC1155{
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


    function creaCertificato(
        uint idCertificato,
        string memory _nomeCorso,
        string memory _pubblicazioneData,
        string memory _infoUtente,
        string memory _infoTeacher
    ) public view returns(Certificato memory){
        // Verifico che l'id del certificato sia sempre >0 altrimenti non posso inserirlo
        // Registro il certificato come se fosse vuoto 
        Certificato memory newCertificate =  Certificato({
            nomeCorso: "",
            pubblicazioneData: "",
            DateUtente: "",
            DateTeacher: "",
            firma: 0
        });
        if(idCertificato<0){
        }
        // Crittografo la firma in maniera da renderla univoca
        bytes32 firmaCertificato = keccak256(
            abi.encodePacked(
            _nomeCorso,
            _pubblicazioneData,
            _infoUtente,
            _infoTeacher,
            msg.sender
            )
            );
        // Creo il certificato con gli appositi campi  
         newCertificate = Certificato({
            nomeCorso: _nomeCorso,
            pubblicazioneData: _pubblicazioneData,
            DateUtente: _infoUtente,
            DateTeacher: _infoTeacher,
            firma: firmaCertificato
        });
        return newCertificate;
    }
}