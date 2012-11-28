<div class="survey">
    <p>Agradeceríamos que indicara el motivo por el que se quiere dar de baja.</p>
    <form action="config.htm?action=unsuscribe" method="post" id="form" class="config">
        <div>
            <input type="radio" name="option" value="${opt1}"/> ${opt1}
        </div>
        <div>
            <input type="radio" name="option" value="${opt2}"/> ${opt2}
        </div>
        <div>
            <label>Otro motivo:</label>
            <textarea cols="40" rows="5" name="other"></textarea>
        </div>
        <div>
            <a href="config.htm">Volver</a>
            <input type="submit" name="unsuscription_button" value="Confirmar la baja"/>
        </div> 
    </form>
</div>