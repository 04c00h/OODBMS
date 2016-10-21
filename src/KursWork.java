
class KursWork {
	private Student student;
	private String name;

	public KursWork(String name) {
		this.name = name;
		this.student = null;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		String student = (this.student.getFio() != null ? this.student.getFio() : " - ");
		return this.name + " [" + student + "]";
	}
}
