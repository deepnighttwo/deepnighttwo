<%@ page language="java"
	import="costrecord.jdo.*,java.util.*,java.text.DateFormat"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Cost Record System - Cost Management</title>
</head>
<body>
<%
    request.setAttribute("checkLogin", true);
%>
<%@ include file="/header.jsp"%>
<h2>Cost Management</h2>

<%
    Long houseID = HouseManager.checkLogin(request, response);
    if ((new Long("-1")).equals(houseID)) {
        return;
    }
    List<Cost> costs = CostRecordManager.getCostWithRole(houseID);
    List<CostRecordRole> roles = RoleManager.getAllRoles(houseID, true);
    HashMap<Long,CostRecordRole> idRoleMap = new HashMap<Long,CostRecordRole>();
    List<String> analysis = CostRecordManager.countCost(costs, roles);
    int totalRole = roles.size();
    if (roles != null) {
    for (CostRecordRole role : roles) {
        if (!role.isActive()) {
            continue;
        }
        Long aroleID = role.getId();
        idRoleMap.put(aroleID, role);
	}
	}
%>
<ul>
	<%
	    if (analysis != null && analysis.size() > 0) {
	%>

	<li>
	<h3>Analysis Result</h3>
	</li>
	<%
	    for (String analy : analysis) {
	%>
	<div style="background-color: #EEEEEE;"><%=analy%></div>
	<%
	    }
	%>
	<a href="/costOperation?operation=archive">Pay and Archive</a>
	<%
	    }
	%>

	<li>
	<h3>Ongoing Cost Record List</h3>
	</li>
	<table width="900">
		<tr>
			<td style="font-weight: bold;">Role Name</td>
			<td style="font-weight: bold;">Money</td>
			<td style="font-weight: bold;">Memo</td>
			<td style="font-weight: bold;">Record Date</td>
			<td style="font-weight: bold;">Weights</td>
			<td style="font-weight: bold;">Operation</td>
		</tr>
		<%
		    if (costs != null) {
		        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.SHORT, Locale.CHINA);
		        for (Cost cost : costs) {
		            if (!cost.isActive()) {
		                continue;
		            }
		            String lid = String.valueOf(cost.getId());
		            String lroleID = String.valueOf(cost.getPayerID());
		            String lroleName = cost.getRole().getUserName();
		            String lmoney = String.valueOf(cost.getMoneyAmount());
		            String lmemo = cost.getMemo();
		            String ldate = format.format(cost.getDate());
		            CostRoleWeight[] weights = CostRecordManager.getWeigtsForCost(cost.getId());
		%>
		<tr>
			<td><%=lroleName%></td>
			<td><%=lmoney%></td>
			<td><%=lmemo%></td>
			<td><%=ldate%></td>
			<td>
			<%
			if(weights == null || weights.length == 0){
			    out.println("Each pays "+(cost.getMoneyAmount()/totalRole));
			}else{
			    double totalWeight = 0;
				for(CostRoleWeight weight : weights){
					totalWeight += weight.getWeight();
				}
				for(CostRoleWeight weight : weights){
				    CostRecordRole role = idRoleMap.get(weight.getRoleID());
				    out.println(role.getUserName()+" should pay " + cost.getMoneyAmount() * (weight.getWeight()/totalWeight)+"<br>");
				}
			}
			%>
			</td>
			<td><a
				href="/costOperation?operation=disableCost&costid=<%=lid%>">Disable
			Cost</a></td>
		</tr>
		<%
		    }
		    }
		%>
	</table>

	<li>
	<h3>Create Cost Record for all users</h3>
	</li>


	<form action="/costOperation" method="post">
	<table>

		<tr>
			<td>Role Name:</td>
			<td><select name="roleID">
				<%
				    if (roles != null) {
				        for (CostRecordRole role : roles) {
				            if (!role.isActive()) {
				                continue;
				            }
				            String aroleID = role.getId().toString();
				            String aroleName = role.getUserName();
				%>
				<option value="<%=aroleID%>"><%=aroleName%> <%
     }
     }
 %>
				
			</select></td>
		</tr>
		<tr>
			<td>Cost:</td>
			<td><input type="text" name="money" /></td>
		</tr>

		<tr>
			<td>Memo:<input type="hidden" name="operation" value="addcostforall" /></td>
			<td><textarea name="memo" rows="3" cols="60"></textarea></td>
		</tr>

		<tr>
			<td><input type="submit" value="Add Cost" /></td>
		</tr>

	</table>
	</form>


	<li>
	<h3>Create Cost Record for selected users</h3>
	</li>


	<form action="/costOperation" method="post">
	<table>

		<tr>
			<td>Role Name:</td>
			<td><select name="roleID">
				<%
				    if (roles != null) {
				        for (CostRecordRole role : roles) {
				            if (!role.isActive()) {
				                continue;
				            }
				            String aroleID = role.getId().toString();
				            String aroleName = role.getUserName();
				%>
				<option value="<%=aroleID%>"><%=aroleName%> <%
     }
     }
 %>
				
			</select></td>
		</tr>
		<tr>
			<td>Cost:</td>
			<td><input type="text" name="money" /></td>
		</tr>

		<tr>
			<td>Role Related:</td>
			<td>
			<table>
				<%
				    if (roles != null) {
				        for (CostRecordRole role : roles) {
				            if (!role.isActive()) {
				                continue;
				            }
				            String aroleID = role.getId().toString();
				            String aroleName = role.getUserName();
				%><tr >
					<td>Role Weights: <input type="hidden" name="userIDs"
						value="<%=aroleID%>" /><%=aroleName%>, &nbsp;</td>
					<td>Weight: <input type="text" name="userWeight" /></td>
				</tr>
				<%
				    }
				    }
				%>
			</table>
			</td>
		</tr>

		<tr>
			<td>Memo:<input type="hidden" name="operation" value="addcostforusers" /></td>
			<td><textarea name="memo" rows="3" cols="60"></textarea></td>
		</tr>

		<tr>
			<td><input type="submit" value="Add Cost" /></td>
		</tr>

	</table>
	</form>
</ul>









<%@ include file="/foot.jsp"%>
</body>
</html>