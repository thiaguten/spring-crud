<%--
  #%L
  %%
  Copyright (C) 2015 - 2016 Thiago Gutenberg Carvalho da Costa.
  %%
  Redistribution and use in source and binary forms, with or without modification,
  are permitted provided that the following conditions are met:
  
  1. Redistributions of source code must retain the above copyright notice, this
     list of conditions and the following disclaimer.
  
  2. Redistributions in binary form must reproduce the above copyright notice,
     this list of conditions and the following disclaimer in the documentation
     and/or other materials provided with the distribution.
  
  3. Neither the name of the Thiago Gutenberg Carvalho da Costa. nor the names of its contributors
     may be used to endorse or promote products derived from this software without
     specific prior written permission.
  
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
  OF THE POSSIBILITY OF SUCH DAMAGE.
  #L%
  --%>
<%@include file="../../includes/includes.jsp" %>

<!DOCTYPE html>
<template:template>
    <jsp:attribute name="title">
        <spring:message code="label.listar.usuario"/>
    </jsp:attribute>

    <jsp:body>
        <c:url var="pesquisarUsuario" value="/usuario/pesquisar"/>
        <c:url var="adicionarUsuario" value="/usuario/novo"/>

        <table style="width: 100%;">
            <tr>
                <td>
                    <form:form method="POST" action="${pesquisarUsuario}" commandName="usuario">
                        <spring:hasBindErrors name="usuario">
                            <form:errors path="*" element="div" cssClass="errorblock"/>
                        </spring:hasBindErrors>

                        <c:if test="${not empty usuario_mensagem_negocial}">
                            <div class="errorblock">${usuario_mensagem_negocial}</div>
                        </c:if>

                        <table style="width: 100%;">
                            <caption><spring:message code="label.pesquisar.usuario"/></caption>
                            <tr>
                                <td>
                                    <table style="width: 100%;" border="1">
                                        <tr>
                                            <th><form:label path="nome"><spring:message
                                                    code="label.nome"/></form:label></th>
                                            <td><form:input path="nome" maxlength="100"/></td>
                                        </tr>
                                        <tr>
                                            <th><form:label path="idade"><spring:message
                                                    code="label.idade"/></form:label></th>
                                            <td><form:input path="idade" maxlength="3"/></td>
                                        </tr>
                                        <tr>
                                            <th><form:label path="email"><spring:message
                                                    code="label.email"/></form:label></th>
                                            <td><form:input path="email" maxlength="50"/></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="submit" value="<spring:message code="label.pesquisar"/>"/>
                                    <input type="reset" value="<spring:message code="label.resetar"/>"/>
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </td>
            </tr>
            <tr>
                <td>
                    <table style="width: 100%;">
                        <caption><spring:message code="label.listar.usuario"/></caption>
                        <tr>
                            <td>
                                <table style="width: 100%;" border=1>
                                    <thead>
                                    <tr>
                                        <th><spring:message code="label.id"/></th>
                                        <th><spring:message code="label.nome"/></th>
                                        <th><spring:message code="label.idade"/></th>
                                        <th><spring:message code="label.email"/></th>
                                        <th colspan=2><spring:message code="label.acao"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${usuarios}" var="usr">
                                        <tr>
                                            <td><c:out value="${usr.id}"/></td>
                                            <td><c:out value="${usr.nome}"/></td>
                                            <td><c:out value="${usr.idade}"/></td>
                                            <td><c:out value="${usr.email}"/></td>
                                            <td align="center">
                                                <a href="<c:url value="/usuario/alterar/${usr.id}"/>">
                                                    <img width="16" height="16"
                                                         title="<spring:message code="label.alterar" />"
                                                         alt="<spring:message code="label.alterar" />"
                                                         src="<c:url value="/resources/img/edit.gif" />">
                                                </a>
                                            </td>
                                            <td align="center">
                                                <a href="<c:url value="/usuario/remover/${usr.id}"/>">
                                                    <img width="16" height="16"
                                                         title="<spring:message code="label.excluir" />"
                                                         alt="<spring:message code="label.excluir" />"
                                                         src="<c:url value="/resources/img/delete.gif" />">
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top: 10px;">
                                <span class="button">
                                    <a href="${adicionarUsuario}">
                                        <spring:message code="label.adicionar.usuario"/>
                                    </a>
                                </span>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </jsp:body>
</template:template>
