import com.db4o.*;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

public class University {

	static void showAll(ObjectSet<Student> students) {
		String s = String.format("%30s", "ФИО") + " ¦ ";
		s += String.format("%8s", "№ зач.") + " ¦ ";
		s += String.format("%8s", "Группа") + " ¦ ";
		s += String.format("%35s", "Факультет");
		System.out.println(s);
		System.out.println("-------------------------------+----------+----------+------------------------------------");

		for (int i = 0; i < students.size(); i++) {
			System.out.println(students.get(i).toTableRow());
		}

		System.out.println("Кол-во записей: " + students.size());
	}

	static void showAll(ObjectContainer db) {
		Student prototype = new Student(null, null, null, null);
		ObjectSet<Student> list = db.queryByExample(prototype);
		showAll(list);
	}

	@SuppressWarnings("serial")
	static void action(ObjectContainer db) {
		showAll(db);

		System.out.println("\nДобавить запись в таблицу:\n");
		Student student1 = new Student("123456", "Иванов Иван Иванович", "Э-101", "факультет Экономики");
		db.store(student1);

		showAll(db);

		System.out.println("\nУдалить запись:\n");
		db.delete(student1);

		showAll(db);

		System.out.println("\nВыбрать студента с номером зачетки \"362014\":\n");
		ObjectSet<Student> oneStudent = db.queryByExample(new Student("362014", null, null, null));
		if (oneStudent.hasNext()) {
			Student t = oneStudent.next();
			System.out.println("ФИО: " + t.getFio());
			System.out.println("Номер зачетной книжки: " + t.getNumber());
			System.out.println("Номер группы: " + t.getGroup());
			System.out.println("Факультет: " + t.getDepartment());
		}

		System.out.println("\nNative Queries:\n");
		ObjectSet<Student> students = db.query(new Predicate<Student>() {
			public boolean match(Student student) {
				return student.getGroup().equals("Г-101") || student.getGroup().equals("Г-102");
			}
		});
		
		showAll(students);

		System.out.println("\nSODA Query API:\n");
		Query query = db.query();
		query.constrain(Student.class);
		query.descend("number").constrain("110073").not();
		Constraint constraint = query.descend("fio").constrain("Петров Петр Петрович");
		query.descend("fio").constrain("Пупкин Василий Петрович").or(constraint);
		students = query.execute();
		
		showAll(students);

	}

	public static void main(String[] args) {

		ObjectContainer db;
		String dB4oFileName = "Univer";
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dB4oFileName);

		try {
			action(db);
		} finally {
			db.close();
		}

	}

}

