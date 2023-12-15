package test;

import java.sql.Timestamp;
import java.util.Date;

import java.sql.Timestamp;
import java.util.Date;

public class Lecture {
    private int id;
    private int lectureId;
    private String lectureName;
    private String lectureTime;
    private String venue;
    private int capacity;
    private Timestamp startTime;
    private String content;
    private String useYN;
    private String regId;
    private Date regDate;
    private int reservationCount;

    public Lecture() {
    }

    public Lecture(int id, int lectureId, String lectureName, String lectureTime, String venue, int capacity,
                   Timestamp startTime, String content, String useYN, String regId, Date regDate) {
        this.id = id;
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.lectureTime = lectureTime;
        this.venue = venue;
        this.capacity = capacity;
        this.startTime = startTime;
        this.content = content;
        this.useYN = useYN;
        this.regId = regId;
        this.regDate = regDate;
    }

    // Getter 및 Setter 메서드
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUseYN() {
        return useYN;
    }

    public void setUseYN(String useYN) {
        this.useYN = useYN;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

  

    public int getReservationCount() {
        return reservationCount;
    }

	public void setReservationcount(int int1) {
		 this.reservationCount = reservationCount;
	}
}


