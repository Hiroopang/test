<%@page import="java.util.List"%>
<%@page import="com.servlet.Lecture"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.servlet.ReservationSystem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>강연리스트</title>
</head>
<body>

<h1>*** 강연 BEST 3 ***</h1>
<%
ReservationSystem system = new ReservationSystem();
List<Lecture> BestLectures = system.getBestLectures();
if (!BestLectures.isEmpty()) { 
	 out.println("<table border = 2><tr>");
	 int bestint = 0;
	 for (Lecture BestLectureslist : BestLectures) { 
		 bestint ++;
		 out.println("<td> "+ bestint + "위 : " + BestLectureslist.getLectureName() );
		 out.println(" / 신청건수 : " + BestLectureslist.reservationcount() + "  </td>");
	 }
	
	 out.println("</tr></table>");
	
}


%>

<h1>강연리스트</h1>

<%
	system.fetchLecturesFromDB(); // 데이터베이스에서 강연 정보 가져오기
	int i = 0;
    
    // 데이터베이스에서 가져온 강연 목록을 출력
    out.println("<ul>");
    for (Map.Entry<String, Lecture> entry : system.getLectures().entrySet()) {
        out.println("<li>" + entry.getValue().getLectureName() + "</li>");
        out.println("<li>" + entry.getValue().getContent() + "</li>");
        out.println("<li>" + entry.getValue().getCapacity() + "</li>");
        out.println("<li>" + entry.getValue().getLectureTime() + "</li>");
        out.println("<li>" + entry.getValue().getVenue()+ "</li>");
        out.println("<li>" + entry.getValue().getStartTime()+ "</li>");

        // 폼을 통해 강연 신청
        out.println("<form action=\"index2.jsp\" method=\"post\" onsubmit=\"return checkForm(" + i++ + ")\">");
        out.println("<input type=\"hidden\" name=\"lectureId\" value=\"" + entry.getValue().getLectureId() + "\">");
        out.println("<input type=\"hidden\" name=\"capacity\" value=\"" + entry.getValue().getCapacity() + "\">");
        out.println("<input type=\"text\" name=\"regid\" placeholder=\"ID\" maxlength=\"5\">");
        out.println("<input type=\"submit\" value=\"신청\">");
        out.println("</form>");
    }
    out.println("</ul>");
    
    %>
    <h1>강연신청조회</h1>
    <%
    out.println("<ul>");
 	// 강연 신청 목록 조회 폼
    out.println("<form action=\"index3.jsp\" method=\"post\">");
    out.println("<input type=\"text\" name=\"regid\" placeholder=\"ID\">");
    out.println("<input type=\"submit\" value=\"조회\">");
    out.println("</form>");

    out.println("</ul>");
    
%>
<script>
    function checkForm(int i) {
        var regId = document.getElementsByName("regid")[i].value;

        if (regId.trim() === '') {
            alert("ID를 입력하세요."); 
            return false; 
        }
    }
</script>
</body>
</html>
