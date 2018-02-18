package task3.model;

public class Group {
	private String name;
	private int pupils;
	private String teacherName;

	public Group(String name, int pupils, String teacherName) {
		this.name = name;
		this.pupils = pupils;
		this.teacherName = teacherName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPupils() {
		return pupils;
	}

	public void setPupils(int pupils) {
		this.pupils = pupils;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	@Override
	public String toString() {
		return "Group{" +
			"name='" + name + '\'' +
			", pupils=" + pupils +
			", teacherName='" + teacherName + '\'' +
			'}';
	}
}
