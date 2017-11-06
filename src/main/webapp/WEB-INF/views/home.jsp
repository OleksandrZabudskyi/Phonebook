<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">


    <title>Welcome</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/filter.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<div class="container" >

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} |
            <button class="btn btn-lg btn-primary" onclick="document.forms['logoutForm'].submit()">Logout</button>
        </h2>

        <h5><span class="badge">Select row on table to edit or remove</span></h5>
        <!-- Trigger the modal with a buttons -->

        <button type="button" class="btn btn-primary " id="myBtnCreate">New</button>
        <button type="button" class="btn btn-primary" id="myBtnEdit">Edit</button>

        <button type="submit" class="btn btn-primary" id="myBtnDelete" onclick="document.forms['deleteForm'].submit()">
            Delete
        </button>


        <form:form role="form" id="deleteForm" action="${contextPath}/deleteContact" method="POST" modelAttribute="contactForm">
            <spring:bind path="id">
                <div class="form-group required ${status.error ? 'has-error' : ''}">
                    <form:input type="hidden" path="id" class="form-control"></form:input>
                    <form:errors path="id" cssclass="error"></form:errors>
                </div>
            </spring:bind>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>



        <!-- Modal for create -->
        <div class="modal fade" id="myModalCreate" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content" id="addContact">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Add new contact</h4>
                    </div>
                    <div class="modal-body">

                        <form:form role="form" action="${contextPath}/saveContact" method="POST" modelAttribute="contactForm">
                            <spring:bind path="firstName">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="firstName"
                                           class="control-label">First Name</label>
                                    <form:input type="text" path="firstName" class="form-control"
                                                placeholder="First Name"></form:input>
                                    <form:errors path="firstName" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="lastName">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="lastName" class="control-label">Last Name </label>
                                    <form:input type="text" path="lastName" class="form-control"
                                                placeholder="Last Name"></form:input>
                                    <form:errors path="lastName" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="additionalName">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="additionalName" class="control-label">Additional Name </label>
                                    <form:input type="text" path="additionalName" class="form-control"
                                                placeholder="Additional Name"></form:input>
                                    <form:errors path="additionalName" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="mobilePhone">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="mobilePhone" class="control-label">Mobile Phone </label>
                                    <form:input path="mobilePhone" type="text" class="form-control"
                                                placeholder="+38(0xx)xxxxxxx"></form:input>
                                    <form:errors path="mobilePhone" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="email">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <label for="email" class="control-label">Email</label>
                                    <form:input type="text" path="email" class="form-control"
                                                placeholder="Email"></form:input>
                                    <form:errors path="email" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="homePhone">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="homePhone">Home Phone</label>
                                    <form:input type="text" path="homePhone" class="form-control"
                                                placeholder="Home Phone"></form:input>
                                    <form:errors path="homePhone" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="address">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="address">Address</label>
                                    <form:input type="text" path="address" class="form-control"
                                                placeholder="Address"></form:input>
                                    <form:errors path="address" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <button type="submit" class="btn btn-success btn-block">Save</button>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>

        <!-- Modal for edit-->
        <div class="modal fade" id="myModalEdit" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content" id="editContact">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Edit contact</h4>
                    </div>
                    <div class="modal-body">

                        <form:form role="form" action="${contextPath}/saveContact" method="POST" modelAttribute="contactForm">
                            <spring:bind path="id">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <form:input type="hidden" path="id" class="form-control" id="id"></form:input>
                                    <form:errors path="id" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="firstName">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="firstName"
                                           class="control-label">First Name </label>
                                    <form:input type="text" path="firstName" class="form-control" id="firstName"
                                                placeholder="First Name"></form:input>
                                    <form:errors path="firstName" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="lastName">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="lastName" class="control-label">Last Name </label>
                                    <form:input type="text" path="lastName" class="form-control" id="lastName"
                                                placeholder="Last Name"></form:input>
                                    <form:errors path="lastName" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="additionalName">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="additionalName" class="control-label">Additional Name </label>
                                    <form:input type="text" path="additionalName" class="form-control"
                                                id="additionalName" placeholder="Additional Name"></form:input>
                                    <form:errors path="additionalName" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="mobilePhone">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="mobilePhone" class="control-label">Mobile Phone </label>
                                    <form:input path="mobilePhone" type="text" class="form-control" id="mobilePhone"
                                                placeholder="+38(0xx)xxxxxxx"></form:input>
                                    <form:errors path="mobilePhone" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="email">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <label for="email" class="control-label">Email</label>
                                    <form:input type="text" path="email" class="form-control" id="email"
                                                placeholder="Email"></form:input>
                                    <form:errors path="email" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="homePhone">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="homePhone">Home Phone</label>
                                    <form:input type="text" path="homePhone" class="form-control" id="homePhone"
                                                placeholder="Home Phone"></form:input>
                                    <form:errors path="homePhone" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="address">
                                <div class="form-group required ${status.error ? 'has-error' : ''}">
                                    <label for="address">Address</label>
                                    <form:input type="text" path="address" class="form-control"
                                                placeholder="Address"></form:input>
                                    <form:errors path="address" cssclass="error"></form:errors>
                                </div>
                            </spring:bind>
                            <button type="submit" class="btn btn-success btn-block">Save</button>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>

        <div class="row">
            <div class="panel panel-primary filterable">
                <div class="panel-heading">
                    <h3 class="panel-title">Contacts
                    <div class="form-group panel-title${errorMessage != null ? 'has-error' : ''}">
                        <span class="label label-warning">${errorMessage}</span>
                    </div>
                    <div class="form-group panel-title${message != null ? 'has-success' : ''}">
                        <span class="label label-success">${message}</span>
                    </div>
                    </h3>
                    <div class="pull-right">
                        <button class="btn btn-default btn-xs btn-filter"><span
                                class="glyphicon glyphicon-filter"></span> Filter
                        </button>
                    </div>
                </div>
                <table class="table table-bordered" id="myTable">
                    <thead>
                    <tr class="filters">
                        <th><input type="text" class="form-control" placeholder="First Name" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Last Name" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Additional Name" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Mobile Phone" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Home Phone" disabled></th>
                        <th><input type="text" class="form-control" placeholder="Address" disabled></th>
                        <th><input type="text" class="form-control" placeholder="email" disabled></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="contact" items="${contacts}">
                        <tr class="clickable-row" data-id="${contact.id}"
                            data-fn="${contact.firstName}"
                            data-ln="${contact.lastName}"
                            data-an="${contact.additionalName}"
                            data-mp="${contact.mobilePhone}"
                            data-hp="${contact.homePhone}"
                            data-ad="${contact.address}"
                            data-em="${contact.email}">
                            <td>${contact.firstName}</td>
                            <td>${contact.lastName}</td>
                            <td>${contact.additionalName}</td>
                            <td>${contact.mobilePhone}</td>
                            <td>${contact.homePhone}</td>
                            <td>${contact.address}</td>
                            <td>${contact.email}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/js/filter.js"></script>
<script src="${contextPath}/resources/js/editor.js"></script>

</body>
</html>
