<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <title>Check token</title>

    <!-- Google font -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <!-- Slick -->
    <link type="text/css" rel="stylesheet" href="css/slick.css"/>
    <link type="text/css" rel="stylesheet" href="css/slick-theme.css"/>


    <!-- Font Awesome Icon -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Custom stlylesheet -->
    <link type="text/css" rel="stylesheet" href="css/style.css"/>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/SignIn.css">

</head>

<body>
<jsp:include page="view/header.jsp"></jsp:include>
<!--BODY-->
<div id="main">
    <!--/BODY-->

    <div class="container ">
        <div class="two_main shadow m-auto mt-5 mb-5 p-4">
            <div class="left">
                <div class="d-flex justify-content-center">
                    <i class="fa fa-user"></i>
                    <p class="ps-2"> Generate key?</p>
                </div>
                <div class="text_mini">
                    <p> Sign in to purchase and explore the rich world of Board games from the BoradStore. Coming to fun
                        game you will experience super hot board games and buy at SUPER CHEAP prices!!!</p>
                </div>

            </div>
            <div class="right">
                <div class="text-foget">
                    <p class="text">Enter your password to get key!!!</p>
                </div>

                <div class="same">
                    <form action="new_key?id=${param.id}" method="post">
                        <p class="nav1"> Your password</p>
                        <c:if test="${userSession==null}">
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" name="email" id="email" placeholder="Email">
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" minlength="6" class="form-control" name="password" id="password"
                                       placeholder="Forget password">
                            </div>
                        </c:if>

                        <c:if test='${mess!=null&&mess!=""}'>
                            <div class="mb-3">
                                <div class="alert alert-danger" role="alert">${mess}</div>
                            </div>
                        </c:if>
                        <button class="btn btn-orange w-100" type="submit">
                            Submit
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
<jsp:include page="view/footer.jsp"></jsp:include>

<!-- jQuery Plugins -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="js/slick.min.js"></script>

<script src="js/jquery.zoom.min.js"></script>
<script src="js/main.js"></script>
</body>

</html>
