<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<script>

//예시로 주어진 Java 코드의 API 또는 서비스를 호출하는 함수
const fetchLecturesFromDB = async () => {
	ReservationSystem system = new ReservationSystem();
	List<Lecture> Lectures1 = system.getBestLectures();
	List<Lecture> Lectures2 = system.fetchLecturesFromDB();
	system.makeReservation("1", "1",30);
	List<Lecture> Lectures3 = system.getMyLectures("test");
};

//Jest를 사용한 단위 테스트
describe('ReservationSystem', () => {
it('fetches lectures from database', async () => {
 
	// fetchLecturesFromDB()를 호출하여 예상 결과를 받아와 테스트 
 const lectures = await fetchLecturesFromDB();
 
 // 받아온 데이터를 검증하여 예상 결과와 일치하는지 확인
 expect(lectures.length).toBeGreaterThan(0); // 예상 결과가 0보다 큰지 검증하는 예시


});
});

</script>