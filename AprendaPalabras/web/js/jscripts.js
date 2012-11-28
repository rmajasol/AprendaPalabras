/**
     * Invierte el sentido de la traducción
     */
function invertSelection(){
    //intercambiamos los selectores
    var sf = document.getElementById('selected_from');
    var st = document.getElementById('selected_to');
    var temp = st.cloneNode(true);
               
    var selFrom = sf.options[sf.selectedIndex].value;
    var selTo = temp.options[st.selectedIndex].value;
               
    for (var i=0; i< sf.options.length; i++){
        if(sf.options[i].value == selFrom){
            st.options[i].selected = true;
            break;
        }
    }
    for (var i=0; i< temp.options.length; i++){
        if(temp.options[i].value == selTo){
            sf.options[i].selected = true;
            break;
        }
    }
        
//también intercambiamos los campos para 'nuevo idioma'
//    var tf = document.getElementById('typed_from');
//    var tt = document.getElementById('typed_to'); 
//    var tf_value = document.getElementById('typed_from').value;
//    var tt_value = document.getElementById('typed_to').value;
//        
//    tt.value = tf_value;
//    tf.value = tt_value;
//    (tf.value == '${typedLang_pmsg}') ? 
//    tf.className = 'untypedText' : tf.className = 'typedText';
//    (tt.value == '${typedLang_pmsg}') ? 
//    tt.className = 'untypedText' : tt.className = 'typedText';
//        
}


//
//        var wf = document.getElementsByName('word_from');
//        var wt = document.getElementsByName('word_to');
//        wf.onfocus = function (){
//            alert('hola');
//            wt.value = '${wordTo_pmsg}' + wf.value;
//        }

