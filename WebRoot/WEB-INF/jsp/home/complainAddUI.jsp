<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    pageContext.setAttribute("basePath", request.getContextPath()+"/") ;
%>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>我要投诉</title>
    <script type="text/javascript" charset="utf-8" src="${basePath}js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}js/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${basePath}js/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script>
    	window.UEDITOR_HOME_URL = "${basePath}js/ueditor/";
    	var ue = UE.getEditor('editor');

    	//根据部门查询其用户
    	function doSelectDept(){
			//获取部门
			var dept = $("#toCompDept option:selected").val();
			if(dept != ""){//选择“请选择”
				//根据部门查询列表
				$.ajax({
					url:"${basePath}sys/home_getUserJson2.action",
					data:{"dept":dept},//向action传入参数
					type:"post",
					dataType:"json",//返回数据类型
					success:function(data){
						//将用户列表设置到投诉人下拉框
						if(data != null && data!= ""){
							if("success" == data.msg){
								var toCompName = $("#toCompName");
								toCompName.empty();
								$.each(data.userList, function(index, user){
									toCompName.append("<option value='" +user.name+ "'>" +user.name+ "</option>");
								});
								
							}else{
								alert("获取被投诉人列表失败！");
							}
						}else{
							alert("获取被投诉人列表失败！");
						}
					},
					error:function(){alert("获取被投诉人列表失败！");}
				});

			}else{
				//清空被投诉人列表
				$("#toCompName").empty();
			}
        }
        //提交表单
        function doSubmit(){
			$.ajax({
				url:"${basePath}sys/home_complainAdd.action",
				data:$("#form").serialize(),//序列化表单，将整个表单提交
				type:"post",
				async:false,//同步操作
				success:function(msg){
					if("success" == msg){
						alert("投诉成功!");
						window.opener.parent.location.reload(true);
						window.close();
					}else{alert("投诉失败!");}
				},
				error:function(){alert("投诉失败!");}
			});
        }
    </script>
</head>
<body>
<form id="form" name="form" action="" method="post" enctype="multipart/form-data">
    <div class="vp_d_1">
        <div style="width:1%;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
        <div class="vp_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>工作主页</strong>&nbsp;-&nbsp;我要投诉</div></div>
    <div class="tableH2">我要投诉</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="250px">投诉标题：</td>
            <td><s:textfield name="comp.compTitle"/></td>
        </tr>
        <tr>
            <td class="tdBg">被投诉人部门：</td>
            <td>
            	<!--  list="" 
            			显示值 
            			得到键
            	-->
            	<s:select id="toCompDept" name="comp.toCompDept" list="#{'':'请选择','财务部门':'财务部门','人事部门':'人事部门'}" onchange="doSelectDept()"/>
            </td>
        </tr>
        <tr>
            <td class="tdBg">被投诉人姓名：</td>
            <td>
            	<select id="toCompName" name="comp.toCompName">
            	</select>
            </td>
        </tr>
        <tr>
            <td class="tdBg">投诉内容：</td>
            <td><s:textarea id="editor" name="comp.compContent" cssStyle="width:90%;height:160px;" /></td>
        </tr>
        <tr>
            <td class="tdBg">是否匿名投诉：</td>
            <td><s:radio name="comp.isNm" list="#{'false':'非匿名投诉','true':'匿名投诉' }" value="true"/></td>
        </tr>
       
    </table>
	<s:hidden name="comp.compCompany" value="%{#session.SYS_USER.dept}"></s:hidden>
	<s:hidden name="comp.compName" value="%{#session.SYS_USER.name}"></s:hidden>
	<s:hidden name="comp.compMobile" value="%{#session.SYS_USER.mobile}"></s:hidden>
    <div class="tc mt20">
        <input type="button" class="btnB2" value="保存" onclick="doSubmit()"/>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:window.close()" class="btnB2" value="关闭" />
    </div>
    </div></div>
    <div style="width:1%;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
    </div>
</form>
</body>
</html>