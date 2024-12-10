<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Đăng ký</title>
</head>
<body>
<div class="container">
    <!-- <h1 class="form-heading">login Form</h1> -->
    <div class="login-form">
        <div class="main-div">
            <%--            <c:if test="${param.incorrectAccount != null}">--%>
            <%--                <div class="alert alert-danger">--%>
            <%--                    Username or password incorrect--%>
            <%--                </div>--%>
            <%--            </c:if>--%>
            <%--            <c:if test="${param.accessDenied != null}">--%>
            <%--                <div class="alert alert-danger">--%>
            <%--                    You Not authorize--%>
            <%--                </div>--%>
            <%--            </c:if>--%>
            <%--            <c:if test="${param.sessionTimeout != null}">--%>
            <%--                <div class="alert alert-danger">--%>
            <%--                    Session Timeout--%>
            <%--                </div>--%>
            <%--            </c:if>--%>
            <div class="container-fluid">
                <section class="gradient-custom">
                    <div class="page-wrapper">
                        <div class="row d-flex justify-content-center align-items-center">
                            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div class="card text-white" style="border-radius: 1rem; background-color: #35bf76;">
                                    <div class="card-body p-5">
                                        <div class="mb-md-5 mt-md-4 pb-5 text-center">
                                            <h2 class="fw-bold mb-2 text-uppercase">Register</h2>
                                            <p class="text-white-50 mb-5"></p>
                                            <form id="formRegister" method="post">
                                                <div class="form-outline form-white mb-4">
                                                    <label class="form-label" for="username">Username</label>
                                                    <input
                                                            type="text"
                                                            class="form-control"
                                                            id="username"
                                                            name="username"
                                                            placeholder="Tên đăng nhập"
                                                            required>
                                                </div>

                                                <div class="form-outline form-white mb-4">
                                                    <label class="form-label" for="email">Email</label>
                                                    <input
                                                            type="text"
                                                            class="form-control"
                                                            id="email"
                                                            name="email"
                                                            placeholder="abc@email.com"
                                                            required>
                                                </div>

                                                <div class="form-outline form-white mb-4">
                                                    <label class="form-label" for="fullName">Full Name</label>
                                                    <input
                                                            type="text"
                                                            class="form-control"
                                                            id="fullName"
                                                            name="fullName"
                                                            placeholder="Nguyễn Văn A"
                                                            required>
                                                </div>

                                                <div class="form-outline form-white mb-4">
                                                    <label class="form-label" for="password">Password</label>
                                                    <input
                                                            type="password"
                                                            class="form-control"
                                                            id="password"
                                                            name="password"
                                                            placeholder="Mật khẩu"
                                                            required>
                                                </div>

                                                <button type="submit" class="btn btn-primary">Đăng ký</button>
                                            </form>
                                            <div class="d-flex justify-content-center text-center mt-2 pt-1">
                                                <a href="#!" class="login-extension text-white"><i
                                                        class="fab fa-facebook-f fa-lg"></i></a>
                                                <a href="#!" class="login-extension text-white"><i
                                                        class="fab fa-twitter fa-lg mx-4 px-2"></i></a>
                                                <a href="#!" class="login-extension text-white"><i
                                                        class="fab fa-google fa-lg"></i></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <%--<script src="./assets/dist/js/boostrap-v5/bootstrap.js"></script>--%>
            <%--<script src="./assets/dist/js/fontawsome-v5/all.js"></script>--%>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        $("#formRegister").submit(function (event) {
            event.preventDefault();

            const username = $("#username").val().trim();
            const email = $("#email").val().trim();
            const fullName = $("#fullName").val().trim();
            const password = $("#password").val().trim();

            if (!username || !email || !fullName || !password) {
                alert("Tất cả các trường là bắt buộc!");
                return;
            }

            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                alert("Địa chỉ email không hợp lệ!");
                return;
            }

            const payload = {
                userName: username,
                email: email,
                fullName: fullName,
                password: password,
                role: "USER"
            };

            $.ajax({
                url: "/api/users",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(payload),
                success: function () {
                    alert("Đăng ký thành công!");
                    window.location.href = "/login";
                },
                error: function (xhr) {
                    let errorMessage = "Đăng ký thất bại. Vui lòng thử lại.";
                    if (xhr.responseJSON && xhr.responseJSON.message) {
                        errorMessage = xhr.responseJSON.message;
                    }
                    alert(errorMessage);
                }
            });
        });
    });
</script>

</body>
</html>