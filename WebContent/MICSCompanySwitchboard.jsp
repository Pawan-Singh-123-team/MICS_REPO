<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	function changeFirstLevelSelection(level) {
		
      switch (level) {
      case 1: 
    	
      	if(document.companySwitchboardCreate.firstLevelSelect1.value == "0"){
      		document.getElementById("idBtnDest1").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest1.value = "";
      		document.getElementById("idSubmenu1").style.visibility= "hidden";
      		document.getElementById("idBtnDest11").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest11.value = "";
      		document.getElementById("idBtnDest12").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest12.value = "";
      		document.getElementById("idBtnDest13").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest13.value = "";
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect1.value == "1"){
      		document.getElementById("idBtnDest1").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest1.value = "";
      		document.getElementById("idSubmenu1").style.visibility= "visible";
      		document.companySwitchboardCreate.submenu1LevelSelect1.value = "0";
      		document.companySwitchboardCreate.submenu1LevelSelect2.value = "0";
      		document.companySwitchboardCreate.submenu1LevelSelect3.value = "0";
      		if(document.companySwitchboardCreate.submenu1LevelSelect1.value == "2"){
      			document.getElementById("idBtnDest11").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu1LevelSelect2.value == "2"){
      			document.getElementById("idBtnDest12").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu1LevelSelect3.value == "2"){
      			document.getElementById("idBtnDest13").style.visibility= "visible";
      		}
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect1.value == "2"){
      		document.getElementById("idBtnDest1").style.visibility= "visible";
      		document.getElementById("idSubmenu1").style.visibility= "hidden";
      		document.getElementById("idBtnDest11").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest11.value = "";
      		document.getElementById("idBtnDest12").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest12.value = "";
      		document.getElementById("idBtnDest13").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest13.value = "";
      	}
        break;
      case 2: 
      	if(document.companySwitchboardCreate.firstLevelSelect2.value == "0"){
      		document.getElementById("idBtnDest2").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest2.value = "";
      		document.getElementById("idSubmenu2").style.visibility= "hidden";
      		document.getElementById("idBtnDest21").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest21.value = "";
      		document.getElementById("idBtnDest22").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest22.value = "";
      		document.getElementById("idBtnDest23").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest23.value = "";
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect2.value == "1"){
      		document.getElementById("idBtnDest2").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest2.value = "";
      		document.getElementById("idSubmenu2").style.visibility= "visible";
      		document.companySwitchboardCreate.submenu2LevelSelect1.value = "0";
      		document.companySwitchboardCreate.submenu2LevelSelect2.value = "0";
      		document.companySwitchboardCreate.submenu2LevelSelect3.value = "0";
      		if(document.companySwitchboardCreate.submenu2LevelSelect1.value == "2"){
      			document.getElementById("idBtnDest21").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu2LevelSelect2.value == "2"){
      			document.getElementById("idBtnDest22").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu2LevelSelect3.value == "2"){
      			document.getElementById("idBtnDest23").style.visibility= "visible";
      		}
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect2.value == "2"){
      		document.getElementById("idBtnDest2").style.visibility= "visible";
      		document.getElementById("idSubmenu2").style.visibility= "hidden";
      		document.getElementById("idBtnDest21").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest21.value = "";
      		document.getElementById("idBtnDest22").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest22.value = "";
      		document.getElementById("idBtnDest23").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest23.value = "";
      	}
      	
        break;
      case 3: 
      	if(document.companySwitchboardCreate.firstLevelSelect3.value == "0"){
      		document.getElementById("idBtnDest3").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest3.value = "";
      		document.getElementById("idSubmenu3").style.visibility= "hidden";
      		document.getElementById("idBtnDest31").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest31.value = "";
      		document.getElementById("idBtnDest32").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest32.value = "";
      		document.getElementById("idBtnDest33").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest33.value = "";
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect3.value == "1"){
      		document.getElementById("idBtnDest3").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest3.value = "";
      		document.getElementById("idSubmenu3").style.visibility= "visible";
      		document.companySwitchboardCreate.submenu3LevelSelect1.value = "0";
      		document.companySwitchboardCreate.submenu3LevelSelect2.value = "0";
      		document.companySwitchboardCreate.submenu3LevelSelect3.value = "0";
      		if(document.companySwitchboardCreate.submenu3LevelSelect1.value == "2"){
      			document.getElementById("idBtnDest31").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu3LevelSelect2.value == "2"){
      			document.getElementById("idBtnDest32").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu3LevelSelect3.value == "2"){
      			document.getElementById("idBtnDest33").style.visibility= "visible";
      		}
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect3.value == "2"){
      		document.getElementById("idBtnDest3").style.visibility= "visible";
      		document.getElementById("idSubmenu3").style.visibility= "hidden";
      		document.getElementById("idBtnDest31").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest31.value = "";
      		document.getElementById("idBtnDest32").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest32.value = "";
      		document.getElementById("idBtnDest33").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest33.value = "";
      	}
        break;
      case 4: 
      	if(document.companySwitchboardCreate.firstLevelSelect4.value == "0"){
      		document.getElementById("idBtnDest4").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest4.value = "";
      		document.getElementById("idSubmenu4").style.visibility= "hidden";
      		document.getElementById("idBtnDest41").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest41.value = "";
      		document.getElementById("idBtnDest42").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest42.value = "";
      		document.getElementById("idBtnDest43").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest43.value = "";
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect4.value == "1"){
      		document.getElementById("idBtnDest4").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest4.value = "";
      		document.getElementById("idSubmenu4").style.visibility= "visible";
      		document.companySwitchboardCreate.submenu4LevelSelect1.value = "0";
      		document.companySwitchboardCreate.submenu4LevelSelect2.value = "0";
      		document.companySwitchboardCreate.submenu4LevelSelect3.value = "0";
      		if(document.companySwitchboardCreate.submenu4LevelSelect1.value == "2"){
      			document.getElementById("idBtnDest41").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu4LevelSelect2.value == "2"){
      			document.getElementById("idBtnDest42").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu4LevelSelect3.value == "2"){
      			document.getElementById("idBtnDest43").style.visibility= "visible";
      		}
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect4.value == "2"){
      		document.getElementById("idBtnDest4").style.visibility= "visible";
      		document.getElementById("idSubmenu4").style.visibility= "hidden";
      		document.getElementById("idBtnDest41").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest41.value = "";
      		document.getElementById("idBtnDest42").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest42.value = "";
      		document.getElementById("idBtnDest43").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest43.value = "";
      	}
        break;
      case 5: 
      	if(document.companySwitchboardCreate.firstLevelSelect5.value == "0"){
      		document.getElementById("idBtnDest5").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest5.value = "";
      		document.getElementById("idSubmenu5").style.visibility= "hidden";
      		document.getElementById("idBtnDest51").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest51.value = "";
      		document.getElementById("idBtnDest52").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest52.value = "";
      		document.getElementById("idBtnDest53").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest53.value = "";
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect5.value == "1"){
      		document.getElementById("idBtnDest5").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest5.value = "";
      		document.getElementById("idSubmenu5").style.visibility= "visible";
      		document.companySwitchboardCreate.submenu5LevelSelect1.value = "0";
      		document.companySwitchboardCreate.submenu5LevelSelect2.value = "0";
      		document.companySwitchboardCreate.submenu51evelSelect3.value = "0";
      		if(document.companySwitchboardCreate.submenu5LevelSelect1.value == "2"){
      			document.getElementById("idBtnDest51").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu5LevelSelect2.value == "2"){
      			document.getElementById("idBtnDest52").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu5LevelSelect3.value == "2"){
      			document.getElementById("idBtnDest53").style.visibility= "visible";
      		}
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect5.value == "2"){
      		document.getElementById("idBtnDest5").style.visibility= "visible";
      		document.getElementById("idSubmenu5").style.visibility= "hidden";
      		document.getElementById("idBtnDest51").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest51.value = "";
      		document.getElementById("idBtnDest52").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest52.value = "";
      		document.getElementById("idBtnDest53").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest53.value = "";
      	}
        break;
      }
                 
 
    }
    function changeSecondLevelSelection(level) {
      switch (level) {
      case 11: 
      	if(document.companySwitchboardCreate.submenu1LevelSelect1.value == "0"){
      		document.getElementById("idBtnDest11").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest11.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu1LevelSelect1.value == "2"){
      		document.getElementById("idBtnDest11").style.visibility= "visible";
      	}     
        break;
      case 12: 
      	if(document.companySwitchboardCreate.submenu1LevelSelect2.value == "0"){
      		document.getElementById("idBtnDest12").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest12.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu1LevelSelect2.value == "2"){
      		document.getElementById("idBtnDest12").style.visibility= "visible";
      	}     
        break;
      case 13: 
      	if(document.companySwitchboardCreate.submenu1LevelSelect3.value == "0"){
      		document.getElementById("idBtnDest13").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest13.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu1LevelSelect3.value == "2"){
      		document.getElementById("idBtnDest13").style.visibility= "visible";
      	}     
        break;
      case 21: 
      	if(document.companySwitchboardCreate.submenu2LevelSelect1.value == "0"){
      		document.getElementById("idBtnDest21").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest21.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu2LevelSelect1.value == "2"){
      		document.getElementById("idBtnDest21").style.visibility= "visible";
      	}     
        break;
      case 22: 
      	if(document.companySwitchboardCreate.submenu2LevelSelect2.value == "0"){
      		document.getElementById("idBtnDest22").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest22.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu2LevelSelect2.value == "2"){
      		document.getElementById("idBtnDest22").style.visibility= "visible";
      	}     
        break;
      case 23: 
      	if(document.companySwitchboardCreate.submenu2LevelSelect3.value == "0"){
      		document.getElementById("idBtnDest23").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest23.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu2LevelSelect3.value == "2"){
      		document.getElementById("idBtnDest23").style.visibility= "visible";
      	}     
        break;
      case 31: 
      	if(document.companySwitchboardCreate.submenu3LevelSelect1.value == "0"){
      		document.getElementById("idBtnDest31").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest31.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu3LevelSelect1.value == "2"){
      		document.getElementById("idBtnDest31").style.visibility= "visible";
      	}     
        break;
      case 32: 
      	if(document.companySwitchboardCreate.submenu3LevelSelect2.value == "0"){
      		document.getElementById("idBtnDest32").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest32.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu3LevelSelect2.value == "2"){
      		document.getElementById("idBtnDest32").style.visibility= "visible";
      	}     
        break;
      case 33: 
      	if(document.companySwitchboardCreate.submenu3LevelSelect3.value == "0"){
      		document.getElementById("idBtnDest33").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest33.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu3LevelSelect3.value == "2"){
      		document.getElementById("idBtnDest33").style.visibility= "visible";
      	}     
        break;
      case 41: 
      	if(document.companySwitchboardCreate.submenu4LevelSelect1.value == "0"){
      		document.getElementById("idBtnDest41").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest41.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu4LevelSelect1.value == "2"){
      		document.getElementById("idBtnDest41").style.visibility= "visible";
      	}     
        break;
      case 42: 
      	if(document.companySwitchboardCreate.submenu4LevelSelect2.value == "0"){
      		document.getElementById("idBtnDest42").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest42.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu4LevelSelect2.value == "2"){
      		document.getElementById("idBtnDest42").style.visibility= "visible";
      	}     
        break;
      case 43: 
      	if(document.companySwitchboardCreate.submenu4LevelSelect3.value == "0"){
      		document.getElementById("idBtnDest43").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest43.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu4LevelSelect3.value == "2"){
      		document.getElementById("idBtnDest43").style.visibility= "visible";
      	}     
        break;
      case 51: 
      	if(document.companySwitchboardCreate.submenu5LevelSelect1.value == "0"){
      		document.getElementById("idBtnDest51").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest51.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu5LevelSelect1.value == "2"){
      		document.getElementById("idBtnDest51").style.visibility= "visible";
      	}     
        break;
      case 52: 
      	if(document.companySwitchboardCreate.submenu5LevelSelect2.value == "0"){
      		document.getElementById("idBtnDest52").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest52.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu5LevelSelect2.value == "2"){
      		document.getElementById("idBtnDest52").style.visibility= "visible";
      	}     
        break;
      case 53: 
      	if(document.companySwitchboardCreate.submenu5LevelSelect3.value == "0"){
      		document.getElementById("idBtnDest53").style.visibility= "hidden";
      		document.companySwitchboardCreate.dest53.value = "";
      	}
      	if(document.companySwitchboardCreate.submenu5LevelSelect3.value == "2"){
      		document.getElementById("idBtnDest53").style.visibility= "visible";
      	}     
        break;
        
      }
                 
 
    }
    
    function clickAddButton(level) {
      document.companySwitchboardCreate.action="companySwitchboardInitSearchDest.action";
      document.companySwitchboardCreate.destlevel.value=level;
      document.companySwitchboardCreate.submit();
    }
    
    function initSwitchboard() {
    	
    	if(document.companySwitchboardCreate.firstLevelSelect1.value == "0"){
      		document.getElementById("idBtnDest1").style.visibility= "hidden";
      		document.getElementById("idSubmenu1").style.visibility= "hidden";
      		document.getElementById("idBtnDest11").style.visibility= "hidden";
      		document.getElementById("idBtnDest12").style.visibility= "hidden";
      		document.getElementById("idBtnDest13").style.visibility= "hidden";
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect1.value == "1"){
      		document.getElementById("idBtnDest1").style.visibility= "hidden";
      		document.getElementById("idSubmenu1").style.visibility= "visible";
      		if(document.companySwitchboardCreate.submenu1LevelSelect1.value == "2"){
      			document.getElementById("idBtnDest11").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu1LevelSelect2.value == "2"){
      			document.getElementById("idBtnDest12").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu1LevelSelect3.value == "2"){
      			document.getElementById("idBtnDest13").style.visibility= "visible";
      		}
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect1.value == "2"){
      		document.getElementById("idBtnDest1").style.visibility= "visible";
      		document.getElementById("idSubmenu1").style.visibility= "hidden";
      		document.getElementById("idBtnDest11").style.visibility= "hidden";
      		document.getElementById("idBtnDest12").style.visibility= "hidden";
      		document.getElementById("idBtnDest13").style.visibility= "hidden";
      	}
      	
      
      	if(document.companySwitchboardCreate.firstLevelSelect2.value == "0"){
      		document.getElementById("idBtnDest2").style.visibility= "hidden";
      		document.getElementById("idSubmenu2").style.visibility= "hidden";
      		document.getElementById("idBtnDest21").style.visibility= "hidden";
      		document.getElementById("idBtnDest22").style.visibility= "hidden";
      		document.getElementById("idBtnDest23").style.visibility= "hidden";
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect2.value == "1"){
      		document.getElementById("idBtnDest2").style.visibility= "hidden";
      		document.getElementById("idSubmenu2").style.visibility= "visible";
      		if(document.companySwitchboardCreate.submenu2LevelSelect1.value == "2"){
      			document.getElementById("idBtnDest21").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu2LevelSelect2.value == "2"){
      			document.getElementById("idBtnDest22").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu2LevelSelect3.value == "2"){
      			document.getElementById("idBtnDest23").style.visibility= "visible";
      		}
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect2.value == "2"){
      		document.getElementById("idBtnDest2").style.visibility= "visible";
      		document.getElementById("idSubmenu2").style.visibility= "hidden";
      		document.getElementById("idBtnDest21").style.visibility= "hidden";
      		document.getElementById("idBtnDest22").style.visibility= "hidden";
      		document.getElementById("idBtnDest23").style.visibility= "hidden";
      	}
        
      	if(document.companySwitchboardCreate.firstLevelSelect3.value == "0"){
      		document.getElementById("idBtnDest3").style.visibility= "hidden";
      		document.getElementById("idSubmenu3").style.visibility= "hidden";
      		document.getElementById("idBtnDest31").style.visibility= "hidden";
      		document.getElementById("idBtnDest32").style.visibility= "hidden";
      		document.getElementById("idBtnDest33").style.visibility= "hidden";
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect3.value == "1"){
      		document.getElementById("idBtnDest3").style.visibility= "hidden";
      		document.getElementById("idSubmenu3").style.visibility= "visible";
      		if(document.companySwitchboardCreate.submenu3LevelSelect1.value == "2"){
      			document.getElementById("idBtnDest31").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu3LevelSelect2.value == "2"){
      			document.getElementById("idBtnDest32").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu3LevelSelect3.value == "2"){
      			document.getElementById("idBtnDest33").style.visibility= "visible";
      		}
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect3.value == "2"){
      		document.getElementById("idBtnDest3").style.visibility= "visible";
      		document.getElementById("idSubmenu3").style.visibility= "hidden";
      		document.getElementById("idBtnDest31").style.visibility= "hidden";
      		document.getElementById("idBtnDest32").style.visibility= "hidden";
      		document.getElementById("idBtnDest33").style.visibility= "hidden";
      	}
       
      	if(document.companySwitchboardCreate.firstLevelSelect4.value == "0"){
      		document.getElementById("idBtnDest4").style.visibility= "hidden";
      		document.getElementById("idSubmenu4").style.visibility= "hidden";
      		document.getElementById("idBtnDest41").style.visibility= "hidden";
      		document.getElementById("idBtnDest42").style.visibility= "hidden";
      		document.getElementById("idBtnDest43").style.visibility= "hidden";
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect4.value == "1"){
      		document.getElementById("idBtnDest4").style.visibility= "hidden";
      		document.getElementById("idSubmenu4").style.visibility= "visible";
      		if(document.companySwitchboardCreate.submenu4LevelSelect1.value == "2"){
      			document.getElementById("idBtnDest41").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu4LevelSelect2.value == "2"){
      			document.getElementById("idBtnDest42").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu4LevelSelect3.value == "2"){
      			document.getElementById("idBtnDest43").style.visibility= "visible";
      		}
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect4.value == "2"){
      		document.getElementById("idBtnDest4").style.visibility= "visible";
      		document.getElementById("idSubmenu4").style.visibility= "hidden";
      		document.getElementById("idBtnDest41").style.visibility= "hidden";
      		document.getElementById("idBtnDest42").style.visibility= "hidden";
      		document.getElementById("idBtnDest43").style.visibility= "hidden";
      	}
       
      
      	if(document.companySwitchboardCreate.firstLevelSelect5.value == "0"){
      		document.getElementById("idBtnDest5").style.visibility= "hidden";
      		document.getElementById("idSubmenu5").style.visibility= "hidden";
      		document.getElementById("idBtnDest51").style.visibility= "hidden";
      		document.getElementById("idBtnDest52").style.visibility= "hidden";
      		document.getElementById("idBtnDest53").style.visibility= "hidden";
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect5.value == "1"){
      		document.getElementById("idBtnDest5").style.visibility= "hidden";
      		document.getElementById("idSubmenu5").style.visibility= "visible";
      		if(document.companySwitchboardCreate.submenu5LevelSelect1.value == "2"){
      			document.getElementById("idBtnDest51").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu5LevelSelect2.value == "2"){
      			document.getElementById("idBtnDest52").style.visibility= "visible";
      		}
      		if(document.companySwitchboardCreate.submenu5LevelSelect3.value == "2"){
      			document.getElementById("idBtnDest53").style.visibility= "visible";
      		}
      	}
      	if(document.companySwitchboardCreate.firstLevelSelect5.value == "2"){
      		document.getElementById("idBtnDest5").style.visibility= "visible";
      		document.getElementById("idSubmenu5").style.visibility= "hidden";
      		document.getElementById("idBtnDest51").style.visibility= "hidden";
      		document.getElementById("idBtnDest52").style.visibility= "hidden";
      		document.getElementById("idBtnDest53").style.visibility= "hidden";
      	}
      	
    	
    }
    
    window.onload=initSwitchboard ; 
</script>
<s:form action="companySwitchboardCreate.action" method="post" namespace="/">
<table class="tableContainer">
<tr>
	<td>&nbsp;</td>
	<td>
	<s:actionerror />
  	<s:fielderror />
  	<s:actionmessage/>
	</td>
	<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td  valign="top">
<table class="formOutlineOrange">
	<tr>
		<th colspan="5" align="center">Switchboard</th>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.switchboardName"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="switchboardName" key="mics.form.switchboardName" size="30" class="fontContent" /></td>
		<s:hidden name="switchboardKey"/>
		<s:hidden name="companykey"/>
		<s:hidden name="destlevel"/>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.publicNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="switchboardPublicNumber" key="mics.form.publicNumber" size="30" class="fontContent" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.privateNumber"/></td>
		<td class="textField">:</td>
		<td><s:textfield name="switchboardPrivateNumber" key="mics.form.privateNumber" size="30" class="fontContent" /></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="textField"><s:text name="mics.form.announcement"/></td>
		<td class="textField">:</td>
		<td><s:select name="annoId" list="annoList" listKey="annoid" listValue="announcementname" label="Select Anno"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colpsan="5">&nbsp;</td>
	</tr>
	
	<!--  Iterate Switchboard -->
	<tr>
		<td colspan="5">
			<table class="formOutlineOrange" valign="top">
				<tr>
					<th >Main Menu</td>
					<th >Sub Menu</td>
				</tr>
				
				<tr valign="top">
					<td>
						<table valign="top">
							<tr>
								<td class="textField">Menu 1:</td>
								<td><s:select name="firstLevelSelect1" list="switchList" listKey="id" listValue="name" label="Select level" onchange="changeFirstLevelSelection(1)"/></td>
								<td style="visibility: hidden;" id="idBtnDest1">
									<input name="" value="Edit" onclick="clickAddButton(1)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest1" size="15" class="fontContent" readonly="true"/>
									
								</td>
							</tr>
						</table>
					</td>
					<td>
						<table style="visibility: hidden;" id="idSubmenu1" valign="top">
							<tr>
								<td class="textField">Announcement 1:</td>
								<td><s:select name="firstLevelAnno1" list="annoList" listKey="annoid" listValue="announcementname" label="Select Anno"/></td>
								<s:hidden name="switchboardmenukey1"/>
							</tr>
							<tr>
								<td class="textField">Sub Menu 1:</td>
								<td><s:select name="submenu1LevelSelect1" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(11)"/></td>
								<td style="visibility: hidden;" id="idBtnDest11">
									<input name="" value="Edit" onclick="clickAddButton(11)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest11" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey11"/>
							</tr>
							<tr>
								<td class="textField">Sub Menu 2:</td>
								<td><s:select name="submenu1LevelSelect2" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(12)"/></td>
								<td style="visibility: hidden;" id="idBtnDest12">
									<input name="" value="Edit" onclick="clickAddButton(12)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest12" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey12"/>
							</tr>
							
							<tr>
								<td class="textField">Sub Menu 3:</td>
								<td><s:select name="submenu1LevelSelect3" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(13)"/></td>
								<td style="visibility: hidden;" id="idBtnDest13">
									<input name="" value="Edit" onclick="clickAddButton(13)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest13" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey13"/>
							</tr>
						</table>
					</td>
				</tr>
				<tr valign="top">
					<td>
						<table valign="top">
							<tr>
								<td class="textField">Menu 2:</td>
								<td><s:select name="firstLevelSelect2" list="switchList" listKey="id" listValue="name" label="Select level" onchange="changeFirstLevelSelection(2)"/></td>
								<td style="visibility: hidden;" id="idBtnDest2">
									<input name="" value="Edit" onclick="clickAddButton(2)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest2" size="15" class="fontContent" readonly="true"/>
									
								</td>
							</tr>
						</table>
					</td>
					<td>
						<table style="visibility: hidden;" id="idSubmenu2" valign="top">
							<tr>
								<td class="textField">Announcement 2:</td>
								<td><s:select name="firstLevelAnno2" list="annoList" listKey="annoid" listValue="announcementname" label="Select Anno"/></td>
								<s:hidden name="switchboardmenukey2"/>
							</tr>
							<tr>
								<td class="textField">Sub Menu 1:</td>
								<td><s:select name="submenu2LevelSelect1" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(21)"/></td>
								<td style="visibility: hidden;" id="idBtnDest21">
									<input name="" value="Edit" onclick="clickAddButton(21)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest21" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey21"/>
							</tr>
							
							<tr>
								<td class="textField">Sub Menu 2:</td>
								<td><s:select name="submenu2LevelSelect2" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(22)"/></td>
								<td style="visibility: hidden;" id="idBtnDest22">
									<input name="" value="Edit" onclick="clickAddButton(22)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest22" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey22"/>
							</tr>
							
							<tr>
								<td class="textField">Sub Menu 3:</td>
								<td><s:select name="submenu2LevelSelect3" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(23)"/></td>
								<td style="visibility: hidden;" id="idBtnDest23">
									<input name="" value="Edit" onclick="clickAddButton(23)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest23" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey23"/>
							</tr>
							
							
						</table>
					</td>
				</tr>
				<tr valign="top">
					<td>
						<table valign="top">
							<tr>
								<td class="textField">Menu 3:</td>
								<td><s:select name="firstLevelSelect3" list="switchList" listKey="id" listValue="name" label="Select level" onchange="changeFirstLevelSelection(3)"/></td>
								<td style="visibility: hidden;" id="idBtnDest3">
									<input name="" value="Edit" onclick="clickAddButton(3)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest3" size="15" class="fontContent" readonly="true"/>
									
								</td>
							</tr>
						</table>
					</td>
					<td>
						<table style="visibility: hidden;" id="idSubmenu3" valign="top">
							<tr>
								<td class="textField">Announcement 3:</td>
								<td><s:select name="firstLevelAnno3" list="annoList" listKey="annoid" listValue="announcementname" label="Select Anno"/></td>
								<s:hidden name="switchboardmenukey3"/>
							</tr>
							<tr>
								<td class="textField">Sub Menu 1:</td>
								<td><s:select name="submenu3LevelSelect1" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(31)"/></td>
								<td style="visibility: hidden;" id="idBtnDest31">
									<input name="" value="Edit" onclick="clickAddButton(31)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest31" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey31"/>
							</tr>
							
							<tr>
								<td class="textField">Sub Menu 2:</td>
								<td><s:select name="submenu3LevelSelect2" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(32)"/></td>
								<td style="visibility: hidden;" id="idBtnDest32">
									<input name="" value="Edit" onclick="clickAddButton(32)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest32" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey32"/>
							</tr>
							
							<tr>
								<td class="textField">Sub Menu 3:</td>
								<td><s:select name="submenu3LevelSelect3" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(33)"/></td>
								<td style="visibility: hidden;" id="idBtnDest33">
									<input name="" value="Edit" onclick="clickAddButton(33)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest33" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey33"/>
							</tr>
							
							
						</table>
					</td>
				</tr>
				<tr valign="top">
					<td>
						<table valign="top">
							<tr>
								<td class="textField">Menu 4:</td>
								<td><s:select name="firstLevelSelect4" list="switchList" listKey="id" listValue="name" label="Select level" onchange="changeFirstLevelSelection(4)"/></td>
								<td style="visibility: hidden;" id="idBtnDest4">
									<input name="" value="Edit" onclick="clickAddButton(4)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest4" size="15" class="fontContent" readonly="true"/>
									
								</td>
							</tr>
						</table>
					</td>
					<td>
						<table style="visibility: hidden;" id="idSubmenu4" valign="top">
							<tr>
								<td class="textField">Announcement 4:</td>
								<td><s:select name="firstLevelAnno4" list="annoList" listKey="annoid" listValue="announcementname" label="Select Anno"/></td>
								<s:hidden name="switchboardmenukey4"/>
							</tr>
							<tr>
								<td class="textField">Sub Menu 1:</td>
								<td><s:select name="submenu4LevelSelect1" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(41)"/></td>
								<td style="visibility: hidden;" id="idBtnDest41">
									<input name="" value="Edit" onclick="clickAddButton(41)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest41" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey41"/>
							</tr>
							
							<tr>
								<td class="textField">Sub Menu 2:</td>
								<td><s:select name="submenu4LevelSelect2" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(42)"/></td>
								<td style="visibility: hidden;" id="idBtnDest42">
									<input name="" value="Edit" onclick="clickAddButton(42)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest42" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey42"/>
							</tr>
							
							<tr>
								<td class="textField">Sub Menu 3:</td>
								<td><s:select name="submenu4LevelSelect3" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(43)"/></td>
								<td style="visibility: hidden;" id="idBtnDest43">
									<input name="" value="Edit" onclick="clickAddButton(43)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest43" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey43"/>
							</tr>
						</table>
					</td>
				</tr>
				<tr valign="top">
					<td>
						<table valign="top">
							<tr>
								<td class="textField">Menu 5:</td>
								<td><s:select name="firstLevelSelect5" list="switchList" listKey="id" listValue="name" label="Select level" onchange="changeFirstLevelSelection(5)"/></td>
								<td style="visibility: hidden;" id="idBtnDest5">
									<input name="" value="Edit" onclick="clickAddButton(5)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest5" size="15" class="fontContent" readonly="true"/>
									
								</td>
							</tr>
						</table>
					</td>
					<td>
						<table style="visibility: hidden;" id="idSubmenu5" valign="top">
							<tr>
								<td class="textField">Announcement 5:</td>
								<td><s:select name="firstLevelAnno5" list="annoList" listKey="annoid" listValue="announcementname" label="Select Anno"/></td>
								<s:hidden name="switchboardmenukey5"/>
							</tr>
							<tr>
								<td class="textField">Sub Menu 1:</td>
								<td><s:select name="submenu5LevelSelect1" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(51)"/></td>
								<td style="visibility: hidden;" id="idBtnDest51">
									<input name="" value="Edit" onclick="clickAddButton(51)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest51" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey51"/>
							</tr>
							<tr>
								<td class="textField">Sub Menu 2:</td>
								<td><s:select name="submenu5LevelSelect2" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(52)"/></td>
								<td style="visibility: hidden;" id="idBtnDest52">
									<input name="" value="Edit" onclick="clickAddButton(52)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest52" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey52"/>
							</tr>
							<tr>
								<td class="textField">Sub Menu 3:</td>
								<td><s:select name="submenu5LevelSelect3" label="Select Level" list="#{'0':'Inactive', '2':'Destination'}" onchange="changeSecondLevelSelection(53)"/></td>
								<td style="visibility: hidden;" id="idBtnDest53">
									<input name="" value="Edit" onclick="clickAddButton(53)" style="width: 75px;" type="button">
									
										<br/><s:textfield name="dest53" size="15" class="fontContent" readonly="true"/>
									
								</td>
								<s:hidden name="switchboardsubmenukey53"/>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<!--  End Iterator -->	
	<tr>
		<td>&nbsp;</td>
		<td colspan="3" class="textField"><s:submit method="execute" key="mics.form.submit" align="center" /></td>
		<td>&nbsp;</td>
	</tr>
</table>

</td>
<td>&nbsp;</td>
</tr>
</table>
</s:form>


