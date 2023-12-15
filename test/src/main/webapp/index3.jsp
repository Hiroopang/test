<%@page import="java.util.List"%>
<%@page import="com.servlet.Lecture"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.servlet.ReservationSystem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>강연</title>
</head>
<body>

<h1>강연목록</h1>

<!-- 강연 목록 테이블 -->
<table border="1">
    <thead>
        <tr>
            <th>강연명 </th>
            <th>내용 </th>
            <th>정원 </th>
            <th>시간 </th>
            <th>장소 </th>
            <th>시작시간 </th>
            <th>취소</th>
            <th>내가 신청한 강연</th>
            <th>내가 신청한 강연</th> 
        </tr>
    </thead>
    <tbody>
        <% ReservationSystem system = new ReservationSystem();
        	String regid = request.getParameter("regid");
        
               List<Lecture> MyLectures = system.getMyLectures(regid);
        %>
             <% if (!MyLectures.isEmpty()) { 
            	 
           for (Lecture MyLectureslist : MyLectures) { %>
            <tr>
                <!-- 강연 정보 표시 -->
                <td><%= MyLectureslist.getLectureName() %></td>
                <td><%= MyLectureslist.getContent() %></td>
                <td><%= MyLectureslist.getCapacity() %></td>
                <td><%= MyLectureslist.getLectureTime() %></td>
                <td><%= MyLectureslist.getVenue() %></td>
                <td><%= MyLectureslist.getStartTime() %></td>
                <td>
                    <form action="index4.jsp" method="post">
                        <input type="hidden" name="lectureId" value="<%= MyLectureslist.getLectureId() %>">
                        <input type="hidden" name="regid" value="<%= regid %>">
                        <input type="submit" value="취소">
                    </form>
                </td>
                <td>
                      <ul>
                          <li><%= MyLectureslist.getLectureName() %></li>
                      </ul>
                  
                </td>
                <td>
                        <%= MyLectureslist.getLectureName() %>
                </td>
            </tr>
                <%}  
           			} else { %>
                        No applications yet.
         <% } %>
    </tbody>
</table>
<a href=index.jsp>메인페이지로 </a>
</body>
</html>
