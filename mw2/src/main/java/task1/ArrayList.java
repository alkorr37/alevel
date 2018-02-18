package task1;

import java.util.Arrays;

public class ArrayList<E> {
	private final int CAPACITY_EXTENDER = 16;

	private Object[] data;
	private int curIndex = 0;

	public ArrayList() {
		this.data = new Object[CAPACITY_EXTENDER];
	}

	private void extendArray() {
		Object[] newData = new Object[data.length + CAPACITY_EXTENDER];
		System.arraycopy(data, 0, newData, 0, data.length);
		data = newData;
	}

	public int add(E elem) {
		if (curIndex == data.length) {
			extendArray();
		}
		add(elem, curIndex);
		return curIndex - 1;
	}

	public void add(E elem, int index) {
		if (index > data.length || curIndex == data.length) {
			extendArray();
			add(elem, index);
		} else {
			Object[] newData = new Object[data.length];
			System.arraycopy(data, 0, newData, 0, index);
			System.arraycopy(data, index, newData, index + 1, data.length - index - 1);
			data = newData;
			data[index] = elem;
			curIndex = (curIndex > index ? curIndex: index) + 1;
		}
	}

	public E get(int index) {
		if (index < curIndex) {
			return (E) data[index];
		}
		return null;
	}

	public E remove(int index) {
		if (index >= curIndex) {
			return null;
		}
		E elem = get(index);
		curIndex--;

		Object[] newData = new Object[data.length];
		System.arraycopy(data, 0, newData, 0, index);
		System.arraycopy(data, index + 1, newData, index, data.length - index - 1);
		data = newData;
		return elem;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("User view: {");
		for (int i = 0; i < curIndex; i++) {
			sb.append(" '").append(data[i]).append("',");
		}
		sb.append("}")
			.append(System.lineSeparator())
			.append("Internal view: ")
			.append(Arrays.toString(data));
		return sb.toString();
	}
}
