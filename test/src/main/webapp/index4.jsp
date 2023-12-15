<%@page import="java.util.List"%>
<%@page import="com.servlet.Lecture"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.servlet.ReservationSystem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
      <% 
      
      	ReservationSystem system = new ReservationSystem();
       String lectureId = request.getParameter("lectureId");
       String regid = request.getParameter("regid");
       
       out.println(lectureId);
       out.println(regid);

       if (regid != null && !regid.isEmpty()) {
            system.cancelReservation(lectureId, regid);
       }
             
             
       out.println("<h1>강연 신청이 취소되었습니다.</h1>");  
       out.println("<a href=index.jsp>메인페이지로 </a>");
      %>
        

</body>
</html>
