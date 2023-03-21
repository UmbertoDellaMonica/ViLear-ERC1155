package it.tcgroup.vilear.course.Contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class CertificatoCorsoViLear extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610ec6380380610ec683398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610e33806100936000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063d16ad45514610051578063da8548c714610079578063ea6ee0e914610099578063f9949445146100bd575b600080fd5b61006461005f3660046108a5565b6100e8565b60405190151581526020015b60405180910390f35b61008c61008736600461095c565b610236565b60405161007091906109c5565b6100ac6100a73660046109df565b61051f565b604051610070959493929190610a17565b6000546100d0906001600160a01b031681565b6040516001600160a01b039091168152602001610070565b6000805460405163607afca360e01b815282916001600160a01b03169063607afca390610121908a908a908a908a908a90600401610a77565b600060405180830381865afa15801561013e573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526101669190810190610b23565b3360009081526001602090815260408083208b845290915290208151919250829181906101939082610c92565b50602082015160018201906101a89082610c92565b50604082015160028201906101bd9082610c92565b50606082015160038201906101d29082610c92565b506080820151816004015590505060006101eb82610778565b90507f86b8c9a0013d2456591ae51347476d48de75f275fca6e4d2b74b4c956f18239d88338360405161022093929190610d52565b60405180910390a1506001979650505050505050565b6060600082116102985760405162461bcd60e51b815260206004820152602360248201527f4c2769642064656c20636572746966696361746f206e6f6e2065272076616c6960448201526203237960ed1b606482015260840160405180910390fd5b33600090815260016020908152604080832085845290915290819020815160a081019092528054610519929190829082906102d290610c09565b80601f01602080910402602001604051908101604052809291908181526020018280546102fe90610c09565b801561034b5780601f106103205761010080835404028352916020019161034b565b820191906000526020600020905b81548152906001019060200180831161032e57829003601f168201915b5050505050815260200160018201805461036490610c09565b80601f016020809104026020016040519081016040528092919081815260200182805461039090610c09565b80156103dd5780601f106103b2576101008083540402835291602001916103dd565b820191906000526020600020905b8154815290600101906020018083116103c057829003601f168201915b505050505081526020016002820180546103f690610c09565b80601f016020809104026020016040519081016040528092919081815260200182805461042290610c09565b801561046f5780601f106104445761010080835404028352916020019161046f565b820191906000526020600020905b81548152906001019060200180831161045257829003601f168201915b5050505050815260200160038201805461048890610c09565b80601f01602080910402602001604051908101604052809291908181526020018280546104b490610c09565b80156105015780601f106104d657610100808354040283529160200191610501565b820191906000526020600020905b8154815290600101906020018083116104e457829003601f168201915b50505050508152602001600482015481525050610778565b92915050565b600160209081526000928352604080842090915290825290208054819061054590610c09565b80601f016020809104026020016040519081016040528092919081815260200182805461057190610c09565b80156105be5780601f10610593576101008083540402835291602001916105be565b820191906000526020600020905b8154815290600101906020018083116105a157829003601f168201915b5050505050908060010180546105d390610c09565b80601f01602080910402602001604051908101604052809291908181526020018280546105ff90610c09565b801561064c5780601f106106215761010080835404028352916020019161064c565b820191906000526020600020905b81548152906001019060200180831161062f57829003601f168201915b50505050509080600201805461066190610c09565b80601f016020809104026020016040519081016040528092919081815260200182805461068d90610c09565b80156106da5780601f106106af576101008083540402835291602001916106da565b820191906000526020600020905b8154815290600101906020018083116106bd57829003601f168201915b5050505050908060030180546106ef90610c09565b80601f016020809104026020016040519081016040528092919081815260200182805461071b90610c09565b80156107685780601f1061073d57610100808354040283529160200191610768565b820191906000526020600020905b81548152906001019060200180831161074b57829003601f168201915b5050505050908060040154905085565b606081600001518260200151836040015184606001516040516020016107a19493929190610d85565b6040516020818303038152906040529050919050565b634e487b7160e01b600052604160045260246000fd5b60405160a0810167ffffffffffffffff811182821017156107f0576107f06107b7565b60405290565b604051601f8201601f1916810167ffffffffffffffff8111828210171561081f5761081f6107b7565b604052919050565b600067ffffffffffffffff821115610841576108416107b7565b50601f01601f191660200190565b600082601f83011261086057600080fd5b813561087361086e82610827565b6107f6565b81815284602083860101111561088857600080fd5b816020850160208301376000918101602001919091529392505050565b600080600080600060a086880312156108bd57600080fd5b85359450602086013567ffffffffffffffff808211156108dc57600080fd5b6108e889838a0161084f565b955060408801359150808211156108fe57600080fd5b61090a89838a0161084f565b9450606088013591508082111561092057600080fd5b61092c89838a0161084f565b9350608088013591508082111561094257600080fd5b5061094f8882890161084f565b9150509295509295909350565b60006020828403121561096e57600080fd5b5035919050565b60005b83811015610990578181015183820152602001610978565b50506000910152565b600081518084526109b1816020860160208601610975565b601f01601f19169290920160200192915050565b6020815260006109d86020830184610999565b9392505050565b600080604083850312156109f257600080fd5b82356001600160a01b0381168114610a0957600080fd5b946020939093013593505050565b60a081526000610a2a60a0830188610999565b8281036020840152610a3c8188610999565b90508281036040840152610a508187610999565b90508281036060840152610a648186610999565b9150508260808301529695505050505050565b85815260a060208201526000610a9060a0830187610999565b8281036040840152610aa28187610999565b90508281036060840152610ab68186610999565b90508281036080840152610aca8185610999565b98975050505050505050565b600082601f830112610ae757600080fd5b8151610af561086e82610827565b818152846020838601011115610b0a57600080fd5b610b1b826020830160208701610975565b949350505050565b600060208284031215610b3557600080fd5b815167ffffffffffffffff80821115610b4d57600080fd5b9083019060a08286031215610b6157600080fd5b610b696107cd565b825182811115610b7857600080fd5b610b8487828601610ad6565b825250602083015182811115610b9957600080fd5b610ba587828601610ad6565b602083015250604083015182811115610bbd57600080fd5b610bc987828601610ad6565b604083015250606083015182811115610be157600080fd5b610bed87828601610ad6565b6060830152506080830151608082015280935050505092915050565b600181811c90821680610c1d57607f821691505b602082108103610c3d57634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115610c8d57600081815260208120601f850160051c81016020861015610c6a5750805b601f850160051c820191505b81811015610c8957828155600101610c76565b5050505b505050565b815167ffffffffffffffff811115610cac57610cac6107b7565b610cc081610cba8454610c09565b84610c43565b602080601f831160018114610cf55760008415610cdd5750858301515b600019600386901b1c1916600185901b178555610c89565b600085815260208120601f198616915b82811015610d2457888601518255948401946001909101908401610d05565b5085821015610d425787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b8381526001600160a01b0383166020820152606060408201819052600090610d7c90830184610999565b95945050505050565b60008551610d97818460208a01610975565b8083019050602d60f81b8082528651610db7816001850160208b01610975565b600192019182018190528551610dd4816002850160208a01610975565b60029201918201528351610def816003840160208801610975565b01600301969550505050505056fea2646970667358221220bcdaa5cb432584a91a5c419877f322b5a462a786c84422bb85fabf3c87b6d94564736f6c63430008130033";

    public static final String FUNC__INFOUTENTEWITHCERTIFICATI = "_infoUtenteWithCertificati";

    public static final String FUNC_CERTIFICATO = "certificato";

    public static final String FUNC_GETCERTIFICATO = "getCertificato";

    public static final String FUNC_REGISTRACERTIFICATO = "registraCertificato";

    public static final Event CERTIFICATOCREATOFATTO_EVENT = new Event("CertificatoCreatoFatto", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected CertificatoCorsoViLear(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CertificatoCorsoViLear(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CertificatoCorsoViLear(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CertificatoCorsoViLear(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public   List<CertificatoCreatoFattoEventResponse> getCertificatoCreatoFattoEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CERTIFICATOCREATOFATTO_EVENT, transactionReceipt);
        ArrayList<CertificatoCreatoFattoEventResponse> responses = new ArrayList<CertificatoCreatoFattoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CertificatoCreatoFattoEventResponse typedResponse = new CertificatoCreatoFattoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.idCertificato = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.wallet = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.certificatoToString = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CertificatoCreatoFattoEventResponse> certificatoCreatoFattoEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CertificatoCreatoFattoEventResponse>() {
            @Override
            public CertificatoCreatoFattoEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CERTIFICATOCREATOFATTO_EVENT, log);
                CertificatoCreatoFattoEventResponse typedResponse = new CertificatoCreatoFattoEventResponse();
                typedResponse.log = log;
                typedResponse.idCertificato = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.wallet = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.certificatoToString = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CertificatoCreatoFattoEventResponse> certificatoCreatoFattoEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CERTIFICATOCREATOFATTO_EVENT));
        return certificatoCreatoFattoEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple5<String, String, String, String, byte[]>> _infoUtenteWithCertificati(String param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC__INFOUTENTEWITHCERTIFICATI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteFunctionCall<Tuple5<String, String, String, String, byte[]>>(function,
                new Callable<Tuple5<String, String, String, String, byte[]>>() {
                    @Override
                    public Tuple5<String, String, String, String, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, String, String, byte[]>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (byte[]) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> certificato() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CERTIFICATO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getCertificato(BigInteger idCertificato) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCERTIFICATO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(idCertificato)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registraCertificato(BigInteger idCertificato, String _nomeCorso, String _pubblicazioneData, String _infoUtente, String _infoTeacher) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTRACERTIFICATO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(idCertificato), 
                new org.web3j.abi.datatypes.Utf8String(_nomeCorso), 
                new org.web3j.abi.datatypes.Utf8String(_pubblicazioneData), 
                new org.web3j.abi.datatypes.Utf8String(_infoUtente), 
                new org.web3j.abi.datatypes.Utf8String(_infoTeacher)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static CertificatoCorsoViLear load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CertificatoCorsoViLear(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CertificatoCorsoViLear load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CertificatoCorsoViLear(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CertificatoCorsoViLear load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CertificatoCorsoViLear(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CertificatoCorsoViLear load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CertificatoCorsoViLear(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CertificatoCorsoViLear> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String certificatoAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certificatoAddress)));
        return deployRemoteCall(CertificatoCorsoViLear.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<CertificatoCorsoViLear> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String certificatoAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certificatoAddress)));
        return deployRemoteCall(CertificatoCorsoViLear.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CertificatoCorsoViLear> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String certificatoAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certificatoAddress)));
        return deployRemoteCall(CertificatoCorsoViLear.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CertificatoCorsoViLear> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String certificatoAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certificatoAddress)));
        return deployRemoteCall(CertificatoCorsoViLear.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class CertificatoCreatoFattoEventResponse extends BaseEventResponse {
        public BigInteger idCertificato;

        public String wallet;

        public String certificatoToString;
    }
}
