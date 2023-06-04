<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.sendRedirect("LoginAuxiliarAction.do?operacion=validaIngreso&proceso=logout");
    return;
%>