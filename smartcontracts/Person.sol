pragma solidity ^0.4.15;

contract Person {

    bytes32 ipfsHash;

    event IpfsHashUpdated(address _who, bytes32 _ipfsHash, uint _timestamp);

    function setIpfsHash(bytes32 _ipfsHash) returns (bool) {
        ipfsHash= _ipfsHash;
        IpfsHashUpdated(tx.origin, ipfsHash, now);
        return true;
    }

    function getIpfsHash() returns (bytes32) {
        return ipfsHash;
    }

}
