package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class ReservationSystem {
	 // 데이터베이스 연결 정보
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Seoul&useSSL=false";;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1223";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void fetchLecturesFromDB() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = " SELECT id, lecture_id, lecture_nm, lecture_time, venue, capacity, start_time, content, use_yn, reg_id, reg_date \r\n"
            		+ " FROM lectures \r\n"
            		+ " WHERE use_yn = 'Y' \r\n"
            		+ "   AND start_time+ INTERVAL 1 DAY > NOW() AND start_time >= (now() - INTERVAL 7 DAY) " ;
             		//강연신청 목록에는 강연시작시간 1주일 전에 노출되며 강연시작시 간 1일 후 노출목록에서 노출하지 않습니다
            		 
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
            	
            	  while (rs.next()) {
            		  Lecture lecture = new Lecture();
            	        lecture.setId(rs.getInt("id"));
            	        lecture.setLectureId(rs.getInt("lecture_id"));
            	        lecture.setLectureName(rs.getString("lecture_nm"));
            	        lecture.setLectureTime(rs.getString("lecture_time"));
            	        lecture.setVenue(rs.getString("venue"));
            	        lecture.setCapacity(rs.getInt("capacity"));
            	        lecture.setStartTime(rs.getTimestamp("start_time"));
            	        lecture.setContent(rs.getString("content"));
            	        lecture.setUseYN(rs.getString("use_yn"));
            	        lecture.setRegId(rs.getString("reg_id"));
            	        lecture.setRegDate(rs.getDate("reg_date"));
            	        addLecture(String.valueOf(lecture.getLectureId()), lecture);
            	    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    public List<Lecture> getMyLectures(String regid) {
        List<Lecture> employeeLectures = new ArrayList<>();
        
        //System.out.println("regid->"+regid);

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = " SELECT l.id, l.lecture_id, l.lecture_nm, l.lecture_time, l.venue, l.capacity, l.start_time, l.content, l.use_yn, l.reg_id, l.reg_date " +
                         " FROM lectures l " +
                         " INNER JOIN reserv r ON l.lecture_id = r.lecture_id " +
                         " WHERE r.reg_id = ? and r.use_yn = 'Y' ";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regid);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Lecture lecture = new Lecture();
                    lecture.setId(rs.getInt("id"));
                    lecture.setLectureId(rs.getInt("lecture_id"));
                    lecture.setLectureName(rs.getString("lecture_nm"));
                    lecture.setLectureTime(rs.getString("lecture_time"));
                    lecture.setVenue(rs.getString("venue"));
                    lecture.setCapacity(rs.getInt("capacity"));
                    lecture.setStartTime(rs.getTimestamp("start_time"));
                    lecture.setContent(rs.getString("content"));
                    lecture.setUseYN(rs.getString("use_yn"));
                    lecture.setRegId(rs.getString("reg_id"));
                    lecture.setRegDate(rs.getDate("reg_date"));

                    employeeLectures.add(lecture);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeLectures; // 직원의 예약 목록 반환
    }
    
    

    public List<Lecture> getBestLectures() {
        List<Lecture> employeeLectures = new ArrayList<>();
        
        //System.out.println("regid->"+regid);

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = " SELECT a.lecture_id,b.lecture_nm, COUNT(*) AS reservation_count \r\n"
            		+ " FROM reserv a LEFT JOIN lectures b on a.lecture_id = b.lecture_id \r\n"
            		+ " WHERE reg_time >= CURDATE() - INTERVAL 3 DAY \r\n"
            		+ " GROUP BY lecture_id,b.lecture_nm \r\n"
            		+ " ORDER BY reservation_count DESC \r\n"
            		+ " LIMIT 3";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            	
            	ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Lecture lecture = new Lecture();
                    lecture.setLectureId(rs.getInt("lecture_id"));
                    lecture.setLectureName(rs.getString("lecture_nm"));
                    lecture.setReservationcount(rs.getInt("reservation_count"));
                    employeeLectures.add(lecture);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeLectures; 
    }


    
	private Map<String, Lecture> lectures = new HashMap<>(); // 강연 정보를 담는 맵
    private Map<String, List<String>> reservations = new HashMap<>(); // 예약 정보를 담는 맵
    private Map<String, Integer> popularLectures = new HashMap<>(); // 인기 강연 정보를 담는 맵
    
    public Map<String, Lecture> getLectures() {
        return lectures;
    }
    public void addLecture(String id, Lecture lecture) {
        lectures.put(id, lecture); // 강연 추가
    }

    public int makeReservation(String id, String regid, String capacity) {
        if (!reservations.containsKey(id)) {
            reservations.put(id, new ArrayList<>());
        }

        if (reservations.get(id).contains(regid)) {
            return 0; // 중복 예약 방지
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
        	 conn.setAutoCommit(true);
        	// 중복 예약을 체크하는 쿼리
            String checkDuplicateSQL = "SELECT COUNT(*) FROM reserv WHERE lecture_id = ? AND reg_id = ? and use_yn = 'Y' ";
            int count = 0;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkDuplicateSQL)) {
                checkStmt.setString(1, id);
                checkStmt.setString(2, regid);
                ResultSet resultSet = checkStmt.executeQuery();
                resultSet.next();
                count = resultSet.getInt(1);
                if (count > 0) {
                    return 1; // 중복 예약 방지
                }
            }
            

            System.out.println(regid);
            System.out.println(id);
            System.out.println(count);

            //정원초과 
            int capa  =  Integer.parseInt(capacity);
            if(count > capa) {
            	return 2; 
            }

            System.out.println("capa->"+capa);
            
            

           // 새로운 예약 ID 가져오기 오라클이면 sequence 추천... 
            int newReservationId = 0;
            String getIdSQL = "SELECT COALESCE(MAX(id), 0) + 1 AS new_id FROM reserv";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(getIdSQL)) {

                if (rs.next()) {
                    newReservationId = rs.getInt("new_id");
                }
            }

            	
            System.out.println("newReservationId->"+newReservationId);
            
            // 추가 
            String insertSQL = "INSERT INTO reserv (id, lecture_id, reg_id, reg_time, use_yn) VALUES (?, ?, ?, NOW(), 'Y')";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, newReservationId);
                pstmt.setString(2, id);
                pstmt.setString(3, regid);
                pstmt.executeUpdate();
            }

            reservations.get(id).add(regid);
            
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리
            return -1; // 예약에 실패한 경우
        }

        return 0; // 성공적으로 예약한 경우
    }



    public void cancelReservation(String id, String regid) {

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
        	 conn.setAutoCommit(true);
        	String insertSQL = " UPDATE reserv SET use_yn = 'N' WHERE lecture_id=?  AND reg_id = ? ";
        	System.out.println("삭제");
        	System.out.println("id"+id);
        	System.out.println("regid"+regid);
        	
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, id);
                pstmt.setString(2, regid);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }


    public List<Lecture> getEmployeeLectures(String employeeId) {
        List<Lecture> employeeLectures = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : reservations.entrySet()) {
            if (entry.getValue().contains(employeeId)) {
                employeeLectures.add(lectures.get(entry.getKey()));
            }
        }
        return employeeLectures; // 사원의 예약 목록 반환
    }

    public Set<String> getEmployeeIds() {
        return reservations.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()); // 모든 사원의 사번 목록 반환
    }

    public void updatePopularLectures() {
        popularLectures.clear();
        for (List<String> employees : reservations.values()) {
            for (String employee : employees) {
                popularLectures.put(employee, popularLectures.getOrDefault(employee, 0) + 1);
            }
        }

        popularLectures = popularLectures.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        // 인기 강연 업데이트
    }


    public static void main(String[] args) {
    	 ReservationSystem system = new ReservationSystem();
    	system.fetchLecturesFromDB(); // 데이터베이스에서 강연 정보 가져오기



        // 기타 기능 호출 및 사용
        // system.makeReservation("1", "12345");
        // system.cancelReservation("1", "12345");
        // system.getEmployeeLectures("12345");
        // system.getEmployeeIds();
        // system.getBestLectures();
    }
}