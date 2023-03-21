package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.course.Contracts.CertificatoCorso;
import it.tcgroup.vilear.course.Contracts.CertificatoCorsoViLear;
import it.tcgroup.vilear.course.Model.CertificateCourse;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

@Service
public class BlockchainServiceImpl implements BlockchainService{

    public final static Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));

    public final static String CONTRACT_ADDRESS_CERTIFICATE_CORSO_VILEAR = "0x7FFbB8fB6b25b11adE29f4DdF48a5Ab115A655f8";
    public final static String CONTRACT_ADDRESS_CERTIFICATE_CORSO = "0xd7E4533C48E2D5Eb4c70Ba1Ae94DB92bA5aB6C33";

    private CertificatoCorsoViLear certificatoCorsoViLear;

    private CertificatoCorso certificatoCorso;

    @Override
    public Credentials initCredentials(String privateKey) {
        Credentials credentials = Credentials.create(privateKey);
        return credentials;
    }


    @Override
    public void initSmartContractCertificatoCorsoViLear(Credentials credentials) {
        certificatoCorsoViLear = CertificatoCorsoViLear.load(
                CONTRACT_ADDRESS_CERTIFICATE_CORSO_VILEAR,
                web3j,
                credentials,
                new DefaultGasProvider()
        );
        certificatoCorso = CertificatoCorso.load(
                CONTRACT_ADDRESS_CERTIFICATE_CORSO,
                web3j,
                credentials,
                new DefaultGasProvider());
    }

    @Override
    public Boolean registerCertificato(BigInteger id, CertificateCourse certificateCourse) throws Exception {
        return certificatoCorsoViLear.registraCertificato(
                id,
                certificateCourse.getNameCourse(),
                certificateCourse.getReleaseDate(),
                certificateCourse.getUser(),
                certificateCourse.getTeacher()
        ).send().isStatusOK();

    }

    @Override
    public String getCertificato(Integer idCertificato) throws Exception {
        String certificatoString = certificatoCorsoViLear.getCertificato(BigInteger.valueOf(idCertificato)).send();
        return certificatoString;
    }
}
