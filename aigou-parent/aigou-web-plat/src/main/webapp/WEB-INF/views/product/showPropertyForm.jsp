<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
.oper{cursor:pointer;font-size:16px;display:inline-block;margin-top:8px;padding:0 4px;}
.oper:hover{background:#a00;color:#fff;border-radius:50%;}
#storeShowPropertyForm input{border:solid 1px #ddd;height:25px;padding-left:8px; line-height:25px;}
</style>
<div class="input-div" id="propGroupTemplate" style="display:none;">
	            <input name="propName" style="width:100px"> ： 
	            <input name="value" style="width:400px"> <span class="oper" onclick="$(this).closest('.input-div').remove();">&times;</span>
</div>
<form id="storeShowPropertyForm" method="post" action="/product/storeShowProperty" style="padding-left:20px;">
		<input type="hidden" name="productId" value="${product.id}"/>
		<div class="input-div">
	           <div style="width:120px;float:left;"> <span class="oper" onclick="addPropGroup(this)">+</span> 属性名</div>
	           <div style="width:200px;float:left;"> 属性值 <span class="oper">&nbsp;</span> </div>
	    </div>
		<c:forEach items="${viewPropertiesList}" var="p">
			<div class="input-div">
	             <input name="propName" style="width:100px"  value="${p.propName}"> ： 
	             <input name="value" style="width:400px"  value="${p.value}"> <span class="oper" onclick="$(this).closest('.input-div').remove();">&times;</span>
	        </div>
		</c:forEach>
		<div class="input-div">
			<label class="label-top"></label>
            <a class="easyui-linkbutton button-lg button-default" onclick="MXF.ajaxForm(this)">提交</a>
        </div>
</form>

<script>
function addPropGroup(o){
	var titleDiv = $(o).closest('.input-div');
	var newGroup = $('#propGroupTemplate').clone(true);
	newGroup.removeAttr('id');
	newGroup.show();
	titleDiv.after(newGroup);
}
</script>