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
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
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
public class CertificatoCorso extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b506040805160208101909152600081526200002c8162000033565b50620001b6565b6002620000418282620000ea565b5050565b634e487b7160e01b600052604160045260246000fd5b600181811c908216806200007057607f821691505b6020821081036200009157634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115620000e557600081815260208120601f850160051c81016020861015620000c05750805b601f850160051c820191505b81811015620000e157828155600101620000cc565b5050505b505050565b81516001600160401b0381111562000106576200010662000045565b6200011e816200011784546200005b565b8462000097565b602080601f8311600181146200015657600084156200013d5750858301515b600019600386901b1c1916600185901b178555620000e1565b600085815260208120601f198616915b82811015620001875788860151825594840194600190910190840162000166565b5085821015620001a65787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b61161680620001c66000396000f3fe608060405234801561001057600080fd5b50600436106100925760003560e01c80634e1273f4116100665780634e1273f414610115578063607afca314610135578063a22cb46514610155578063e985e9c514610168578063f242432a146101a457600080fd5b8062fdd58e1461009757806301ffc9a7146100bd5780630e89341c146100e05780632eb2c2d614610100575b600080fd5b6100aa6100a5366004610c49565b6101b7565b6040519081526020015b60405180910390f35b6100d06100cb366004610c8c565b610250565b60405190151581526020016100b4565b6100f36100ee366004610cb0565b6102a0565b6040516100b49190610d19565b61011361010e366004610e78565b610334565b005b610128610123366004610f22565b610380565b6040516100b49190611028565b61014861014336600461103b565b6104aa565b6040516100b491906110c2565b61011361016336600461114e565b610588565b6100d061017636600461118a565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205460ff1690565b6101136101b23660046111bd565b610597565b60006001600160a01b0383166102275760405162461bcd60e51b815260206004820152602a60248201527f455243313135353a2061646472657373207a65726f206973206e6f742061207660448201526930b634b21037bbb732b960b11b60648201526084015b60405180910390fd5b506000818152602081815260408083206001600160a01b03861684529091529020545b92915050565b60006001600160e01b03198216636cdb3d1360e11b148061028157506001600160e01b031982166303a24d0760e21b145b8061024a57506301ffc9a760e01b6001600160e01b031983161461024a565b6060600280546102af90611222565b80601f01602080910402602001604051908101604052809291908181526020018280546102db90611222565b80156103285780601f106102fd57610100808354040283529160200191610328565b820191906000526020600020905b81548152906001019060200180831161030b57829003601f168201915b50505050509050919050565b6001600160a01b03851633148061035057506103508533610176565b61036c5760405162461bcd60e51b815260040161021e9061125c565b61037985858585856105dc565b5050505050565b606081518351146103e55760405162461bcd60e51b815260206004820152602960248201527f455243313135353a206163636f756e747320616e6420696473206c656e677468604482015268040dad2e6dac2e8c6d60bb1b606482015260840161021e565b6000835167ffffffffffffffff81111561040157610401610d2c565b60405190808252806020026020018201604052801561042a578160200160208202803683370190505b50905060005b84518110156104a25761047585828151811061044e5761044e6112aa565b6020026020010151858381518110610468576104686112aa565b60200260200101516101b7565b828281518110610487576104876112aa565b602090810291909101015261049b816112d6565b9050610430565b509392505050565b6104df6040518060a0016040528060608152602001606081526020016060815260200160608152602001600080191681525090565b6040805160c081018252600060a082018181528252825160208082018552828252808401919091528351808201855282815283850152835180820185528281526060840152608083018290529251919290916105459189918991899189913391016112ef565b60408051808303601f19018152828252805160209182012060a0840183529983528201979097529586019490945250506060830152506080810191909152919050565b6105933383836107b9565b5050565b6001600160a01b0385163314806105b357506105b38533610176565b6105cf5760405162461bcd60e51b815260040161021e9061125c565b6103798585858585610899565b815183511461063e5760405162461bcd60e51b815260206004820152602860248201527f455243313135353a2069647320616e6420616d6f756e7473206c656e677468206044820152670dad2e6dac2e8c6d60c31b606482015260840161021e565b6001600160a01b0384166106645760405162461bcd60e51b815260040161021e90611362565b3360005b845181101561074b576000858281518110610685576106856112aa565b6020026020010151905060008583815181106106a3576106a36112aa565b602090810291909101810151600084815280835260408082206001600160a01b038e1683529093529190912054909150818110156106f35760405162461bcd60e51b815260040161021e906113a7565b6000838152602081815260408083206001600160a01b038e8116855292528083208585039055908b168252812080548492906107309084906113f1565b9250508190555050505080610744906112d6565b9050610668565b50846001600160a01b0316866001600160a01b0316826001600160a01b03167f4a39dc06d4c0dbc64b70af90fd698a233a518aa5d07e595d983b8c0526c8f7fb878760405161079b929190611404565b60405180910390a46107b18187878787876109c3565b505050505050565b816001600160a01b0316836001600160a01b03160361082c5760405162461bcd60e51b815260206004820152602960248201527f455243313135353a2073657474696e6720617070726f76616c20737461747573604482015268103337b91039b2b63360b91b606482015260840161021e565b6001600160a01b03838116600081815260016020908152604080832094871680845294825291829020805460ff191686151590811790915591519182527f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a3505050565b6001600160a01b0384166108bf5760405162461bcd60e51b815260040161021e90611362565b3360006108cb85610b27565b905060006108d885610b27565b90506000868152602081815260408083206001600160a01b038c1684529091529020548581101561091b5760405162461bcd60e51b815260040161021e906113a7565b6000878152602081815260408083206001600160a01b038d8116855292528083208985039055908a168252812080548892906109589084906113f1565b909155505060408051888152602081018890526001600160a01b03808b16928c821692918816917fc3d58168c5ae7397731d063d5bbf3d657854427343f4c083240f7aacaa2d0f62910160405180910390a46109b8848a8a8a8a8a610b72565b505050505050505050565b6001600160a01b0384163b156107b15760405163bc197c8160e01b81526001600160a01b0385169063bc197c8190610a079089908990889088908890600401611432565b6020604051808303816000875af1925050508015610a42575060408051601f3d908101601f19168201909252610a3f91810190611490565b60015b610aee57610a4e6114ad565b806308c379a003610a875750610a626114c9565b80610a6d5750610a89565b8060405162461bcd60e51b815260040161021e9190610d19565b505b60405162461bcd60e51b815260206004820152603460248201527f455243313135353a207472616e7366657220746f206e6f6e2d455243313135356044820152732932b1b2b4bb32b91034b6b83632b6b2b73a32b960611b606482015260840161021e565b6001600160e01b0319811663bc197c8160e01b14610b1e5760405162461bcd60e51b815260040161021e90611553565b50505050505050565b60408051600180825281830190925260609160009190602080830190803683370190505090508281600081518110610b6157610b616112aa565b602090810291909101015292915050565b6001600160a01b0384163b156107b15760405163f23a6e6160e01b81526001600160a01b0385169063f23a6e6190610bb6908990899088908890889060040161159b565b6020604051808303816000875af1925050508015610bf1575060408051601f3d908101601f19168201909252610bee91810190611490565b60015b610bfd57610a4e6114ad565b6001600160e01b0319811663f23a6e6160e01b14610b1e5760405162461bcd60e51b815260040161021e90611553565b80356001600160a01b0381168114610c4457600080fd5b919050565b60008060408385031215610c5c57600080fd5b610c6583610c2d565b946020939093013593505050565b6001600160e01b031981168114610c8957600080fd5b50565b600060208284031215610c9e57600080fd5b8135610ca981610c73565b9392505050565b600060208284031215610cc257600080fd5b5035919050565b60005b83811015610ce4578181015183820152602001610ccc565b50506000910152565b60008151808452610d05816020860160208601610cc9565b601f01601f19169290920160200192915050565b602081526000610ca96020830184610ced565b634e487b7160e01b600052604160045260246000fd5b601f8201601f1916810167ffffffffffffffff81118282101715610d6857610d68610d2c565b6040525050565b600067ffffffffffffffff821115610d8957610d89610d2c565b5060051b60200190565b600082601f830112610da457600080fd5b81356020610db182610d6f565b604051610dbe8282610d42565b83815260059390931b8501820192828101915086841115610dde57600080fd5b8286015b84811015610df95780358352918301918301610de2565b509695505050505050565b600082601f830112610e1557600080fd5b813567ffffffffffffffff811115610e2f57610e2f610d2c565b604051610e46601f8301601f191660200182610d42565b818152846020838601011115610e5b57600080fd5b816020850160208301376000918101602001919091529392505050565b600080600080600060a08688031215610e9057600080fd5b610e9986610c2d565b9450610ea760208701610c2d565b9350604086013567ffffffffffffffff80821115610ec457600080fd5b610ed089838a01610d93565b94506060880135915080821115610ee657600080fd5b610ef289838a01610d93565b93506080880135915080821115610f0857600080fd5b50610f1588828901610e04565b9150509295509295909350565b60008060408385031215610f3557600080fd5b823567ffffffffffffffff80821115610f4d57600080fd5b818501915085601f830112610f6157600080fd5b81356020610f6e82610d6f565b604051610f7b8282610d42565b83815260059390931b8501820192828101915089841115610f9b57600080fd5b948201945b83861015610fc057610fb186610c2d565b82529482019490820190610fa0565b96505086013592505080821115610fd657600080fd5b50610fe385828601610d93565b9150509250929050565b600081518084526020808501945080840160005b8381101561101d57815187529582019590820190600101611001565b509495945050505050565b602081526000610ca96020830184610fed565b600080600080600060a0868803121561105357600080fd5b85359450602086013567ffffffffffffffff8082111561107257600080fd5b61107e89838a01610e04565b9550604088013591508082111561109457600080fd5b6110a089838a01610e04565b945060608801359150808211156110b657600080fd5b610ef289838a01610e04565b602081526000825160a060208401526110de60c0840182610ced565b90506020840151601f19808584030160408601526110fc8383610ced565b925060408601519150808584030160608601526111198383610ced565b92506060860151915080858403016080860152506111378282610ced565b915050608084015160a08401528091505092915050565b6000806040838503121561116157600080fd5b61116a83610c2d565b91506020830135801515811461117f57600080fd5b809150509250929050565b6000806040838503121561119d57600080fd5b6111a683610c2d565b91506111b460208401610c2d565b90509250929050565b600080600080600060a086880312156111d557600080fd5b6111de86610c2d565b94506111ec60208701610c2d565b93506040860135925060608601359150608086013567ffffffffffffffff81111561121657600080fd5b610f1588828901610e04565b600181811c9082168061123657607f821691505b60208210810361125657634e487b7160e01b600052602260045260246000fd5b50919050565b6020808252602e908201527f455243313135353a2063616c6c6572206973206e6f7420746f6b656e206f776e60408201526d195c881bdc88185c1c1c9bdd995960921b606082015260800190565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b6000600182016112e8576112e86112c0565b5060010190565b60008651611301818460208b01610cc9565b865190830190611315818360208b01610cc9565b8651910190611328818360208a01610cc9565b855191019061133b818360208901610cc9565b60609490941b6bffffffffffffffffffffffff191693019283525050601401949350505050565b60208082526025908201527f455243313135353a207472616e7366657220746f20746865207a65726f206164604082015264647265737360d81b606082015260800190565b6020808252602a908201527f455243313135353a20696e73756666696369656e742062616c616e636520666f60408201526939103a3930b739b332b960b11b606082015260800190565b8082018082111561024a5761024a6112c0565b6040815260006114176040830185610fed565b82810360208401526114298185610fed565b95945050505050565b6001600160a01b0386811682528516602082015260a06040820181905260009061145e90830186610fed565b82810360608401526114708186610fed565b905082810360808401526114848185610ced565b98975050505050505050565b6000602082840312156114a257600080fd5b8151610ca981610c73565b600060033d11156114c65760046000803e5060005160e01c5b90565b600060443d10156114d75790565b6040516003193d81016004833e81513d67ffffffffffffffff816024840111818411171561150757505050505090565b828501915081518181111561151f5750505050505090565b843d87010160208285010111156115395750505050505090565b61154860208286010187610d42565b509095945050505050565b60208082526028908201527f455243313135353a204552433131353552656365697665722072656a656374656040820152676420746f6b656e7360c01b606082015260800190565b6001600160a01b03868116825285166020820152604081018490526060810183905260a0608082018190526000906115d590830184610ced565b97965050505050505056fea264697066735822122000c92cd8bccabcec9fa13ebc179043363e65cff69cbe12c8426fa1f5a86cc6d964736f6c63430008130033";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BALANCEOFBATCH = "balanceOfBatch";

    public static final String FUNC_CREACERTIFICATO = "creaCertificato";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_SAFEBATCHTRANSFERFROM = "safeBatchTransferFrom";

    public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_URI = "uri";

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event TRANSFERBATCH_EVENT = new Event("TransferBatch", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
    ;

    public static final Event TRANSFERSINGLE_EVENT = new Event("TransferSingle", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event URI_EVENT = new Event("URI", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected CertificatoCorso(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CertificatoCorso(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CertificatoCorso(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CertificatoCorso(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public   List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public   List<TransferBatchEventResponse> getTransferBatchEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERBATCH_EVENT, transactionReceipt);
        ArrayList<TransferBatchEventResponse> responses = new ArrayList<TransferBatchEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferBatchEventResponse typedResponse = new TransferBatchEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.ids = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getValue();
            typedResponse.values = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(1)).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferBatchEventResponse> transferBatchEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferBatchEventResponse>() {
            @Override
            public TransferBatchEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERBATCH_EVENT, log);
                TransferBatchEventResponse typedResponse = new TransferBatchEventResponse();
                typedResponse.log = log;
                typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.ids = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getValue();
                typedResponse.values = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(1)).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferBatchEventResponse> transferBatchEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERBATCH_EVENT));
        return transferBatchEventFlowable(filter);
    }

    public   List<TransferSingleEventResponse> getTransferSingleEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERSINGLE_EVENT, transactionReceipt);
        ArrayList<TransferSingleEventResponse> responses = new ArrayList<TransferSingleEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferSingleEventResponse typedResponse = new TransferSingleEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferSingleEventResponse> transferSingleEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferSingleEventResponse>() {
            @Override
            public TransferSingleEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERSINGLE_EVENT, log);
                TransferSingleEventResponse typedResponse = new TransferSingleEventResponse();
                typedResponse.log = log;
                typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferSingleEventResponse> transferSingleEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERSINGLE_EVENT));
        return transferSingleEventFlowable(filter);
    }

    public   List<URIEventResponse> getURIEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(URI_EVENT, transactionReceipt);
        ArrayList<URIEventResponse> responses = new ArrayList<URIEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            URIEventResponse typedResponse = new URIEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.value = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<URIEventResponse> uRIEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, URIEventResponse>() {
            @Override
            public URIEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(URI_EVENT, log);
                URIEventResponse typedResponse = new URIEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.value = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<URIEventResponse> uRIEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(URI_EVENT));
        return uRIEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String account, BigInteger id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account), 
                new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> balanceOfBatch(List<String> accounts, List<BigInteger> ids) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOFBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(accounts, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(ids, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Certificato> creaCertificato(BigInteger idCertificato, String _nomeCorso, String _pubblicazioneData, String _infoUtente, String _infoTeacher) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CREACERTIFICATO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(idCertificato), 
                new org.web3j.abi.datatypes.Utf8String(_nomeCorso), 
                new org.web3j.abi.datatypes.Utf8String(_pubblicazioneData), 
                new org.web3j.abi.datatypes.Utf8String(_infoUtente), 
                new org.web3j.abi.datatypes.Utf8String(_infoTeacher)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Certificato>() {}));
        return executeRemoteCallSingleValueReturn(function, Certificato.class);
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String account, String operator) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account), 
                new org.web3j.abi.datatypes.Address(160, operator)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> safeBatchTransferFrom(String from, String to, List<BigInteger> ids, List<BigInteger> amounts, byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SAFEBATCHTRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(ids, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(amounts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger id, BigInteger amount, byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(id), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator, Boolean approved) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator), 
                new org.web3j.abi.datatypes.Bool(approved)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> uri(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_URI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static CertificatoCorso load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CertificatoCorso(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CertificatoCorso load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CertificatoCorso(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CertificatoCorso load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CertificatoCorso(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CertificatoCorso load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CertificatoCorso(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CertificatoCorso> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CertificatoCorso.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<CertificatoCorso> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CertificatoCorso.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CertificatoCorso> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertificatoCorso.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CertificatoCorso> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertificatoCorso.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Certificato extends DynamicStruct {
        public String nomeCorso;

        public String pubblicazioneData;

        public String DateUtente;

        public String DateTeacher;

        public byte[] firma;

        public Certificato(String nomeCorso, String pubblicazioneData, String DateUtente, String DateTeacher, byte[] firma) {
            super(new org.web3j.abi.datatypes.Utf8String(nomeCorso), 
                    new org.web3j.abi.datatypes.Utf8String(pubblicazioneData), 
                    new org.web3j.abi.datatypes.Utf8String(DateUtente), 
                    new org.web3j.abi.datatypes.Utf8String(DateTeacher), 
                    new org.web3j.abi.datatypes.generated.Bytes32(firma));
            this.nomeCorso = nomeCorso;
            this.pubblicazioneData = pubblicazioneData;
            this.DateUtente = DateUtente;
            this.DateTeacher = DateTeacher;
            this.firma = firma;
        }

        public Certificato(Utf8String nomeCorso, Utf8String pubblicazioneData, Utf8String DateUtente, Utf8String DateTeacher, Bytes32 firma) {
            super(nomeCorso, pubblicazioneData, DateUtente, DateTeacher, firma);
            this.nomeCorso = nomeCorso.getValue();
            this.pubblicazioneData = pubblicazioneData.getValue();
            this.DateUtente = DateUtente.getValue();
            this.DateTeacher = DateTeacher.getValue();
            this.firma = firma.getValue();
        }
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String account;

        public String operator;

        public Boolean approved;
    }

    public static class TransferBatchEventResponse extends BaseEventResponse {
        public String operator;

        public String from;

        public String to;

        public List<BigInteger> ids;

        public List<BigInteger> values;
    }

    public static class TransferSingleEventResponse extends BaseEventResponse {
        public String operator;

        public String from;

        public String to;

        public BigInteger id;

        public BigInteger value;
    }

    public static class URIEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String value;
    }
}
