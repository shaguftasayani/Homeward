//import '../../stylesheets/app.css'
import { initWeb3, addToIPFS, getImage,getNoOfDocs} from './eth_link'

// Initialize contract and IPFS
initWeb3();


//document.getElementById("start").onclick = function () { alert('hello!'); };

  
  $(window).load(function(){document.getElementById('getval').addEventListener('change', readURL, true);
  console.log("here");
  function readURL(){
      var file = document.getElementById('getval').files[0];
      var reader = new FileReader();
      reader.onloadend = function(){
          document.getElementById('clock').style.backgroundImage = "url(" + reader.result + ")";        
      }
      if(file){
          
          reader.readAsDataURL(file);
      }else{
      }
  }
  })
  