<%-- 
    Document   : index1
    Created on : 2016-7-24, 18:25:06
    Author     : jayan
--%>

<%@page contentType="text/html" pageEncoding="GBK"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>JSP Page</title>
        <link href="../resources/js/uploadify/uploadify.css" rel="stylesheet" type="text/css"/>
        <script src="../resources/js/jquery/jquery.js" type="text/javascript"></script>
        <script src="../resources/js/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
        <script >
            $(document).ready(function () {
                $("#file_upload").uploadify({
                    'buttonText': '��ѡ��',
                    'height': 30,
                    'swf': '../resources/js/uploadify/uploadify.swf',
                    'uploader': '${pageContext.request.contextPath}/Home/uploadFile?${_csrf.parameterName}=${_csrf.token}',
                     //'uploader': '${pageContext.request.contextPath}/Home/uploadFile',
                    'width': 120,
                    'auto': false,
                    'fileObjName': 'file',
                    'onUploadSuccess': function (file, data, response) {
                        alert(file.name + ' �ϴ��ɹ��� ');
                    }
                });
            });
        </script>  
    </head>
    <body>
        <h1>Hello World!</h1>
        <input type="file" name="fileName" id="file_upload" />  
        <a href="javascript:$('#file_upload').uploadify('upload', '*')">�ϴ��ļ�</a> |
        <a href="javascript:$('#file_upload').uploadify('stop')">ֹͣ�ϴ�!</a>  
    </body>
</html>
