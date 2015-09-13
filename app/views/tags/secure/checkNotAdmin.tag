#{if session.username && controllers.security.Secure.Security.invoke("checkNotAdmin")}
    #{doBody /}
#{/if}