package Model;

import java.util.ArrayList;
import java.util.List;

public class CompareModel {
	
	/**
	 * CurrentRows 를 originalRows 와 비교하여, PK 가 다른 추가된 행을 찾아 반환한다.<br>
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getAddedRows(String[][] originalRows,
			String[][] currentRows, int[] pkIndexes) {

		if(currentRows == null)
			return null; // 현재 데이터가 하나도 없으면 추가된 로우가 없는 것임
		
		ArrayList<String[]> addedRows = new ArrayList<String[]>();
		boolean isAddedRow, isSameRow;

		for (int i = 0; i < currentRows.length; i++) {
			isAddedRow = true;

			for (int j = 0; j < originalRows.length; j++) {
				isSameRow = true;

				// PK 만을 검사하여 같은 행인지 확인한다.
				for (int pk = 0; pk < pkIndexes.length; pk++) {
					int pIndex = pkIndexes[pk] - 1;

					if (!currentRows[i][pIndex].equals(originalRows[j][pIndex])) {
						isSameRow = false; // 다른 PK 가 있다면 같은 행이 아니다.
						break; // 다음 originalRow 로 넘어간다.
					}
				}

				// 같은 행이 있는 경우
				if (isSameRow) {
					isAddedRow = false;
					break; // 더이상 originalRows 를 탐색하지 않는다.
				}
			}

			if (isAddedRow)
				addedRows.add(currentRows[i]);
		}

		return listToDArr(addedRows);
	}
	
	/**
	 * CurrentRows 를 originalRows 와 비교하여, PK 가 다른 추가된 행을 찾아 반환한다.<br>
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getAddedRows(List<String[]> originalRows,
			String[][] currentRows, int[] pkIndexes) {
		return getAddedRows(listToDArr(originalRows), currentRows, pkIndexes);
	}
	
	/**
	 * CurrentRows 를 originalRows 와 비교하여, PK 가 다른 추가된 행을 찾아 반환한다.<br>
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getAddedRows(String[][] originalRows,
			List<String[]> currentRows, int[] pkIndexes) {
		return getAddedRows(originalRows, listToDArr(currentRows), pkIndexes);
	}
	
	/**
	 * CurrentRows 를 originalRows 와 비교하여, PK 가 다른 추가된 행을 찾아 반환한다.<br>
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getAddedRows(List<String[]> originalRows,
			List<String[]> currentRows, int[] pkIndexes) {
		return getAddedRows(listToDArr(originalRows), listToDArr(currentRows), pkIndexes);
	}
	
	
	
	/**
	 * CurrentRows 를 originalRows 와 비교하여, PK 가 같고 나머지 속성이 변경된 행을 반환한다.
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getUpdatedRows(String[][] originalRows,
			String[][] currentRows, int[] pkIndexes) {
		
		if(currentRows == null)
			return null; // 현재 데이터가 하나도 없으면 수정된 로우가 없는 것임
		
		ArrayList<String[]> updatedRows = new ArrayList<String[]>();
		boolean isUpdatedRow, isSameRow;

		for (int i = 0; i < currentRows.length; i++) {
			isUpdatedRow = false;

			originalRowsLoop:
			for (int j = 0; j < originalRows.length; j++) {
				isSameRow = true;

				// PK 만을 검사하여 같은 행인지 확인한다.
				for (int pk = 0; pk < pkIndexes.length; pk++) {
					int pIndex = pkIndexes[pk] - 1;

					if (!currentRows[i][pIndex].equals(originalRows[j][pIndex])) {
						isSameRow = false; // 다른 PK 가 있다면 같은 행이 아니다.
						break; // 다음 originalRow 로 넘어간다.
					}
				}

				// 같은 행인 경우
				if (isSameRow) {
					for(int k=0; k < currentRows[i].length; k++){
						// 다른 속성이 하나라도 있는 경우. PK 는 중복검사.
						if (!currentRows[i][k].equals(originalRows[j][k])){
							isUpdatedRow = true;
							break originalRowsLoop; // 더이상 originalRows 를 탐색하지 않는다. 
						}
					}
				}
			}

			if (isUpdatedRow)
				updatedRows.add(currentRows[i]);
		}

		return listToDArr(updatedRows);
	}
	
	/**
	 * CurrentRows 를 originalRows 와 비교하여, PK 가 같고 나머지 속성이 변경된 행을 반환한다.
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getUpdatedRows(List<String[]> originalRows,
			String[][] currentRows, int[] pkIndexes) {
		return getUpdatedRows(listToDArr(originalRows), currentRows, pkIndexes);
	}
	
	/**
	 * CurrentRows 를 originalRows 와 비교하여, PK 가 같고 나머지 속성이 변경된 행을 반환한다.
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getUpdatedRows(String[][] originalRows,
			List<String[]> currentRows, int[] pkIndexes) {
		return getUpdatedRows(originalRows, listToDArr(currentRows), pkIndexes);
	}
	
	/**
	 * CurrentRows 를 originalRows 와 비교하여, PK 가 같고 나머지 속성이 변경된 행을 반환한다.
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getUpdatedRows(List<String[]> originalRows,
			List<String[]> currentRows, int[] pkIndexes) {
		return getUpdatedRows(listToDArr(originalRows), listToDArr(currentRows), pkIndexes);
	}
	
	/**
	 * originalRows 를 CurrentRows 와 비교하여, PK 가 사라진 행을 찾아 반환한다.<br>
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getDeletedRows(String[][] originalRows,
			String[][] currentRows, int[] pkIndexes) {
		
		if(currentRows == null)
			return originalRows; // 현재 데이터가 하나도 없으면 모두 삭제된 것임.
		
		ArrayList<String[]> deletedRows = new ArrayList<String[]>();
		boolean isDeletedRow, isSameRow;

		for (int i = 0; i < originalRows.length; i++) {
			isDeletedRow = true;

			for (int j = 0; j < currentRows.length; j++) {
				isSameRow = true;

				// PK 만을 검사하여 같은 행인지 확인한다.
				for (int pk = 0; pk < pkIndexes.length; pk++) {
					int pIndex = pkIndexes[pk] - 1;

					if (!originalRows[i][pIndex].equals(currentRows[j][pIndex])) {
						isSameRow = false; // 다른 PK 가 있다면 같은 행이 아니다.
						break; // 다음 currentRow 로 넘어간다.
					}
				}

				// 같은 행이 있는 경우
				if (isSameRow) {
					isDeletedRow = false;
					break; // 더이상 currentRows 를 탐색하지 않는다.
				}
			}

			if (isDeletedRow)
				deletedRows.add(originalRows[i]);
		}

		return listToDArr(deletedRows);
	}
	
	/**
	 * originalRows 를 CurrentRows 와 비교하여, PK 가 사라진 행을 찾아 반환한다.<br>
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getDeletedRows(List<String[]> originalRows,
			String[][] currentRows, int[] pkIndexes) {
		return getDeletedRows(listToDArr(originalRows), currentRows, pkIndexes);
	}
	
	/**
	 * originalRows 를 CurrentRows 와 비교하여, PK 가 사라진 행을 찾아 반환한다.<br>
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getDeletedRows(String[][] originalRows,
			List<String[]> currentRows, int[] pkIndexes) {
		return getDeletedRows(originalRows, listToDArr(currentRows), pkIndexes);
	}
	
	/**
	 * originalRows 를 CurrentRows 와 비교하여, PK 가 사라진 행을 찾아 반환한다.<br>
	 * 세 번째 인자에는 PK 인 열의 인덱스들을 담고 있는 int 배열을 전달한다.<br>
	 * 예컨대 1,3 번째 열이 Primary Key 라면 [1, 3] 을 세 번째 인자로 전달한다.<br>
	 */
	public static String[][] getDeletedRows(List<String[]> originalRows,
			List<String[]> currentRows, int[] pkIndexes) {
		return getDeletedRows(listToDArr(originalRows), listToDArr(currentRows), pkIndexes);
	}
	
	/** String[] 의 list 를 String[][] 로 변환한다. */
	private static String[][] listToDArr(List<String[]> list){
		if(list.size() == 0)
			return null;
		
		String[][] arr = new String[list.size()][list.get(0).length];
		for(int i=0; i<arr.length; i++){
			System.arraycopy(list.get(i), 0, arr[i], 0, list.get(i).length);
		}
		return arr;
	}
}
