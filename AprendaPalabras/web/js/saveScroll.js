///**
// *
// *
// * script tomado de http://www.webdeveloper.com/forum/showthread.php?t=58146
// */
//
//function getCookie(name)
//{
//    var start = document.cookie.indexOf( name + "=" );
//    var len = start + name.length + 1;
//    if ( ( !start ) && ( name != document.cookie.substring( 0, name.length ) ) ) {
//        return null;
//    }
//    if ( start == -1 ) return null;
//    var end = document.cookie.indexOf( ";", len );
//    if ( end == -1 ) end = document.cookie.length;
//    return unescape( document.cookie.substring( len, end ) );
//}
//
//function doload()
//{
//    var scrollTop = getCookie ("scrollTop");
//    var scrollLeft = getCookie("scrollLeft");
//
//    if (!isNaN(scrollTop))
//    {
//        document.body.scrollTop = scrollTop;
//        document.documentElement.scrollTop = scrollTop;
//
//    }
//    if (!isNaN(scrollLeft))
//    {
//        document.body.scrollLeft = scrollLeft;
//        document.documentElement.scrollLeft = scrollLeft;
//    }
//
//    Delete_Cookie("scrollTop");
//    Delete_Cookie("scrollLeft");
//    setTimeout( "Refresh()", 120*1000 );
//}
//
//function Refresh()
//{
//    document.cookie = 'scrollTop=' + f_scrollTop();
//    document.cookie = 'scrollLeft=' + f_scrollLeft();
//    document.location.reload(true);
//}
//
////Setting the cookie for vertical position
//function f_scrollTop() {
//    return f_filterResults (
//        window.pageYOffset ? window.pageYOffset : 0,
//        document.documentElement ? document.documentElement.scrollTop : 0,
//        document.body ? document.body.scrollTop : 0
//        );
//}
//
//function f_filterResults(n_win, n_docel, n_body) {
//    var n_result = n_win ? n_win : 0;
//    if (n_docel && (!n_result || (n_result > n_docel)))
//        n_result = n_docel;
//    return n_body && (!n_result || (n_result > n_body)) ? n_body : n_result;
//}
//
////Setting the cookie for horizontal position
//function f_scrollLeft() {
//    return f_filterResults (
//        window.pageXOffset ? window.pageXOffset : 0,
//        document.documentElement ? document.documentElement.scrollLeft : 0,
//        document.body ? document.body.scrollLeft : 0
//        );
//}
//
//
//function Delete_Cookie(name)
//{
//    document.cookie = name + "=" + ";expires=Thu, 01-Jan-1970 00:00:01 GMT";
//
//}
//
////window.onload=doload;

<!--
cookieName="page_scroll"
expdays=365

// An adaptation of Dorcht's cookie functions.
// http://www.huntingground.freeserve.co.uk/main/mainfram.htm?../scripts/cookies/scrollpos.htm

function setCookie(name, value, expires, path, domain, secure){
    if (!expires){
        expires = new Date()
    }
    document.cookie = name + "=" + escape(value) + 
    ((expires == null) ? "" : "; expires=" + expires.toGMTString()) +
    ((path == null) ? "" : "; path=" + path) +
    ((domain == null) ? "" : "; domain=" + domain) +
    ((secure == null) ? "" : "; secure")
}

function getCookie(name) {
    var arg = name + "="
    var alen = arg.length
    var clen = document.cookie.length
    var i = 0
    while (i < clen) {
        var j = i + alen
        if (document.cookie.substring(i, j) == arg){
            return getCookieVal(j)
        }
        i = document.cookie.indexOf(" ", i) + 1
        if (i == 0) break
    }
    return null
}

function getCookieVal(offset){
    var endstr = document.cookie.indexOf (";", offset)
    if (endstr == -1)
        endstr = document.cookie.length
    return unescape(document.cookie.substring(offset, endstr))
}

function deleteCookie(name,path,domain){
    document.cookie = name + "=" +
    ((path == null) ? "" : "; path=" + path) +
    ((domain == null) ? "" : "; domain=" + domain) +
    "; expires=Thu, 01-Jan-00 00:00:01 GMT"
}

function saveScroll(){ // added function
    var expdate = new Date ()
    expdate.setTime (expdate.getTime() + (expdays*24*60*60*1000)); // expiry date

    //    var x = (document.pageXOffset?document.pageXOffset:document.body.scrollLeft)
    //    var y = (document.pageYOffset?document.pageYOffset:document.body.scrollTop)
    var x = scrollX
    var y = scrollY
    Data=x + "_" + y
    setCookie(cookieName,Data,expdate)
//    alert ("The current scroll position: " +scrollX +"x" + scrollY);
}

function loadScroll(){ // added function
    inf=getCookie(cookieName)
    if(!inf){
        return
    }
    var ar = inf.split("_")
    if(ar.length == 2){
        window.scrollTo(parseInt(ar[0]), parseInt(ar[1]))
    }
//    alert(inf);
    //
    deleteCookie(cookieName, null, null);
}

// add onload="loadScroll()" onunload="saveScroll()" to the opening BODY tag

// -->