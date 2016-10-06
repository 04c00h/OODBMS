class Student {
	private String number;
	private String fio;
	private String group;
	private String department;
	
	public Student(String number, String fio, String group, String department)
	{
		this.number = number;
		this.fio = fio;
		this.group = group;
		this.department = department;
	}

	public String getNumber() { return this.number; }
	public String getFio() { return this.fio; }
	public String getGroup() { return this.group; }
	public String getDepartment() { return this.department; }
	
	public String toTableRow()
	{
		String s = String.format("%30s", this.fio) + " ¦ ";
		s += String.format("%8s", this.number) + " ¦ ";
		s += String.format("%8s", this.group) + " ¦ ";
		s += String.format("%35s", this.department);
		return s;
	}
}
