<%@page import="com.servlet.ReservationSystem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>강연예약</title>
</head>
<body>

<%
    String lectureId = request.getParameter("lectureId");
    String regid = request.getParameter("regid");
    String capacity = request.getParameter("capacity");
    ReservationSystem system = new ReservationSystem();

    int isReserved = 0;
    if (regid != null && !regid.isEmpty()) {
        isReserved = system.makeReservation(lectureId, regid,capacity);
    }
    
    if (isReserved == 0) {
        out.println("<h1>강연 신청이 완료되었습니다.</h1>");
    }else if(isReserved == 1){
    	 out.println("<h1>이미 신청된 강연입니다.</h1>");
    }else if(isReserved == 2){
    	out.println("<h1>신청가능 정원이 초과되었습니다.</h1>");
    }else{
    	out.println("<h1>관리자에게 문의 하세요 </h1>");
    }
    
	out.println("<a href=index.jsp>메인페이지로 </a>");
%>

</body>
</html>
