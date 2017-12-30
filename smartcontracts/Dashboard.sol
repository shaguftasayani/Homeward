pragma solidity 0.4.19;

import "./Factory.sol";
import "./Person.sol";

contract Dashboard{

    address factoryAddr;
    Factory database;

    function Dashboard(address _factoryAddr){
        factoryAddr = _factoryAddr;
        database = Factory(_factoryAddr);
    }

    function deployPerson() returns (bool){
        database.newPerson();
        return true;
    }
    function incrementPerson(uint _personId, bytes32 ipfsHash) constant returns (bool) {
        address personAddr = database.getPersonInfo(_personId);
        Person person = Person(personAddr);
        person.setIpfsHash(ipfsHash);
        return true;
    }

    function viewPersonInfo(uint _personId) constant returns (bytes32) {
        address personAddr = database.getPersonInfo(_personId);
        Person person = Person(personAddr);
        return person.getIpfsHash();
    }

}
