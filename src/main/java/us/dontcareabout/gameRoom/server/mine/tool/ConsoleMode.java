package us.dontcareabout.gameRoom.server.mine.tool;

import java.util.Scanner;

import us.dontcareabout.gameRoom.client.mine.MineGM;
import us.dontcareabout.gameRoom.client.mine.ai.DummyAI;
import us.dontcareabout.gameRoom.client.mine.ai.JavaAI;
import us.dontcareabout.gameRoom.client.mine.vo.GameInfo;
import us.dontcareabout.gameRoom.client.mine.vo.XY;

//FIXME 因為 JavaAI 用了純 GWT 的 code 而無法執行..... Orz
public class ConsoleMode {
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		JavaAI ai = new DummyAI();	//FIXME change your Player here!!

		MineGM gm = new MineGM();
		GameInfo result;
		XY xy;

		do {
			do {
				result = gm.getGameInfo();
				if(result.getPlayerHit()[0] >= (result.getTotal() / 2.0)) {
					System.out.println("Player win!");
					System.exit(0);	//暴力結束 \囧/
				}
				show(result);
				xy = new XY(
					read("x (0~" + (result.getWidth() - 1) + ") : "),
					read("y (0~" + (result.getHeight() - 1) + ") : ")
				);
			} while(gm.shoot(0, xy));

			do {
				xy = ai.guess(gm.getGameInfo());
			} while(gm.shoot(1, xy));

			//顯示 Player 踩了哪些
			result = gm.getGameInfo();
			System.out.print("Player : ");

			System.out.println("\n========================");
		} while(result.getPlayerHit()[1] < (result.getTotal() / 2.0));

		System.out.println("Player win!");
		System.exit(0);
	}

	private static int read(String string) {
		System.out.print(string);
		try{
			return scanner.nextInt();
		}catch(Exception e){
			return -1;
		}
	}

	public static void show(GameInfo info) {
		//畫 x 軸的 index
		System.out.print("  ");

		for(int x = 0; x < info.getWidth(); x++) {
			System.out.print((x % 10) + " ");
		}

		System.out.println();

		//畫邊框
		System.out.print(" +");

		for(int x = 0; x < info.getWidth(); x++) {
			System.out.print("--");
		}

		System.out.println();

		for(int y = 0; y < info.getHeight(); y++) {
			for(int x = 0; x < info.getWidth(); x++) {
				//畫 y 軸 index
				if(x == 0){
					System.out.print((y % 10)+"|");
				}

				switch(info.getMap()[x][y]) {
				case -1:
					System.out.print("-");
					break;
				case 0:
					System.out.print(" ");
					break;
				case MineGM.IS_MINE:
				case MineGM.P2_FLAG:
					System.out.print("*");
					break;
				default:
					System.out.print(info.getMap()[x][y]);
				}
				System.out.print(" ");
			}

			switch (y) {
			case 0:
				System.out.print("  Player : ");
				System.out.print(info.getPlayerHit()[0]);
				break;
			case 1:
				System.out.print("  Player : ");
				System.out.print(info.getPlayerHit()[1]);
				break;
			case 2:
				System.out.print("  Remainder : ");
				System.out.print(info.getRemainder()+"/"+info.getTotal());
				break;
			default:
				break;
			}
			System.out.println();
		}
		System.out.println("========================");
	}
}
