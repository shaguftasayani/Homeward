pragma solidity 0.4.19;
import "./Person.sol";
contract Factory{

    uint currentId = 0;

    event NewPerson(uint indexed _id, address indexed _owner, address indexed _personAddr);

    mapping(uint => address) public personInfo;

    function newPerson() returns (uint) {
        currentId++;
        Person p = new Person();
        personInfo[currentId] = p;
        NewPerson(currentId, msg.sender, p);
        return currentId;
    }

    function getPersonInfo(uint _id) returns (address) {
        return personInfo[_id];
    }

}
