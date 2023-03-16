package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.course.Model.CertificateCourse;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public interface BlockchainService {
    /**
     * Inizializza le Credenziali tramite PrivateKey
     * Ritorna le Credenziali dell'Utente
     * @param privateKey si riferisce alla chiave privata dell'utente
     */
     Credentials initCredentials(String privateKey);

    /**
     * Inizializza il Contratto relativo allo Smart Contract CertificatoCorsoViLear.sol
     * @param credentials si riferisce alle credenziali della persona che vuole accedere allo smart contract
     * Quest'ultimo esegue una transazione
     */
    void initSmartContractCertificatoCorsoViLear(Credentials credentials);

    /**
     * Registra un certificato all'interno della Blockchain
     * @param idCertificato id del certificato
     * @param certificateCourse certificato relativo al corso
     * @throws Exception
     */
    Boolean registerCertificato(BigInteger idCertificato, CertificateCourse certificateCourse) throws Exception;

    /**
     * Recupera un certificato in relazione al voto dello studente
     * @param idCertificato si riferisce all'id del Certificato
     */
    String getCertificato(Integer idCertificato) throws Exception;
}
