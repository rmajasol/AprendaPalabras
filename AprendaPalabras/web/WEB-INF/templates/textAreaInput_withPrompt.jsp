<textarea name="${param.name}"

          <c:if test="${!empty param.id}">
              id="${param.id}"
          </c:if>

          <%-- el param.class da: 
javax.el.ELException: Failed to parse the expression [${!empty param.class}]--%>

          <c:choose>
              <c:when test="${param.text eq param.promptMsg || empty param.text}">
                  class ="untypedText"
                  <c:set var="txt" value="${param.promptMsg}"/>
              </c:when>
              <c:otherwise>
                  class ="typedText"
                  <c:set var="txt" value="${param.text}"/>
              </c:otherwise>
          </c:choose>

          onfocus="
              if(this.className == 'untypedText'){
                  // para poner el cursor al principio una vez seleccionado el campo
                  // tomado de http://stackoverflow.com/a/2127271/1260374  
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
              if(this.value == '${param.promptMsg}'){
                  this.value='';
                  this.className = 'typedText';
              }"
          onblur="
              if(this.value==''){
                  this.value='${param.promptMsg}';
              }
              if(this.value == '${param.promptMsg}'){
                  this.className = 'untypedText';this.setAttribute('type', 'text');
              }"
          >${txt}</textarea>