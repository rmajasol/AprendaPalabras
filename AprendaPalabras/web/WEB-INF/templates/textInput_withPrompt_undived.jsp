<input name="${param.name}"
       <c:choose>
           <c:when test="${param.value eq param.promptMsg || empty param.value}">
               type="text"
               value="${param.promptMsg}"
               class ="untypedText"
           </c:when>
           <c:otherwise>
               class ="typedText"
               value="${param.value}"
               <c:choose>
                   <c:when test="${param.name eq 'password2'}">
                       type="password"
                   </c:when>
                   <c:otherwise>
                       type="text"
                   </c:otherwise>
               </c:choose>
           </c:otherwise>
       </c:choose>

       <c:if test="${!empty param.size}">
           size="${param.size}"
       </c:if>

       <c:if test="${!empty param.id}">
           id="${param.id}"
       </c:if>

       onfocus="
           if(this.className == 'untypedText'){
               // script tomado de http://stackoverflow.com/a/2127271/1260374  
               var inp = this;
               if (inp.createTextRange) {
                   var part = inp.createTextRange();
                   part.moveat('character', 0);
                   part.moveEnd('character', 0);
                   part.select();
               }else if (inp.setSelectionRange){
                   inp.setSelectionRange(0, 0);}
               inp.focus()
           }"
       onkeydown="
           if(this.value=='${param.promptMsg}'){
               this.value='';
               this.className = 'typedText';
           }
           if(this.name == 'password2')this.setAttribute('type', 'password');"
       onblur="
           if(this.value==''){
               this.value='${param.promptMsg}';
           }
           if(this.value == '${param.promptMsg}'){
               this.className = 'untypedText';this.setAttribute('type', 'text');
           }"
       />