<div class='cssmenu'>
    <ul>
        <li><a href='?view=addedTrs'
               <c:if test="${param.view eq 'addedTrs'
                             || empty param.view}">
                     class="selected"
               </c:if>
               >Traducciones</a>
        </li>
        <li><a href='?view=createdTrs'
               <c:if test="${param.view eq 'createdTrs'}">
                   class="selected"
               </c:if>
               >Mis aportaciones</a>
        </li>
    </ul>
</div>