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
    public static final String BINARY = "60806040523480156200001157600080fd5b506040805160208101909152600081526200002c8162000033565b50620001b6565b6002620000418282620000ea565b5050565b634e487b7160e01b600052604160045260246000fd5b600181811c908216806200007057607f821691505b6020821081036200009157634e487b7160e01b600052602260045260246000fd5b50919050565b601f821115620000e557600081815260208120601f850160051c81016020861015620000c05750805b601f850160051c820191505b81811015620000e157828155600101620000cc565b5050505b505050565b81516001600160401b0381111562000106576200010662000045565b6200011e816200011784546200005b565b8462000097565b602080601f8311600181146200015657600084156200013d5750858301515b600019600386901b1c1916600185901b178555620000e1565b600085815260208120601f198616915b82811015620001875788860151825594840194600190910190840162000166565b5085821015620001a65787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b611d9680620001c66000396000f3fe608060405234801561001057600080fd5b50600436106100a85760003560e01c8063607afca311610071578063607afca31461014b578063a22cb4651461015e578063da8548c714610171578063e985e9c514610184578063ea6ee0e9146101c0578063f242432a146101e457600080fd5b8062fdd58e146100ad57806301ffc9a7146100d35780630e89341c146100f65780632eb2c2d6146101165780634e1273f41461012b575b600080fd5b6100c06100bb366004611248565b6101f7565b6040519081526020015b60405180910390f35b6100e66100e136600461128b565b610290565b60405190151581526020016100ca565b6101096101043660046112af565b6102e0565b6040516100ca9190611318565b610129610124366004611477565b610374565b005b61013e610139366004611521565b6103c0565b6040516100ca9190611627565b6100e661015936600461163a565b6104ea565b61012961016c3660046116c1565b610610565b61010961017f3660046112af565b61061f565b6100e66101923660046116fd565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205460ff1690565b6101d36101ce366004611248565b6108fe565b6040516100ca959493929190611730565b6101296101f2366004611790565b610b57565b60006001600160a01b0383166102675760405162461bcd60e51b815260206004820152602a60248201527f455243313135353a2061646472657373207a65726f206973206e6f742061207660448201526930b634b21037bbb732b960b11b60648201526084015b60405180910390fd5b506000818152602081815260408083206001600160a01b03861684529091529020545b92915050565b60006001600160e01b03198216636cdb3d1360e11b14806102c157506001600160e01b031982166303a24d0760e21b145b8061028a57506301ffc9a760e01b6001600160e01b031983161461028a565b6060600280546102ef906117f5565b80601f016020809104026020016040519081016040528092919081815260200182805461031b906117f5565b80156103685780601f1061033d57610100808354040283529160200191610368565b820191906000526020600020905b81548152906001019060200180831161034b57829003601f168201915b50505050509050919050565b6001600160a01b03851633148061039057506103908533610192565b6103ac5760405162461bcd60e51b815260040161025e9061182f565b6103b98585858585610b9c565b5050505050565b606081518351146104255760405162461bcd60e51b815260206004820152602960248201527f455243313135353a206163636f756e747320616e6420696473206c656e677468604482015268040dad2e6dac2e8c6d60bb1b606482015260840161025e565b6000835167ffffffffffffffff8111156104415761044161132b565b60405190808252806020026020018201604052801561046a578160200160208202803683370190505b50905060005b84518110156104e2576104b585828151811061048e5761048e61187d565b60200260200101518583815181106104a8576104a861187d565b60200260200101516101f7565b8282815181106104c7576104c761187d565b60209081029190910101526104db816118a9565b9050610470565b509392505050565b60008085858585336040516020016105069594939291906118c2565b60408051601f19818403018152828252805160209182012060a0840183528984528184018990528383018890526060840187905260808401819052336000908152600383528381208c825290925291902082519193508291819061056a9082611980565b506020820151600182019061057f9082611980565b50604082015160028201906105949082611980565b50606082015160038201906105a99082611980565b506080820151816004015590505060006105c282610d79565b90507f86b8c9a0013d2456591ae51347476d48de75f275fca6e4d2b74b4c956f18239d8933836040516105f793929190611a40565b60405180910390a1600193505050505b95945050505050565b61061b338383610db8565b5050565b60606000821161067d5760405162461bcd60e51b815260206004820152602360248201527f4c2769642064656c20636572746966696361746f206e6f6e2065272076616c6960448201526203237960ed1b606482015260840161025e565b33600090815260036020908152604080832085845290915290819020815160a08101909252805461028a929190829082906106b7906117f5565b80601f01602080910402602001604051908101604052809291908181526020018280546106e3906117f5565b80156107305780601f1061070557610100808354040283529160200191610730565b820191906000526020600020905b81548152906001019060200180831161071357829003601f168201915b50505050508152602001600182018054610749906117f5565b80601f0160208091040260200160405190810160405280929190818152602001828054610775906117f5565b80156107c25780601f10610797576101008083540402835291602001916107c2565b820191906000526020600020905b8154815290600101906020018083116107a557829003601f168201915b505050505081526020016002820180546107db906117f5565b80601f0160208091040260200160405190810160405280929190818152602001828054610807906117f5565b80156108545780601f1061082957610100808354040283529160200191610854565b820191906000526020600020905b81548152906001019060200180831161083757829003601f168201915b5050505050815260200160038201805461086d906117f5565b80601f0160208091040260200160405190810160405280929190818152602001828054610899906117f5565b80156108e65780601f106108bb576101008083540402835291602001916108e6565b820191906000526020600020905b8154815290600101906020018083116108c957829003601f168201915b50505050508152602001600482015481525050610d79565b6003602090815260009283526040808420909152908252902080548190610924906117f5565b80601f0160208091040260200160405190810160405280929190818152602001828054610950906117f5565b801561099d5780601f106109725761010080835404028352916020019161099d565b820191906000526020600020905b81548152906001019060200180831161098057829003601f168201915b5050505050908060010180546109b2906117f5565b80601f01602080910402602001604051908101604052809291908181526020018280546109de906117f5565b8015610a2b5780601f10610a0057610100808354040283529160200191610a2b565b820191906000526020600020905b815481529060010190602001808311610a0e57829003601f168201915b505050505090806002018054610a40906117f5565b80601f0160208091040260200160405190810160405280929190818152602001828054610a6c906117f5565b8015610ab95780601f10610a8e57610100808354040283529160200191610ab9565b820191906000526020600020905b815481529060010190602001808311610a9c57829003601f168201915b505050505090806003018054610ace906117f5565b80601f0160208091040260200160405190810160405280929190818152602001828054610afa906117f5565b8015610b475780601f10610b1c57610100808354040283529160200191610b47565b820191906000526020600020905b815481529060010190602001808311610b2a57829003601f168201915b5050505050908060040154905085565b6001600160a01b038516331480610b735750610b738533610192565b610b8f5760405162461bcd60e51b815260040161025e9061182f565b6103b98585858585610e98565b8151835114610bfe5760405162461bcd60e51b815260206004820152602860248201527f455243313135353a2069647320616e6420616d6f756e7473206c656e677468206044820152670dad2e6dac2e8c6d60c31b606482015260840161025e565b6001600160a01b038416610c245760405162461bcd60e51b815260040161025e90611a6a565b3360005b8451811015610d0b576000858281518110610c4557610c4561187d565b602002602001015190506000858381518110610c6357610c6361187d565b602090810291909101810151600084815280835260408082206001600160a01b038e168352909352919091205490915081811015610cb35760405162461bcd60e51b815260040161025e90611aaf565b6000838152602081815260408083206001600160a01b038e8116855292528083208585039055908b16825281208054849290610cf0908490611af9565b9250508190555050505080610d04906118a9565b9050610c28565b50846001600160a01b0316866001600160a01b0316826001600160a01b03167f4a39dc06d4c0dbc64b70af90fd698a233a518aa5d07e595d983b8c0526c8f7fb8787604051610d5b929190611b0c565b60405180910390a4610d71818787878787610fc2565b505050505050565b60608160000151826020015183604001518460600151604051602001610da29493929190611b31565b6040516020818303038152906040529050919050565b816001600160a01b0316836001600160a01b031603610e2b5760405162461bcd60e51b815260206004820152602960248201527f455243313135353a2073657474696e6720617070726f76616c20737461747573604482015268103337b91039b2b63360b91b606482015260840161025e565b6001600160a01b03838116600081815260016020908152604080832094871680845294825291829020805460ff191686151590811790915591519182527f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a3505050565b6001600160a01b038416610ebe5760405162461bcd60e51b815260040161025e90611a6a565b336000610eca85611126565b90506000610ed785611126565b90506000868152602081815260408083206001600160a01b038c16845290915290205485811015610f1a5760405162461bcd60e51b815260040161025e90611aaf565b6000878152602081815260408083206001600160a01b038d8116855292528083208985039055908a16825281208054889290610f57908490611af9565b909155505060408051888152602081018890526001600160a01b03808b16928c821692918816917fc3d58168c5ae7397731d063d5bbf3d657854427343f4c083240f7aacaa2d0f62910160405180910390a4610fb7848a8a8a8a8a611171565b505050505050505050565b6001600160a01b0384163b15610d715760405163bc197c8160e01b81526001600160a01b0385169063bc197c81906110069089908990889088908890600401611bb2565b6020604051808303816000875af1925050508015611041575060408051601f3d908101601f1916820190925261103e91810190611c10565b60015b6110ed5761104d611c2d565b806308c379a0036110865750611061611c49565b8061106c5750611088565b8060405162461bcd60e51b815260040161025e9190611318565b505b60405162461bcd60e51b815260206004820152603460248201527f455243313135353a207472616e7366657220746f206e6f6e2d455243313135356044820152732932b1b2b4bb32b91034b6b83632b6b2b73a32b960611b606482015260840161025e565b6001600160e01b0319811663bc197c8160e01b1461111d5760405162461bcd60e51b815260040161025e90611cd3565b50505050505050565b604080516001808252818301909252606091600091906020808301908036833701905050905082816000815181106111605761116061187d565b602090810291909101015292915050565b6001600160a01b0384163b15610d715760405163f23a6e6160e01b81526001600160a01b0385169063f23a6e61906111b59089908990889088908890600401611d1b565b6020604051808303816000875af19250505080156111f0575060408051601f3d908101601f191682019092526111ed91810190611c10565b60015b6111fc5761104d611c2d565b6001600160e01b0319811663f23a6e6160e01b1461111d5760405162461bcd60e51b815260040161025e90611cd3565b80356001600160a01b038116811461124357600080fd5b919050565b6000806040838503121561125b57600080fd5b6112648361122c565b946020939093013593505050565b6001600160e01b03198116811461128857600080fd5b50565b60006020828403121561129d57600080fd5b81356112a881611272565b9392505050565b6000602082840312156112c157600080fd5b5035919050565b60005b838110156112e35781810151838201526020016112cb565b50506000910152565b600081518084526113048160208601602086016112c8565b601f01601f19169290920160200192915050565b6020815260006112a860208301846112ec565b634e487b7160e01b600052604160045260246000fd5b601f8201601f1916810167ffffffffffffffff811182821017156113675761136761132b565b6040525050565b600067ffffffffffffffff8211156113885761138861132b565b5060051b60200190565b600082601f8301126113a357600080fd5b813560206113b08261136e565b6040516113bd8282611341565b83815260059390931b85018201928281019150868411156113dd57600080fd5b8286015b848110156113f857803583529183019183016113e1565b509695505050505050565b600082601f83011261141457600080fd5b813567ffffffffffffffff81111561142e5761142e61132b565b604051611445601f8301601f191660200182611341565b81815284602083860101111561145a57600080fd5b816020850160208301376000918101602001919091529392505050565b600080600080600060a0868803121561148f57600080fd5b6114988661122c565b94506114a66020870161122c565b9350604086013567ffffffffffffffff808211156114c357600080fd5b6114cf89838a01611392565b945060608801359150808211156114e557600080fd5b6114f189838a01611392565b9350608088013591508082111561150757600080fd5b5061151488828901611403565b9150509295509295909350565b6000806040838503121561153457600080fd5b823567ffffffffffffffff8082111561154c57600080fd5b818501915085601f83011261156057600080fd5b8135602061156d8261136e565b60405161157a8282611341565b83815260059390931b850182019282810191508984111561159a57600080fd5b948201945b838610156115bf576115b08661122c565b8252948201949082019061159f565b965050860135925050808211156115d557600080fd5b506115e285828601611392565b9150509250929050565b600081518084526020808501945080840160005b8381101561161c57815187529582019590820190600101611600565b509495945050505050565b6020815260006112a860208301846115ec565b600080600080600060a0868803121561165257600080fd5b85359450602086013567ffffffffffffffff8082111561167157600080fd5b61167d89838a01611403565b9550604088013591508082111561169357600080fd5b61169f89838a01611403565b945060608801359150808211156116b557600080fd5b6114f189838a01611403565b600080604083850312156116d457600080fd5b6116dd8361122c565b9150602083013580151581146116f257600080fd5b809150509250929050565b6000806040838503121561171057600080fd5b6117198361122c565b91506117276020840161122c565b90509250929050565b60a08152600061174360a08301886112ec565b828103602084015261175581886112ec565b9050828103604084015261176981876112ec565b9050828103606084015261177d81866112ec565b9150508260808301529695505050505050565b600080600080600060a086880312156117a857600080fd5b6117b18661122c565b94506117bf6020870161122c565b93506040860135925060608601359150608086013567ffffffffffffffff8111156117e957600080fd5b61151488828901611403565b600181811c9082168061180957607f821691505b60208210810361182957634e487b7160e01b600052602260045260246000fd5b50919050565b6020808252602e908201527f455243313135353a2063616c6c6572206973206e6f7420746f6b656e206f776e60408201526d195c881bdc88185c1c1c9bdd995960921b606082015260800190565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b6000600182016118bb576118bb611893565b5060010190565b600086516118d4818460208b016112c8565b8651908301906118e8818360208b016112c8565b86519101906118fb818360208a016112c8565b855191019061190e8183602089016112c8565b60609490941b6bffffffffffffffffffffffff191693019283525050601401949350505050565b601f82111561197b57600081815260208120601f850160051c8101602086101561195c5750805b601f850160051c820191505b81811015610d7157828155600101611968565b505050565b815167ffffffffffffffff81111561199a5761199a61132b565b6119ae816119a884546117f5565b84611935565b602080601f8311600181146119e357600084156119cb5750858301515b600019600386901b1c1916600185901b178555610d71565b600085815260208120601f198616915b82811015611a12578886015182559484019460019091019084016119f3565b5085821015611a305787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b8381526001600160a01b0383166020820152606060408201819052600090610607908301846112ec565b60208082526025908201527f455243313135353a207472616e7366657220746f20746865207a65726f206164604082015264647265737360d81b606082015260800190565b6020808252602a908201527f455243313135353a20696e73756666696369656e742062616c616e636520666f60408201526939103a3930b739b332b960b11b606082015260800190565b8082018082111561028a5761028a611893565b604081526000611b1f60408301856115ec565b828103602084015261060781856115ec565b60008551611b43818460208a016112c8565b8083019050602d60f81b8082528651611b63816001850160208b016112c8565b600192019182018190528551611b80816002850160208a016112c8565b600292019182018190528451611b9d8160038501602089016112c8565b60039201918201526004019695505050505050565b6001600160a01b0386811682528516602082015260a060408201819052600090611bde908301866115ec565b8281036060840152611bf081866115ec565b90508281036080840152611c0481856112ec565b98975050505050505050565b600060208284031215611c2257600080fd5b81516112a881611272565b600060033d1115611c465760046000803e5060005160e01c5b90565b600060443d1015611c575790565b6040516003193d81016004833e81513d67ffffffffffffffff8160248401118184111715611c8757505050505090565b8285019150815181811115611c9f5750505050505090565b843d8701016020828501011115611cb95750505050505090565b611cc860208286010187611341565b509095945050505050565b60208082526028908201527f455243313135353a204552433131353552656365697665722072656a656374656040820152676420746f6b656e7360c01b606082015260800190565b6001600160a01b03868116825285166020820152604081018490526060810183905260a060808201819052600090611d55908301846112ec565b97965050505050505056fea2646970667358221220061f2d8939d586b2e7eeecad493c4aab39dd3681e49ac446bedecbd7ec5ae7fb64736f6c63430008130033";

    public static final String FUNC__INFOUTENTEWITHCERTIFICATI = "_infoUtenteWithCertificati";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BALANCEOFBATCH = "balanceOfBatch";

    public static final String FUNC_CREACERTIFICATO = "creaCertificato";

    public static final String FUNC_GETCERTIFICATO = "getCertificato";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_SAFEBATCHTRANSFERFROM = "safeBatchTransferFrom";

    public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_URI = "uri";

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event CERTIFICATOCREATOFATTO_EVENT = new Event("CertificatoCreatoFatto", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> creaCertificato(BigInteger idCertificato, String _nomeCorso, String _pubblicazioneData, String _infoUtente, String _infoTeacher) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREACERTIFICATO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(idCertificato), 
                new org.web3j.abi.datatypes.Utf8String(_nomeCorso), 
                new org.web3j.abi.datatypes.Utf8String(_pubblicazioneData), 
                new org.web3j.abi.datatypes.Utf8String(_infoUtente), 
                new org.web3j.abi.datatypes.Utf8String(_infoTeacher)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getCertificato(BigInteger idCertificato) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCERTIFICATO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(idCertificato)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public static RemoteCall<CertificatoCorsoViLear> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CertificatoCorsoViLear.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<CertificatoCorsoViLear> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CertificatoCorsoViLear.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CertificatoCorsoViLear> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertificatoCorsoViLear.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CertificatoCorsoViLear> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CertificatoCorsoViLear.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String account;

        public String operator;

        public Boolean approved;
    }

    public static class CertificatoCreatoFattoEventResponse extends BaseEventResponse {
        public BigInteger idCertificato;

        public String wallet;

        public String certificatoToString;
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
