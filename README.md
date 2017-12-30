# Homeward

Homeward is a platform targeting refugee resettlement.

It has majorly 3 broad components:

1) Smart Matrix - Powered by Artificial Intelligence and Machine Learning that focuses on multiple factors like country quotas, family & community, education & certification validity, professional Experience & Capabilities, language, cultural & religious preferences in order to measure the potential success of reintegration for the Refugees

2) Biometrics - Powered by IOT, aims at collecting data such as fingerprints, iris scan, facial recognition, voice recognition and other documents scan such as passport and driver's license of the refugees to start building their

3) Identity Management - This is implemented using smart contracts to provide a secure, tamper proof ledger of identity in conjunction with IPFS to store the documents and detailed information and integrated with a decentralized app that provides the user interface to all the interacting parties and nodes in the network

This project is still under development and the MVP focuses on smart matrix and the identity management system through smart contracts.

For more details on the problem statement and the concept, please refer the "Documentation" folder.

For more understanding of the architecture and components refer Architecture/Homeward.jpg

For referring the code, look at "smartcontracts" folder.

A factory pattern has been implemented to create a new instance of the person smart contract every time from the dashboard.
The person object only stored the ipfsHash which is obtained from the IPFS for the related data stored in json format in the IPFS.
