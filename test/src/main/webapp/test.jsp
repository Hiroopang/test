<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<script>

//���÷� �־��� Java �ڵ��� API �Ǵ� ���񽺸� ȣ���ϴ� �Լ�
const fetchLecturesFromDB = async () => {
	ReservationSystem system = new ReservationSystem();
	List<Lecture> Lectures1 = system.getBestLectures();
	List<Lecture> Lectures2 = system.fetchLecturesFromDB();
	system.makeReservation("1", "1",30);
	List<Lecture> Lectures3 = system.getMyLectures("test");
};

//Jest�� ����� ���� �׽�Ʈ
describe('ReservationSystem', () => {
it('fetches lectures from database', async () => {
 
	// fetchLecturesFromDB()�� ȣ���Ͽ� ���� ����� �޾ƿ� �׽�Ʈ 
 const lectures = await fetchLecturesFromDB();
 
 // �޾ƿ� �����͸� �����Ͽ� ���� ����� ��ġ�ϴ��� Ȯ��
 expect(lectures.length).toBeGreaterThan(0); // ���� ����� 0���� ū�� �����ϴ� ����


});
});

</script>