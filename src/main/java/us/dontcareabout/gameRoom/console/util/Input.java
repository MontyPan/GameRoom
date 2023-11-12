package us.dontcareabout.gameRoom.console.util;

import java.util.Scanner;

public class Input {
	private static final Scanner scanner = new Scanner(System.in);

	public static int $int() {
		return $int("");
	}

	/**
	 * @param prompt 提示字串
	 * @return 輸入的整數，無法處理的話會回傳 {@value Integer#MIN_VALUE}
	 */
	public static int $int(String prompt) {
		System.out.print(prompt);
		try {
			return scanner.nextInt();
		} catch(Exception e){
			return Integer.MIN_VALUE;
		}
	}
}
