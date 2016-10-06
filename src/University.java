import com.db4o.*;

public class University {

	static void showAll(ObjectContainer db)
	{
		Student prototype = new Student(null, null, null, null);
		ObjectSet<Student> list = db.queryByExample(prototype);

		String s = String.format("%30s", "ФИО") + " ¦ ";
		s += String.format("%8s", "№ зач.") + " ¦ ";
		s += String.format("%8s", "Группа") + " ¦ ";
		s += String.format("%35s", "Факультет");
		System.out.println(s);
		System.out.println("-------------------------------+----------+----------+------------------------------------");
		
		while (list.hasNext())
		{
			System.out.println(((Student) list.next()).toTableRow());
		}
		
		System.out.println("Кол-во записей: " + list.size());
	}
	
	static void action(ObjectContainer db)
	{
		showAll(db);
		
		System.out.println("\nДобавить запись в таблицу:\n");
		Student student1 = new Student("123456", "Иванов Иван Иванович", "Э-101", "факультет Экономики");
		db.store(student1);
		
		showAll(db);

		
		System.out.println("\nУдалить запись:\n");
		db.delete(student1);
		
		showAll(db);

		System.out.println("\nВыбрать студента с номером зачетки \"362014\":\n");
		ObjectSet<Student> list = db.queryByExample(new Student("362014", null, null, null));
		if (list.hasNext())
		{
			Student t = list.next();
			System.out.println("ФИО: " + t.getFio());
			System.out.println("Номер зачетной книжки: " + t.getNumber());
			System.out.println("Номер группы: " + t.getGroup());
			System.out.println("Факультет: " + t.getDepartment());
		}
	
	}

	public static void main(String[] args) {
		
		ObjectContainer db;
		String dB4oFileName = "Univer";
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dB4oFileName);
		
		try
		{
			action(db);
		}
		finally
		{
			db.close();
		}
		
	}

}
