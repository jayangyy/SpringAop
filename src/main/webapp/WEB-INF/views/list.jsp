<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>资料信息列表页</title>
        <link href="../s/js/easyui/themes/bootstrap/easyui.css" rel="stylesheet" type="text/css"/>
        <link href="../s/js/easyui/themes/icon.css" rel="stylesheet" type="text/css">
        <script src="../s/js/easyui/jquery.min.js" type="text/javascript"></script>
        <script src="../s/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
        <script src="../s/js/json3/json3.js" type="text/javascript"></script>
        <script src="../s/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
        <style>
            #bindPost_dlg p{border-bottom: #575765 dashed 1px;padding-bottom:2px;text-indent:1em;cursor:pointer;}
            #bindPost_dlg p.selected{border-bottom: #575765 dashed 1px;padding-bottom:2px;text-indent:1em;cursor:pointer;color:red;}
        </style>
        <script type="text/javascript">
            $(document).ready(function () {
//                bindBookType();
                bindBigType();
                bindBindedPost();
                bindAllPost();
                bindData();
                $("#tt").datagrid('getPager').pagination({
                    onSelectPage: function () {
                        bindData();
                    }
                });
            });
            function bindData() {
                var pageNumber = $("#tt").datagrid('getPager').data("pagination").options.pageNumber;
                pageNumber = pageNumber === 0 ? 1 : pageNumber;
                var pageSize = $("#tt").datagrid('getPager').data("pagination").options.pageSize;
                var bigTypeCode = $("#cbType1").combobox("getValue");
                var smallTypeCode = $("#cbType2").combobox("getValue");
//                var bookCode = $("#cbType3").combobox("getValue");
//                var bookValue = $("#cbType3").combobox("getText");
                var post = $("#cbPost").combobox("getValue");
                var _filterRules = [];
                if ($("#tbTitle").val())
                    _filterRules.push({"field": "title", "op": "contains", "value": $("#tbTitle").val()});
                if (bigTypeCode !== "" && bigTypeCode !== "-1")
                    _filterRules.push({"field": "type2id", "op": "equals", "value": bigTypeCode});
                if (smallTypeCode !== "" && smallTypeCode !== "-1")
                    _filterRules.push({"field": "type1", "op": "equals", "value": bigTypeCode});
//                if (bookCode !== "" && bookCode !== "-1")
//                    _filterRules.push({"field": "type4", "op": "equals", "value": bookValue});
                if (post !== "" && post !== "--所有--")
                    _filterRules.push({"field": "post", "op": "custom", "value": post});
                var filterRuleStr = "";
                if (_filterRules.length > 0)
                    filterRuleStr = JSON.stringify(_filterRules);
                var _sort = $("#tt").datagrid('options').sortName;
                var _order = $("#tt").datagrid('options').sortOrder;
                if (_sort == null || _sort == "")
                    _order = "desc";
                $.ajax({
                    type: 'get',
                    url: 'allBooks',
                    data: {page: pageNumber, rows: pageSize, filterRules: filterRuleStr, sort: _sort, order: _order},
                    dataType: "json",
                    success: function (r) {
                        if (r.result)
                            $("#tt").datagrid("loadData", r);
                        else
                            $.messager.alert("提示", r.info, "alert");
                    },
                    error: function () {
                        $.messager.alert("提示", "调用后台接口出错！", "alert");
                    }
                });
            }
            function formatDate(value) {
                if (value)
                    return value.split(' ')[0];
                else
                    return "";
            }
            function formatOptions(value, row) {
                return "<a href=# onclick=openView('" + row.id + "')>查阅</a>";
            }
            function openView(bookid) {
//                $("#view-window").load("view",{"bookid":bookid});
                var url = "view?bookid=" + bookid;
                var con = '<iframe src="' + url + '" frameborder="no" width="100%" height="100%" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>';
                $('#view-window').html(con);
                $('#view-window').window('open');
            }
            function bindBookType() {
                $.ajax({
                    type: 'get',
                    url: 'bookType',
                    data: {},
                    dataType: "json",
                    success: function (r) {
                        if (r.result) {
                            r.rows.unshift({"code": "-1", "type": "--所有--"});
                            $("#cbType3").combobox("loadData", r.rows);
                            $("#cbType3").combobox("select", r.rows[0].code);
                        } else
                            $.messager.alert("提示", r.info, "alert");
                    },
                    error: function () {
                        $.messager.alert("提示", "调用后台接口出错！", "alert");
                    }
                });
            }
            function bindBigType() {
                $.ajax({
                    type: 'get',
                    url: 'bigType',
                    data: {},
                    dataType: "json",
                    success: function (r) {
                        if (r.result) {
                            r.rows.unshift({"code": "-1", "type": "--所有--"});
                            $("#cbType1").combobox("loadData", r.rows);
                            $("#cbType1").combobox("select", r.rows[0].code);
                        } else
                            $.messager.alert("提示", r.info, "alert");
                    },
                    error: function () {
                        $.messager.alert("提示", "调用后台接口出错！", "alert");
                    }
                });
            }
            function bindSmallType() {
                $("#cbType2").combobox("loadData", []);
                var bigTypeCode = $("#cbType1").combobox("getValue");
                $.ajax({
                    type: 'get',
                    url: 'smallType',
                    data: {code: bigTypeCode},
                    dataType: "json",
                    success: function (r) {
                        if (r.result) {
                            r.rows.unshift({"code": "-1", "type": "--所有--"});
                            $("#cbType2").combobox("loadData", r.rows);
                            $("#cbType2").combobox("select", r.rows[0].code);
                        } else
                            $.messager.alert("提示", r.info, "alert");
                    },
                    error: function () {
                        $.messager.alert("提示", "调用后台接口出错！", "alert");
                    }
                });
            }
            function bindBindedPost() {
                $("#cbPost").combobox("loadData", []);
                $.ajax({
                    type: 'get',
                    url: 'bindedPost',
                    data: {},
                    dataType: "json",
                    success: function (r) {
                        if (r.result) {
                            r.rows.unshift({"post": "--未绑定资料--"});
                            r.rows.unshift({"post": "--所有--"});
                            $("#cbPost").combobox("loadData", r.rows);
                            $("#cbPost").combobox("select", r.rows[0].post);
                        } else
                            $.messager.alert("提示", r.info, "alert");
                    },
                    error: function () {
                        $.messager.alert("提示", "调用后台接口出错！", "alert");
                    }
                });
            }
            function bindAllPost() {
                $.ajax({
                    type: 'get',
                    url: 'allPost',
                    data: {},
                    dataType: "json",
                    success: function (r) {
                        var items = r.rows;
                        for (var i = 0; i < items.length; i++) {
                            var professional = items[i].professional;
                            var childrens = items[i].children;
                            var contentHtml = "";
                            for (var j = 0; j < childrens.length; j++) {
                                contentHtml += "<p ck='0' onclick='choosePost(this)' value='" + childrens[j].post + "'>" + childrens[j].post + "</p>";
                            }
                            $('#aa').accordion('add', {
                                title: professional,
                                content: contentHtml
                            });
                        }
                        $('#aa').accordion('select', 0);
                    },
                    error: function () {
                        $.messager.alert("提示", "调用后台接口出错！", "alert");
                    }
                });
//                $("#postDiv").html("");
//                $.ajax({
//                    type: 'get',
//                    url: 'allPost',
//                    data: {},
//                    dataType: "json",
//                    success: function (r) {
//                        if (r.result) {
//                            var items = r.rows;
//                            for (var i = 0; i < items.length; i++) {
//                                $("#postDiv").html($("#postDiv").html() + "<p ck='0' onclick='choosePost(this)' value='" + items[i].post + "'>" + items[i].post + "</p>");
//                            }
//                        } else
//                            $.messager.alert("提示", r.info, "alert");
//                    },
//                    error: function () {
//                        $.messager.alert("提示", "调用后台接口出错！", "alert");
//                    }
//                });
            }
            function choosePost(obj) {
                if ($(obj).attr("ck") === "0")
                    $(obj).attr("ck", "1").addClass("selected");
                else
                    $(obj).attr("ck", "0").removeClass("selected");
            }
            function openBindPostDialog() {
                $("#selectBookDiv").html("");
                var selectRows = $("#tt").datagrid("getChecked");
                if (selectRows.length > 0) {
                    var bindedPost = [];
                    var _contains = true;
                    for (var i = 0; i < selectRows.length; i++) {
                        var a = /^([\d])/.test(selectRows[i].type2);
                        if (!a) {
                            $.messager.alert("提示", "所选资料中有不能关联工种的资料[" + selectRows[i].title + "]！", "alert");
                            return;
                        }
                        $("#selectBookDiv").html($("#selectBookDiv").html() + "<p>" + selectRows[i].title + "</p>");
                        $.ajax({
                            type: 'get',
                            url: 'bookPost',
                            data: {bookid: selectRows[i].id},
                            dataType: "json",
                            async: false,
                            success: function (r) {
                                if (r.result) {
                                    var items = r.rows;
                                    var temp = [];
                                    for (var j = 0; j < items.length; j++) {
                                        temp.push(items[j][0]);
                                    }
                                    bindedPost.push(temp);
                                } else
                                    $.messager.alert("提示", r.info, "alert");
                            },
                            error: function () {
                                $.messager.alert("提示", "调用后台接口出错！", "alert");
                            }
                        });
                    }
                    for (var i = 0; i < bindedPost.length - 1; i++) {
                        _contains = bindedPost[i].toString() === bindedPost[i + 1].toString();
                        if (!_contains)
                            break;
                    }
                    $("#postDiv p").attr("ck", "0").removeClass("selected");
                    for (var i = 0; i < bindedPost.length; i++) {
                        for (var j = 0; j < bindedPost[i].length; j++) {
                            var post = bindedPost[i][j];
                            $("#postDiv p[value='" + post + "']").attr("ck", "1").addClass("selected");
                        }
                    }
                    if (_contains)
                        $("#bindPost_dlg").dialog("open");
                    else {
                        $.messager.confirm("提示", "所选资料分别绑定有不同的工种，是否要重新绑定？", function (r) {
                            if (r)
                                $("#bindPost_dlg").dialog("open");
                        });
                    }
                } else {
                    $.messager.alert("提示", "请至少选择一条数据！", "alert");
                }
            }
            Array.prototype.contains = function (item) {
                for (i = 0; i < this.length; i++) {
                    if (this[i] === item) {
                        return true;
                    }
                }
                return false;
            };
            function saveBookeBindPost() {
                var choosePost = $("#postDiv p[ck='1']");
                if (choosePost.length > 0) {
                    var selectBooks = $("#tt").datagrid("getChecked");
                    var _bookids = "";
                    var _posts = "";
                    for (var i = 0; i < selectBooks.length; i++) {
                        var bookid = selectBooks[i].id;
                        _bookids += bookid + ',';
                    }
                    for (var i = 0; i < choosePost.length; i++) {
                        var post = $(choosePost[i]).text();
                        _posts += post + ',';
                    }
                    _bookids = _bookids.substring(0, _bookids.length - 1);
                    _posts = _posts.substring(0, _posts.length - 1);
                    $.ajax({
                        type: 'post',
                        url: 'bookBindPost',
                        data: {bookids: _bookids, posts: _posts},
                        dataType: "json",
                        success: function (r) {
                            if (r.result) {
                                $.messager.alert("提示", "操作成功！", "alert");
                                bindBindedPost();
                                bindData();
                                var row = $("#tt").datagrid("getSelections")[0];
                                var rowIndex = $("#tt").datagrid("getRowIndex", row);
                                datagridRowCheck(rowIndex, row);
                                $('#bindPost_dlg').dialog('close');
                            } else
                                $.messager.alert("提示", r.info, "alert");
                        },
                        error: function () {
                            $.messager.alert("提示", "调用后台接口出错！", "alert");
                        }
                    });
                } else {
                    $.messager.alert("提示", "请至少选择一个工种！", "alert");
                }
            }
            function datagridRowClick(index, row) {
                $("#tt").datagrid("unselectAll");
                $("#tt").datagrid("selectRow", index);
//                var checkLength = $("#tt").datagrid("getChecked").length;
//                if(checkLength==0)
//                $("#tt").datagrid("uncheckAll");
//                $("#tt").datagrid("checkRow",index);
                showHidePost(row);
            }
            function datagridRowCheck(index, row) {
                showHidePost(row);
            }
            function showHidePost(row) {
                var a = /^([\d])/.test(row.type2);
                if (a)
                    $("#btnBindPost").show();
                else
                    $("#btnBindPost").hide();
                $('#cc').layout('remove', 'east');
                var options = {
                    region: 'east',
                    width: '200px',
                    title: '已关联工种',
                    content: getContent(row),
                    collapsed: false,
                    split: true
                };
                $('#cc').layout('add', options);
            }
            function getContent(row) {
                var contentHtml = "";
                var postHtml = "";
                $.ajax({
                    type: 'get',
                    url: 'bookPost',
                    data: {bookid: row.id},
                    dataType: "json",
                    async: false,
                    success: function (r) {
                        if (r.result) {
                            var items = r.rows;
                            for (var j = 0; j < items.length; j++) {
                                postHtml += "<p>" + items[j][0] + "</p>";
                            }
                        } else
                            $.messager.alert("提示", r.info, "alert");
                    },
                    error: function () {
                        $.messager.alert("提示", "调用后台接口出错！", "alert");
                    }
                });
                postHtml = postHtml === "" ? "<p style='color:#D4D4D4'>未关联工种</p>" : postHtml;
                contentHtml += '<div style="padding:60px 0 0 10px;font-size:20px;font-weight:bold;">' + row.title + '</div>';
                contentHtml += '<div style="padding:20px 0 0 10px;font-size:14px;">已关联工种：</div>';
                contentHtml += '<div style="padding:10px 0 0 10px;font-size:14px;">' + postHtml + '</div>';
                return contentHtml;
            }
            function addBook() {
                var con = '<iframe src="edit" frameborder="no" width="100%" height="100%" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>';
                $("#view-window").html(con);
                $("#view-window").window({title: '新增资料', width: '900px'})
                $("#view-window").window("open");
            }
            function editBook() {
                var rows = $("#tt").datagrid("getSelections");
                if (rows.length > 0) {
                    var con = '<iframe src="edit?bookid=' + rows[rows.length - 1].id + '" frameborder="no" width="100%" height="100%" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>';
                    $("#view-window").html(con);
                    $("#view-window").window({title: '编辑资料[ ' + rows[rows.length - 1].title + ' ]', width: '900px'})
                    $("#view-window").window("open");
                } else {
                    $.messager.alert("提示", "请选择要编辑的资料！", "alert");
                }
            }
            function delBook() {
                var rows = $("#tt").datagrid("getSelections");
                if (rows.length > 0) {
                    var row = rows[rows.length - 1];
                    $.messager.confirm("提示", "确定要删除资料[" + row.title + "]？", function (r) {
                        if (r)
                        {
                            $.ajax({
                                type: 'delete',
                                url: 'book/' + row.id,
                                data: {},
                                dataType: "json",
                                ajaxSend: function (e, xhr, options) {
                                    var token = $("meta[name='_csrf']").attr("content");
                                    var header = $("meta[name='_csrf_header']").attr("content");
                                    xhr.setRequestHeader(header, token);
                                },
                                success: function (r) {
                                    $.messager.alert("提示", r.info, "alert");
                                    if (r.result) {
                                        bindBindedPost();
                                        bindData();
                                    }
                                },
                                error: function () {
                                    $.messager.alert("提示", "调用后台接口出错！", "alert");
                                }
                            });
                        }
                    });
                } else {
                    $.messager.alert("提示", "请选择要删除的资料！", "alert");
                }
            }
            function searchClick() {
                $("#tt").datagrid("unselectAll");
                bindData();
            }
            function viewWindowClosing() {
                $('#view-window').panel('clear');
            }
        </script>
    </head>
    <body>
        <div id='cc' class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'">
                <table id="tt" title="资料信息列表" class="easyui-datagrid" data-options="
                       rownumbers:true,
                       fit:true,
                       singleSelect:false,
                       pagination:true,
                       pageSize:20,
                       idField:'id',
                       onSortColumn:bindData,
                       onClickRow:datagridRowClick,
                       onCheck:datagridRowCheck,
                       toolbar:'#menuTollbar'">
                    <thead>
                        <tr>
                            <th data-options="field:'ck',checkbox:true"></th>
                            <th data-options="field:'type2',width:100,align:'center',halign:'center',sortable:'true'">大类</th>
                            <th data-options="field:'type3',width:100,align:'center',halign:'center',sortable:'true'">小类</th>
                            <th data-options="field:'title',width:300,align:'left',halign:'center',sortable:'true'">标题</th>
                            <th data-options="field:'author',width:100,align:'center',halign:'center',sortable:'true'">作者</th>
                            <th data-options="field:'press',width:150,align:'center',halign:'center',sortable:'true'">出版社/单位</th>
                            <th data-options="field:'tdate',width:80,align:'center',halign:'center',formatter:formatDate,sortable:'true'">日期</th>
                            <th data-options="field:'type4',width:100,align:'center',halign:'center',sortable:'true'">类别</th>
                            <th data-options="field:'postcount',width:100,align:'center',halign:'center'">已关联工种数</th>
                            <th data-options="field:'opt',width:80,align:'center',halign:'center',formatter:formatOptions">在线查阅</th>
                        </tr>
                    </thead>
                </table>
                <div id="menuTollbar" style="height: auto;">
                    <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" onclick="bindData();">刷新</a>
                    <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addBook();">添加</a>
                    <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="editBook();">编辑</a>
                    <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="delBook();">删除</a>
                    <div style="padding:2px;border-top:1px solid #D4D4D4;">
                        <span style="padding-left:10px;">大类：</span>
                        <input class="easyui-combobox" data-options="valueField:'code',textField:'type',onChange:bindSmallType" style="width:130px;" id="cbType1">
                        <span style="padding-left:10px;">小类：</span>
                        <input class="easyui-combobox" data-options="valueField:'code',textField:'type'" style="width:130px;" id="cbType2">
                        <!--                        <span style="padding-left:10px;">类别：</span>
                                                <input class="easyui-combobox" data-options="valueField:'code',textField:'type'" style="width:130px;" id="cbType3">-->
                        <span style="padding-left:10px;">资料工种：</span>
                        <input class="easyui-combobox" data-options="valueField:'post',textField:'post'" style="width:130px;" id="cbPost">
                        <span>标题：</span>
                        <input class="easyui-textbox" style="width:140px" id="tbTitle">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchClick()">查询</a>
                        <a id="btnBindPost" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-more'" onclick="openBindPostDialog()">关联工种</a>
                    </div>
                </div>
            </div>
        </div>

        <div id="bindPost_dlg" class="easyui-dialog" title="关联工种" data-options="iconCls:'icon-save',closed:true,buttons:'#bindPost_dlg-buttons'" style="width:550px;height:600px;padding:10px">
            <div class="easyui-layout" data-options="fit:true">
                <div id="selectBookDiv" data-options="region:'west'" style="width:270px;padding:0 10px;" title="所选择的资料"></div>
                <div id="postDiv" data-options="region:'center'" style="padding:0 10px;" title="工种选择">
                    <div id="aa" class="easyui-accordion" data-options="fit:true"></div>
                </div>
            </div>
        </div>

        <div id="bindPost_dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="saveBookeBindPost()">确定</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="$('#bindPost_dlg').dialog('close');">关闭</a>
        </div>

        <div id="view-window" class="easyui-window" title="在线阅读/下载" 
             data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,onBeforeClose:viewWindowClosing" 
             style="width:660px;height:700px;overflow:hidden;">
        </div>
    </body>
</html>
