<div class='cssmenu'>
    <ul class="floating">
        <li><a href="search.htm"
               <c:if test="${currentUrl.contains('search.htm')}">
                   class="selected"
               </c:if>
               >Buscar</a>
        </li>
        <li><a href="lists.htm"
               <c:if test="${currentUrl.contains('lists.htm')}">
                   class="selected"
               </c:if>
               >Listas</a>
        </li>
        <li><a href="config.htm"
               <c:if test="${currentUrl.contains('config.htm')}">
                   class="selected"
               </c:if>
               >Configuración</a>
        </li>
        <li>
            <a href="logout.htm">
                Cerrar sesión <span class="username">[${sessionUser.username}]</span>
            </a>
        </li>
    </ul>

    <a href="search.htm">
        <img src="${logo_url}" style="margin-bottom: 10px" height="25px"/>
    </a>
    <div class="clear"/>
</div>